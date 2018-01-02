package pl.oblivion.colliders;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

public abstract class BasicCollider implements Intersection {

	private final Model model;
	private final ColliderType colliderType;
	private Vector3f translation;
	private Vector3f center;

	BasicCollider(Model model, ColliderType colliderType) {
		this.model = model;
		this.colliderType = colliderType;
		this.center = model.getPosition();
	}

	public abstract void update();

	public Model getModel() {
		return model;
	}

	Vector3f getTranslation() {
		return translation;
	}

	void setTranslation(Vector3f translation) {
		this.translation = translation;
	}

	public Vector3f getCenter() {
		return center;
	}

	public ColliderType getColliderType() {
		return colliderType;
	}
}
