package pl.oblivion.dataStructure;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.SphereCollider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OctreeNode {

	private final int numberOfChildren = 8;

	private final int currentDepth;
	private final Vector3f center;
	private LinkedList<Model> objects;
	private OctreeNode[] children;
	private OctreeNode parent;
	private LinkedList<Model> parentsObjets;
	private List<ModelPair> colliedObjects;

	OctreeNode(Vector3f pos, float halfWidth, int stopDepth, OctreeNode parent) {
		this.currentDepth = stopDepth;
		this.center = pos;
		this.parent = parent;
		this.objects = new LinkedList<>();
		this.parentsObjets = (parent != null) ? new LinkedList<>(parent.objects) : new LinkedList<>();
		this.colliedObjects = new ArrayList<>();
		createChildren(pos, halfWidth, stopDepth);
	}

	private void createChildren(Vector3f pos, float halfWidth, int stopDepth) {
		Vector3f offset = new Vector3f();
		if (stopDepth > 0) {
			this.children = new OctreeNode[numberOfChildren];
			float step = halfWidth * 0.5f;

			for (int i = 0; i < children.length; i++) {
				offset.x = (((i & 1) == 0) ? step : - step);
				offset.y = (((i & 2) == 0) ? step : - step);
				offset.z = (((i & 4) == 0) ? step : - step);

				children[i] = new OctreeNode(new Vector3f(pos).add(offset), step, stopDepth - 1, this);
			}
		} else {
			this.children = null;
		}
	}

	public void insertModel(final Model model) {
		int index = 0;
		boolean straddle = false;
		float delta;

		Vector3f modelsPosition = model.getPosition();
		final float[] objPos = {modelsPosition.x, modelsPosition.y, modelsPosition.z};
		final float[] nodePos = {center.x, center.y, center.z};

		for (int i = 0; i < nodePos.length; i++) {
			delta = nodePos[i] - objPos[i];

			if (Math.abs(delta) <= model.getModelView().getFurthestPoint()) {
				straddle = true;
				break;
			}

			if (delta > 0.0f) { index |= (1 << i); }
		}

		if (!straddle && currentDepth > 0) {
			children[index].insertModel(model);
		} else {
			objects.add(model);
		}
	}

	public void clean() {
		this.objects.clear();
		if (currentDepth > 0) {
			for (OctreeNode child : children) {
				child.clean();
			}
		}
	}

	public void update() {
		updateModelsNode();
		checkOwnObjectsForCollisions();
		checkParentsObjects();
		if (currentDepth > 0) {
			for (OctreeNode child : children) {
				child.update();
			}
		}
	}

	private void updateModelsNode() {
		for (Model model : objects) {
			updateModel(model);
		}
	}

	private void updateModel(Model model) {
		CollisionShape collisionShape = model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
		if (collisionShape.isMoving()) {
			collisionShape.update();

		}
		/*
		TODO if model isMoving() check if its new position can be moved upper (to parent's node) or bottom (1 of 8 child nodes).
		 */
	}

	private void fillParentsObjectsList() {
		if (parent != null) {
			this.parentsObjets.clear();
			this.parentsObjets.addAll(this.parent.parentsObjets);
			this.parentsObjets.addAll(this.parent.getObjects());
		}
	}

	private void checkOwnObjectsForCollisions() {
		this.colliedObjects.clear();
		for (int i = 0; i < objects.size(); i++) {
			CollisionShape ownCollisionShape1 =
					objects.get(i).getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
			if (ownCollisionShape1.isMoving()) {
				for (int j = i + 1; j < objects.size(); j++) {
					CollisionShape ownCollisionShape2 =
							objects.get(j).getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
					if (intersection(ownCollisionShape1, ownCollisionShape2)) {
						this.colliedObjects.add(new ModelPair(objects.get(i), objects.get(j)));
					}
				}
			}
		}
	}

	private void checkParentsObjects() {
		fillParentsObjectsList();
		for (Model ownModel : objects) {
			CollisionShape ownCollisionShape =
					ownModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
			if (ownCollisionShape.isMoving()) {
				for (Model parentsModel : parentsObjets) {
					CollisionShape parentsCollisionShape =
							parentsModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
					if (intersection(ownCollisionShape, parentsCollisionShape)) {
						this.colliedObjects.add(new ModelPair(ownModel, parentsModel));
					}
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

	private LinkedList<Model> getObjects() {
		return objects;
	}
}
