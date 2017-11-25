package components;

import pl.oblivion.base.ModelPart;
import pl.oblivion.components.BaseComponent;
import shapes.CollisionShape;

import java.util.HashMap;

public class CollisionComponent extends BaseComponent {

    private final CollisionShape broadPhaseCollisionShape;
    private HashMap<ModelPart, CollisionShape> narrowPhaseCollisionShapeMap = new HashMap<>();

    public CollisionComponent(CollisionShape broadPhaseCollisionShape, HashMap<ModelPart, CollisionShape> narrowPhaseCollisionShapeMap) {
        this.broadPhaseCollisionShape = broadPhaseCollisionShape;
        this.narrowPhaseCollisionShapeMap = narrowPhaseCollisionShapeMap;
    }

    public CollisionShape getBroadPhaseCollisionShape() {
        return broadPhaseCollisionShape;
    }

    public HashMap<ModelPart, CollisionShape> getNarrowPhaseCollisionShapeMap() {
        return narrowPhaseCollisionShapeMap;
    }
}
