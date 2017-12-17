package pl.oblivion.components;

import pl.oblivion.shapes.CollisionShape;

public class CollisionComponent extends BaseComponent {

	private final CollisionShape broadPhaseCollisionShape;
	private final CollisionShape narrowPhaseCollisionShapeMap;

	public CollisionComponent(CollisionShape broadPhaseCollisionShape, CollisionShape narrowPhaseCollisionShapeMap) {
		this.broadPhaseCollisionShape = broadPhaseCollisionShape;
		this.narrowPhaseCollisionShapeMap = narrowPhaseCollisionShapeMap;
	}

	public CollisionShape getBroadPhaseCollisionShape() {
		return broadPhaseCollisionShape;
	}

	public CollisionShape getNarrowPhaseCollisionShape() {
		return narrowPhaseCollisionShapeMap;
	}
}
