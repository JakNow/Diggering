package pl.oblivion.shapes;

import pl.oblivion.base.Model;
import pl.oblivion.collision.CollisionIntersection;


public abstract class CollisionShape implements CollisionIntersection {

    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public abstract void updatePosition();
}
