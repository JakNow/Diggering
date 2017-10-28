package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;

public class MoveComponent extends BaseComponent {

    private final Model model;

    private float moveSpeed;

    public MoveComponent(Model model) {
        this.model = model;
    }

    public MoveComponent(Model model, float moveSpeed) {
        this.model = model;
        this.moveSpeed = moveSpeed;
    }

    public void move(boolean xAxis, boolean yAxis, boolean zAxis) {
        if (xAxis)
            model.getPosition().x += moveSpeed;
        if (yAxis)
            model.getPosition().y += moveSpeed;
        if (zAxis)
            model.getPosition().z += moveSpeed;
    }


    public void move(Vector3f move) {
        model.getPosition().x += move.x;
        model.getPosition().y += move.y;
        model.getPosition().z += move.z;
    }

    public void move(float dx, float dy, float dz) {
        model.getPosition().x += dx;
        model.getPosition().y += dy;
        model.getPosition().z += dz;
    }

    public Model getModel() {
        return model;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
