package pl.oblivion.base;

import org.joml.Vector3f;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;

import java.util.HashMap;

public abstract class Model {

	private final ModelView modelView;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;

	private HashMap<ComponentType, BaseComponent> componentHashMap = new HashMap<>();

	private float height;
	private boolean isStatic = true;

	public Model(Vector3f position, Vector3f rotation, float scale, ModelView modelView) {
		this.position = position;
		this.height = position.y;
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

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPostion(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void addComponent(BaseComponent component) {
		componentHashMap.put(component.getComponentType(), component);
	}

	/*public <T> T getComponent(Class<T> component) {
		T result = (T) componentHashMap.get(component);
		if (result == null) { return null; }
		return result;
	}*/

	public <T> T getComponent(ComponentType componentType){
		T result = (T) componentHashMap.get(componentType);
		if (result == null) { return null; }
		return result;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean aStatic) {
		isStatic = aStatic;
	}

	public HashMap<ComponentType, BaseComponent> getComponentHashMap() {
		return componentHashMap;
	}
}
