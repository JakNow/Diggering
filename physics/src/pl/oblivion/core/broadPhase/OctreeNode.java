package pl.oblivion.core.broadPhase;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.utils.PMaths;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OctreeNode {

	private final String id;
	//OctreeNode structure
	private Vector3f center;
	private float worldExtends;
	private int currentDepth;
	private int worldDepth;
	private OctreeNode parentsNode;
	private OctreeNode[] childrensNode;
	private Octree octree;
	private int position;
	//Objects mapping
	private List<Model> objects = new LinkedList<>();
	private List<Model> collidableObjects = new ArrayList<>();

	OctreeNode(Vector3f center, float worldExtends, int currentDepth, int worldDepth, OctreeNode parentsNode,
			Octree octree, int position) {
		this.center = center;
		this.worldExtends = worldExtends;
		this.currentDepth = currentDepth;
		this.worldDepth = worldDepth;
		this.parentsNode = parentsNode;
		this.octree = octree;
		this.position = position;
		this.id = generateID();

		this.childrensNode = createChildren();
	}

	private String generateID() {
		if (parentsNode != null) {
			return parentsNode.id.concat(String.valueOf(position));
		}
		return String.valueOf(position);

	}

	private OctreeNode[] createChildren() {
		if (currentDepth < worldDepth) {
			OctreeNode[] childrensNode = new OctreeNode[8];
			float step = worldExtends / 2;
			for (int i = 0; i < 8; i++) {

				Vector3f offset =
						new Vector3f((((i & 1) == 0) ? step / 2 : - step / 2), (((i & 2) == 0) ? step / 2 : - step / 2),
								(((i & 4) == 0) ? step / 2 : - step / 2));
				childrensNode[i] = new OctreeNode(new Vector3f(this.center).add(offset), step, this.currentDepth + 1,
						this.worldDepth, this, octree, i);
			}
			return childrensNode;
		} else {
			return null;
		}
	}

	public void insertModel(final Model model) {
		int index = 0;
		boolean straddle = false;
		float delta;

		final float[] modelsPosition = PMaths.vecToArray(model.getPosition());
		final float[] nodePosition = PMaths.vecToArray(this.getCenter());

		for (int i = 0; i < 3; i++) {
			delta = nodePosition[i] - modelsPosition[i];

			if (Math.abs(delta) <= model.getModelView().getFurthestPoint() * model.getScale()) {
				straddle = true;
				break;
			}

			if (delta > 0.0f) {
				index |= (1 << i);
			}
		}

		if (! straddle && currentDepth < worldDepth) {
			childrensNode[index].insertModel(model);
		} else {
			putModelToList(model);
			update();
		}
	}

	private void putModelToList(final Model model) {
		octree.getModelOctreeNodeMap().put(model, this);
		objects.add(model);
	}

	public void updateModelsPosition(final Model model) {
		octree.getModelOctreeNodeMap().remove(model);
		objects.remove(model);
		octree.insertObject(model);
	}

	private void update() {
		this.collidableObjects.clear();
		fillCollidableObjectsListParents(collidableObjects);
		fillCollidableObjectsListChildren(collidableObjects);
		collidableObjects.addAll(objects);
	}

	private void fillCollidableObjectsListParents(List<Model> collidableObjects) {
		if (parentsNode != null) {
			collidableObjects.addAll(parentsNode.objects);
			parentsNode.fillCollidableObjectsListParents(collidableObjects);
		}
	}

	private void fillCollidableObjectsListChildren(List<Model> collidableObjects) {
		if (currentDepth < worldDepth) {
			for (OctreeNode childNode : childrensNode) {
				collidableObjects.addAll(childNode.objects);
				childNode.fillCollidableObjectsListChildren(collidableObjects);
			}
		}

	}

	public List<Model> getCollidableObjectsList() {
		return collidableObjects;
	}

	public void clean() {
		objects.clear();
		collidableObjects.clear();
		if (currentDepth < worldDepth) {
			for (OctreeNode child : childrensNode) {
				child.clean();
			}
		}
	}

	public String getId() {
		return id;
	}

	public Vector3f getCenter() {
		return center;
	}

}