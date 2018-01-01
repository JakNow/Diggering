package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;

public class RotateComponent extends BaseComponent {

	private final Model model;
	private Vector3f rotation;
	private float currentTurnSpeed;
	private float rotationSpeed;

	public RotateComponent(Model model) {
		this.model = model;
		this.rotation = new Vector3f(model.getRotation());
	}

	public RotateComponent(Model model, float rotationSpeed) {
		this.model = model;
		this.rotation = new Vector3f(model.getRotation());
		this.rotationSpeed = rotationSpeed;
	}

	public void update(float delta) {
		rotate(0, currentTurnSpeed, 0, delta);
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

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public float getCurrentTurnSpeed() {
		return currentTurnSpeed;
	}

	public void setCurrentTurnSpeed(float currentTurnSpeed) {
		this.currentTurnSpeed = currentTurnSpeed;
	}
}
