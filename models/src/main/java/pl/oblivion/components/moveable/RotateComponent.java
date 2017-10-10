package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;

public class RotateComponent extends BaseComponent {

    final Model model;
    float rotateSpeed;

    public RotateComponent(Model model) {
        this.model = model;
    }

    public RotateComponent(Model model, float rotateSpeed) {
        this.model = model;
        this.rotateSpeed = rotateSpeed;
    }

    public void rotate(Vector3f vector) {
        model.getRotation().x += vector.x * rotateSpeed;
        model.getRotation().y += vector.y * rotateSpeed;
        model.getRotation().z += vector.z * rotateSpeed;
    }

    public void rotate(boolean xAxis, boolean yAxis, boolean zAxis) {
        if (xAxis)
            model.getRotation().x += rotateSpeed;
        if (yAxis)
            model.getRotation().y += rotateSpeed;
        if (zAxis)
            model.getRotation().z += rotateSpeed;
    }

    public Model getModel() {
        return model;
    }

    public float getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(float rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }
}
