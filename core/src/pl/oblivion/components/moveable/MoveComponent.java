package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;

public class MoveComponent extends BaseComponent {

	private final Model model;
	private static final ComponentType componentType = ComponentType.MOVE;
	private float currentSpeed;
	private float currentSideSpeed;
	private float upwardSpeed;
	private boolean isInAir = false;

	private float gravity;
	private float runSpeed;
	private float jumpPower;

	public MoveComponent(Model model) {
		super(componentType);
		this.model = model;
	}

	public MoveComponent(Model model, float gravity, float runSpeed, float jumpPower) {
		super(componentType);
		this.model = model;
		this.gravity = gravity;
		this.runSpeed = runSpeed;
		this.jumpPower = jumpPower;
	}

	public void update(float delta) {
		float dx = (float) (currentSpeed * Math.sin(Math.toRadians(model.getRotation().y)));
		float dz = (float) (currentSpeed * Math.cos(Math.toRadians(model.getRotation().y)));

		dx += (float) (currentSideSpeed * Math.sin(Math.toRadians(model.getRotation().y + 90)));
		dz += (float) (currentSideSpeed * Math.cos(Math.toRadians(model.getRotation().y + 90)));

		upwardSpeed -= gravity;
		move(new Vector3f(dx, upwardSpeed, dz), delta);
		if (model.getPosition().y < model.getHeight()) {
			upwardSpeed = 0;
			model.getPosition().y = model.getHeight();
			isInAir = false;
		}
	}

	private void move(Vector3f move, float delta) {
		model.getPosition().add(move.mul(delta));
	}

	private void move(float dx, float dy, float dz, float delta) {
		model.getPosition().x += dx * delta;
		model.getPosition().y += dy * delta;
		model.getPosition().z += dz * delta;
	}

	public void jump() {
		if (! isInAir) {
			this.upwardSpeed = jumpPower;
			isInAir = true;
		}
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(float runSpeed) {
		this.runSpeed = runSpeed;
	}

	public float getJumpPower() {
		return jumpPower;
	}

	public void setJumpPower(float jumpPower) {
		this.jumpPower = jumpPower;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public float getUpwardSpeed() {
		return upwardSpeed;
	}

	public void setUpwardSpeed(float upwardSpeed) {
		this.upwardSpeed = upwardSpeed;
	}

	public boolean isInAir() {
		return isInAir;
	}

	public void setInAir(boolean inAir) {
		isInAir = inAir;
	}

	public Model getModel() {
		return model;
	}

	public float getCurrentSideSpeed() {
		return currentSideSpeed;
	}

	public void setCurrentSideSpeed(float currentSideSpeed) {
		this.currentSideSpeed = currentSideSpeed;
	}
}

