package pl.oblivion.shapes;

import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.collision.CollisionIntersection;

import java.util.Map;


public abstract class CollisionShape implements CollisionIntersection {

    public Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public abstract void updatePosition();

    public abstract CollisionShape createShape(TexturedMesh texturedMesh);

    public abstract CollisionShape createShape(ModelPart modelPart);

    public abstract CollisionShape createShape(Model model);

    public abstract Map<TexturedMesh, CollisionShape> createMapShape(ModelPart modelPart);

    public abstract Map<ModelPart, CollisionShape> createMapShape(Model model);

    public abstract Map<TexturedMesh, CollisionShape> createFullMapShape(Model model);

}
