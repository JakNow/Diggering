package pl.oblivion.components.moveable;

import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;

public class RotateComponent extends BaseComponent {

    private final Model model;

    public RotateComponent(Model model) {
        this.model = model;
    }

    public void rotate(float dx, float dy, float dz, float delta) {
        model.getRotation().x += dx * delta;
        model.getRotation().y += dy * delta;
        model.getRotation().z += dz * delta;

    }

    public Model getModel() {
        return model;
    }
}
