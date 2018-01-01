package pl.oblivion.colliders;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

public abstract class BasicCollider implements Intersection {

	private final Model model;
	private Vector3f translation;
	private Vector3f center;

	BasicCollider(Model model) {
		this.model = model;
		this.center = model.getPosition();
	}

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

}
