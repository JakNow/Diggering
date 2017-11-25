package pl.oblivion.components.moveable;

import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;

public class MoveComponent extends BaseComponent {

    private final Model model;

    public MoveComponent(Model model) {
        this.model = model;
    }

    public void move(float dx, float dy, float dz, float delta) {
        model.getPosition().x += dx * delta;
        model.getPosition().y += dy * delta;
        model.getPosition().z += dz * delta;
    }

    public void move(float dx, float dy, float dz) {
        model.getPosition().x += dx;
        model.getPosition().y += dy;
        model.getPosition().z += dz;
    }

    public Model getModel() {
        return model;
    }
}
