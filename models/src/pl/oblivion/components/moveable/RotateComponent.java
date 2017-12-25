package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;

public class RotateComponent extends BaseComponent {

	private final Model model;
	private Vector3f rotation;

	public RotateComponent(Model model) {
		this.model = model;
		this.rotation = new Vector3f(model.getRotation());
	}

	public void rotate(float dx, float dy, float dz, float delta) {
		model.getRotation().x += dx * delta;
		model.getRotation().y += dy * delta;
		model.getRotation().z += dz * delta;

		this.rotation.x = clampRotation(model.getRotation().x);
		this.rotation.y = clampRotation(model.getRotation().y);
		this.rotation.z = clampRotation(model.getRotation().z);

		model.setRotation(this.rotation);
	}

	private float clampRotation(float rotation) {
		rotation %= 360;
		if (rotation < 0) {
			rotation += 360;
		}
		return rotation;
	}

	public Model getModel() {
		return model;
	}
}
