package pl.oblivion.base;

import org.joml.Vector3f;
import pl.oblivion.components.BaseComponent;

import java.util.HashMap;

public abstract class Model {

    private final ModelView modelView;
    private final Vector3f position;
    private final Vector3f rotation;
    private final float scale;

    private HashMap<Class, BaseComponent> componentHashMap = new HashMap<>();

    public Model(Vector3f position, Vector3f rotation, float scale, ModelView modelView) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.modelView = modelView;
    }

    public void delete() {
        for (ModelPart modelPart : modelView.getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                texturedMesh.getMesh().delete();
                texturedMesh.getMaterial().delete();
            }
        }
    }

    public ModelView getModelView() {
        return modelView;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void addComponent(BaseComponent component) {
        componentHashMap.put(component.getClass(), component);
    }

    public <T> T getComponent(Class<T> component) {
        T result = (T) componentHashMap.get(component);
        if (result == null)
            return null;
        return result;
    }
}
