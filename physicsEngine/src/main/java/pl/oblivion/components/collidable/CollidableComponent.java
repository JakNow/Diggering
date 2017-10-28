package pl.oblivion.components.collidable;

import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.shapes.CollisionShape;

import java.util.Map;

public class CollidableComponent extends BaseComponent {

    private final Model model;
    private boolean isMoveable = false;
    private Map<TexturedMesh, CollisionShape> collisionShapeTexturedMeshMap;
    private Map<ModelPart, CollisionShape> collisionShapeModelPartMap;
    private CollisionShape modelCollisionShape;

    public CollidableComponent(Model model) {
        this.model = model;
    }

    public boolean isMoveAble() {
        return isMoveable;
    }

    public void setMoveAble(boolean moveAble) {
        isMoveable = moveAble;
    }

    public Model getModel() {
        return model;
    }

    public boolean isMoveable() {
        return isMoveable;
    }

    public Map<TexturedMesh, CollisionShape> getCollisionShapeTexturedMeshMap() {
        return collisionShapeTexturedMeshMap;
    }

    public Map<ModelPart, CollisionShape> getCollisionShapeModelPartMap() {
        return collisionShapeModelPartMap;
    }

    public CollisionShape getModelCollisionShape() {
        return modelCollisionShape;
    }
}
