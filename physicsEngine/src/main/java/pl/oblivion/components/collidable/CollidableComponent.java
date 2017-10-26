package pl.oblivion.components.collidable;

import pl.oblivion.base.Model;
import pl.oblivion.collision.broadPhase.BroadPhase;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.shapes.CollisionShape;

public class CollidableComponent extends BaseComponent {

    private final Model model;
    private boolean isMoveAble = false;
    private CollisionShape collisionShape;

    public CollidableComponent(Model model) {
        this.model = model;
    }


    public void createCollisionShape(CollisionShape collisionShape) {
        this.collisionShape = collisionShape;
        BroadPhase.broadPhaseCollisionMap.put(model, this);
    }

    public boolean isMoveAble() {
        return isMoveAble;
    }

    public void setMoveAble(boolean moveAble) {
        isMoveAble = moveAble;
    }

    public CollisionShape getCollisionShape() {
        return collisionShape;
    }
}
