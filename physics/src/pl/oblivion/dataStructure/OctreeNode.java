package pl.oblivion.dataStructure;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.Mesh;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.models.ModelsManager;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.SphereCollider;
import pl.oblivion.utils.PMaths;

import java.util.LinkedList;
import java.util.List;

public class OctreeNode {

	private final float meshThickness = 0.05f;
	//OctreeNode structure
	private Vector3f center;
	private float worldExtends;
	private int currentDepth;
	private int worldDepth;
	private OctreeNode parent;
	private OctreeNode[] children;
	private Octree octree;
	//Objects mapping
	private List<Model> staticModels = new LinkedList<>();
	private List<Model> dynamicModels = new LinkedList<>();
	private List<Model> tempDynamicModels = new LinkedList<>();
	private List<Model> parentsModels = new LinkedList<>();
	private List<ModelPair> collidedObjects = new LinkedList<>();
	//Visual params
	private TexturedMesh octreeNodeShape;
	private Vector4f colour;

	OctreeNode(Vector3f center, float worldExtends, int currentDepth, int worldDepth, OctreeNode parent,
			Octree octree) {
		this.center = center;
		this.worldExtends = worldExtends;
		this.currentDepth = currentDepth;
		this.worldDepth = worldDepth;
		this.parent = parent;
		this.octree = octree;
		this.children = createChildren();
		this.octreeNodeShape = createOctreeNodeShape();
		this.colour = setColour();
	}

	private OctreeNode[] createChildren() {
		if (currentDepth < worldDepth) {
			OctreeNode[] children = new OctreeNode[8];
			float step = worldExtends / 2;
			for (int i = 0; i < 8; i++) {

				Vector3f offset =
						new Vector3f((((i & 1) == 0) ? step / 2 : - step / 2), (((i & 2) == 0) ? step / 2 : - step / 2),
								(((i & 4) == 0) ? step / 2 : - step / 2));
				children[i] = new OctreeNode(new Vector3f(this.center).add(offset), step, this.currentDepth + 1,
						this.worldDepth, this, octree);
			}
			return children;
		} else {
			return null;
		}
	}

	//Just for visual effect.
	private TexturedMesh createOctreeNodeShape() {
		TexturedMesh texturedMesh = ModelsManager.getOctreeNodeShape();
		float[] tempVert = texturedMesh.getMeshData().vertices;

		for (int i = 0; i < tempVert.length; i++) {
			tempVert[i] = convertVertices(tempVert[i]);
		}

		Mesh mesh = Mesh.create();
		mesh.bind();
		mesh.createIndexBuffer(texturedMesh.getMeshData().indices);
		mesh.createAttribute(0, tempVert, 3);
		mesh.createAttribute(1, texturedMesh.getMeshData().textures, 2);
		mesh.unbind();

		return new TexturedMesh(mesh, texturedMesh.getMaterial());
	}

	private Vector4f setColour() {
		switch (currentDepth) {
			case 0:
				return new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
			case 1:
				return new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
			case 2:
				return new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
			case 3:
				return new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

			default:
				return new Vector4f(0.3f, 0.6f, 0.4f, 1.0f);
		}
	}

	private float convertVertices(float vertices) {
		if (vertices == 1.0f || vertices == - 1.0f) {
			return vertices * worldExtends / 2;
		} else if (vertices == - 0.98f) {
			return - (worldExtends / 2 - meshThickness);
		} else if (vertices == 0.98f) {
			return (worldExtends / 2 - meshThickness);
		} else if (vertices == 0.01f) {
			return meshThickness;
		} else if (vertices == - 0.01f) {
			return - meshThickness;
		} else { return 0; }
	}

	public void insertModel(final Model model) {
		int index = 0;
		boolean straddle = false;
		float delta;

		final float[] modelsPosition = PMaths.vecToArray(model.getPosition());
		final float[] nodePosition = PMaths.vecToArray(this.getCenter());

		for (int i = 0; i < 3; i++) {
			delta = nodePosition[i] - modelsPosition[i];

			if (Math.abs(delta) <= model.getModelView().getFurthestPoint()) {
				straddle = true;
				break;
			}

			if (delta > 0.0f) {
				index |= (1 << i);
			}
		}

		if (! straddle && currentDepth < worldDepth) {
			children[index].insertModel(model);
		} else {
			putModelToList(model);
		}
	}

	private void putModelToList(final Model model) {
		model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().setMeshColour(this.getColour());
		if (model.getComponent(MoveComponent.class) != null) {
			dynamicModels.add(model);
		} else {
			staticModels.add(model);
		}
	}

	public void update() {
		checkOwnObjectsForCollision();
		updateDynamicModels();
		if (currentDepth < worldDepth) {
			for (OctreeNode child : children) {
				child.update();
			}
		}
	}

	public void clean() {
		staticModels.clear();
		dynamicModels.clear();
		if (currentDepth < worldDepth) {
			for (OctreeNode child : children) {
				child.clean();
			}
		}
	}

	private void updateDynamicModels() {
		tempDynamicModels.clear();
		tempDynamicModels.addAll(dynamicModels);
		for (Model dynamicModel : tempDynamicModels) {
			dynamicModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().update();
			dynamicModels.remove(dynamicModel);
			octree.insertObject(dynamicModel);
		}
	}

	private void fillParentsModels() {
		if (parent != null) {
			parentsModels.clear();
			parentsModels.addAll(parent.dynamicModels);
			parentsModels.addAll(parent.staticModels);
			parentsModels.addAll(parent.parentsModels);
		}
	}

	private void checkOwnObjectsForCollision() {
		this.collidedObjects.clear();
		fillParentsModels();
		for (Model dynamicModel : dynamicModels) {
			CollisionShape dynamicModelCollisionShape =
					dynamicModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
			for (Model staticModel : staticModels) {
				CollisionShape staticModelCollisionShape =
						staticModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
				if (intersection(dynamicModelCollisionShape, staticModelCollisionShape)) {
					this.collidedObjects.add(new ModelPair(dynamicModel, staticModel));
				}
			}
			for (Model parentModel : parentsModels) {
				CollisionShape parentModelCollisionShape =
						parentModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
				if (intersection(dynamicModelCollisionShape, parentModelCollisionShape)) {
					this.collidedObjects.add(new ModelPair(dynamicModel, parentModel));
				}
			}
		}
	}

	private boolean intersection(CollisionShape collisionShape1, CollisionShape collisionShape2) {
		String className = collisionShape2.getClass().getName();
		if (className.contains("AABB")) {
			return collisionShape1.intersection((AABB) collisionShape2);
		} else if (className.contains("CylinderCollider")) {
			return collisionShape1.intersection((CylinderCollider) collisionShape2);
		}
		return className.contains("SphereCollider") &&
				collisionShape1.intersection((SphereCollider) collisionShape2);
	}

	public int getCurrentDepth() {
		return currentDepth;
	}

	public TexturedMesh getOctreeNodeShape() {
		return octreeNodeShape;
	}

	public OctreeNode[] getChildren() {
		return children;
	}

	public Vector3f getCenter() {
		return center;
	}

	public int getWorldDepth() {
		return worldDepth;
	}

	public Vector4f getColour() {
		return colour;
	}
}