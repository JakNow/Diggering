package pl.oblivion.dataStructure;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.SphereCollider;
import pl.oblivion.utils.PMaths;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class OctreeNode {

	private final int numberOfChildren = 8;

	private final int currentDepth;
	private final Vector3f center;
	private final float halfWidth;
	private LinkedList<Model> staticModels;
	private LinkedList<Model> dynamicModels;
	private OctreeNode[] children;
	private OctreeNode parent;
	private LinkedList<Model> parentsStaticModels;
	private LinkedList<Model> parentsDynamicModels;
	private List<ModelPair> colliedObjects;
	private String nodeId;

	OctreeNode(Vector3f pos, float halfWidth, int stopDepth, OctreeNode parent) {
		this.currentDepth = stopDepth;
		this.center = pos;
		this.halfWidth = halfWidth;
		this.parent = parent;
		this.staticModels = new LinkedList<>();
		this.dynamicModels = new LinkedList<>();
		this.parentsStaticModels = (parent != null) ? new LinkedList<>(parent.staticModels) : new LinkedList<>();
		this.parentsDynamicModels = (parent != null) ? new LinkedList<>(parent.dynamicModels) : new LinkedList<>();
		this.colliedObjects = new ArrayList<>();
		this.nodeId = (parent != null) ? parent.nodeId + ":" + stopDepth : Integer.toString(stopDepth);
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

	void insertModel(final Model model) {
		int index = 0;
		boolean moveDown = true;
		float delta;

		Vector3f modelsCollisionShapeCenter =
				model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().getTempCenter();

		final float[] modelsCenter = PMaths.vecToArray(modelsCollisionShapeCenter);
		final float[] nodeCenter = PMaths.vecToArray(center);

		for (int i = 0; i < nodeCenter.length; i++) {
			delta = nodeCenter[i] - modelsCenter[i];

			if (Math.abs(delta) + model.getModelView().getFurthestPoint() >= halfWidth) {
				moveDown = false;
				break;
			}

			if (delta > 0.0f) {
				index |= (1 << i);
			}
		}

		if (moveDown && currentDepth > 0) {
			children[index].insertModel(model);
		} else if (moveDown && currentDepth == 0) {
			if (model.getComponent(MoveComponent.class) != null) {
				this.dynamicModels.add(model);
			} else {
				this.staticModels.add(model);
			}
		} else {
			if (parent != null) {
				if (model.getComponent(MoveComponent.class) != null) {
					this.parent.dynamicModels.add(model);
				} else {
					this.parent.staticModels.add(model);
				}
			} else {
				if (model.getComponent(MoveComponent.class) != null) {
					this.dynamicModels.add(model);
				} else {
					this.staticModels.add(model);
				}
			}
		}
	}

	void removeModel(final Model model) {
		boolean objectRemoved = false;

		for (Model staticModel : staticModels) {
			if (staticModel.equals(model)) {
				staticModels.remove(staticModel);
				objectRemoved = true;
				break;
			}
		}
		if (! objectRemoved) {
			for (Model dynamicModel : dynamicModels) {
				if (dynamicModel.equals(model)) {
					dynamicModels.remove(dynamicModel);
					objectRemoved = true;
					break;
				}
			}
		}

		if (! objectRemoved) {
			if (currentDepth > 0) {
				for (OctreeNode child : children) {
					child.removeModel(model);
				}
			}
		}
	}

	void clean() {
		this.staticModels.clear();
		this.dynamicModels.clear();
		if (currentDepth > 0) {
			for (OctreeNode child : children) {
				child.clean();
			}
		}
	}

	void dynamicUpdate() {
		updateModelsNode();
		checkOwnObjectsForCollisions();
		if (currentDepth > 0) {
			for (OctreeNode child : children) {
				child.dynamicUpdate();
			}
		}
		if (this.colliedObjects.size() != 0) {
			// System.out.println(this.colliedObjects.size());
		}
	}

	private void updateModelsNode() {
		for (Model model : dynamicModels) {
			updateModel(model);
		}
	}

	private void updateModel(final Model model) {
		model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().update();
		// this.changeModelsNode(model);
	}

	private void changeModelsNode(final Model model) {
		this.removeModel(model);
		this.insertModel(model);
	}

	private void noChildren(final Model model) {
		boolean straddle = false;
		float delta;

		Vector3f modelsCollisionShapeCenter =
				model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().getTempCenter();
		final float[] objPos =
				{modelsCollisionShapeCenter.x, modelsCollisionShapeCenter.y, modelsCollisionShapeCenter.z};
		final float[] parentPos = {parent.center.x, parent.center.y, parent.center.z};

		for (int i = 0; i < parentPos.length; i++) {
			delta = parentPos[i] - objPos[i];

			if (Math.abs(delta) <= model.getModelView().getFurthestPoint()) {
				straddle = true;
				break;
			}
		}
		if (! straddle) {

		}
	}
    /*
      int index = 0;
        boolean straddle = false;
        float delta;

        Vector3f modelsCollisionShapeCenter = model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().getCenter();
        final float[] objPos = {modelsCollisionShapeCenter.x, modelsCollisionShapeCenter.y, modelsCollisionShapeCenter.z};
        final float[] nodePos = {center.x, center.y, center.z};

        for (int i = 0; i < nodePos.length; i++) {
            delta = nodePos[i] - objPos[i];

            if (Math.abs(delta) <= model.getModelView().getFurthestPoint()) {
                straddle = true;
                break;
            }

            if (delta > 0.0f) {
                index |= (1 << i);
            }
        }

        if (!straddle && currentDepth > 0) {
            children[index].insertModel(model);
        } else {
            if (model.getComponent(MoveComponent.class) != null) {
                this.dynamicModels.add(model);
            } else {
                this.staticModels.add(model);
            }
        }
     */

	private void fillParentsObjectsList() {
		if (parent != null) {
			this.parentsStaticModels.clear();
			this.parentsStaticModels.addAll(this.parent.parentsStaticModels);
			this.parentsStaticModels.addAll(this.parent.staticModels);

			this.parentsDynamicModels.clear();
			this.parentsDynamicModels.addAll(this.parent.parentsDynamicModels);
			this.parentsDynamicModels.addAll(this.parent.dynamicModels);
		}
	}

	private void checkOwnObjectsForCollisions() {
		this.colliedObjects.clear();
		fillParentsObjectsList();
		for (Model dynamicModel : dynamicModels) {
			CollisionShape dynamicModelsCollisionShape =
					dynamicModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
			for (Model staticModel : staticModels) {
				CollisionShape staticModelsCollisionShape =
						staticModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
				if (intersection(dynamicModelsCollisionShape, staticModelsCollisionShape)) {
					this.colliedObjects.add(new ModelPair(dynamicModel, staticModel));
				}
			}
			for (Model parentsStaticModel : parentsStaticModels) {
				CollisionShape parentsStaticModelCollisionShape =
						parentsStaticModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
				if (intersection(dynamicModelsCollisionShape, parentsStaticModelCollisionShape)) {
					this.colliedObjects.add(new ModelPair(dynamicModel, parentsStaticModel));
				}
			}
			for (Model parentsDynamicModel : parentsDynamicModels) {
				CollisionShape parentsDynamicModelCollisionShape =
						parentsDynamicModel.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
				if (intersection(dynamicModelsCollisionShape, parentsDynamicModelCollisionShape)) {
					this.colliedObjects.add(new ModelPair(dynamicModel, parentsDynamicModel));
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

}