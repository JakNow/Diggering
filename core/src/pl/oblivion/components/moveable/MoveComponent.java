package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;

public class MoveComponent extends BaseComponent {

	private static final ComponentType componentType = ComponentType.MOVE;
	private float currentSpeed;
	private float currentSideSpeed;
	private float upwardSpeed;
	private boolean isInAir = false;

	private float gravity;
	private float runSpeed;
	private float jumpPower;

	private Vector3f velocity;
	private Vector3f newPosition;
	public MoveComponent(Model model) {
		super(model,componentType);
		velocity = new Vector3f();
	}

	public MoveComponent(Model model, float gravity, float runSpeed, float jumpPower) {
		super(model,componentType);
		this.gravity = gravity;
		this.runSpeed = runSpeed;
		this.jumpPower = jumpPower;
		velocity = new Vector3f();
		this.newPosition = model.getPosition();
	}

	public void update() {
		float dx = (float) (currentSpeed * Math.sin(Math.toRadians(this.getModel().getRotation().y)));
		float dz = (float) (currentSpeed * Math.cos(Math.toRadians(this.getModel().getRotation().y)));

		dx += (float) (currentSideSpeed * Math.sin(Math.toRadians(this.getModel().getRotation().y + 90)));
		dz += (float) (currentSideSpeed * Math.cos(Math.toRadians(this.getModel().getRotation().y + 90)));

		if(upwardSpeed>0)
		upwardSpeed -= gravity;
		if (this.getModel().getPosition().y < this.getModel().getHeight()) {
			upwardSpeed = 0;
			this.getModel().getPosition().y = this.getModel().getHeight();
			isInAir = false;
		}
		this.setVelocity(dx,upwardSpeed,dz);
	}

	public void move() {
		this.getModel().setPosition(this.getNewPosition());
	}

	public void calculateNewPosition(float delta){
		this.newPosition = new Vector3f(this.getModel().getPosition().add(velocity.mul(delta)));
	}

	public Vector3f getNewPosition() {
		return newPosition;
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

	public float getCurrentSideSpeed() {
		return currentSideSpeed;
	}

	public void setCurrentSideSpeed(float currentSideSpeed) {
		this.currentSideSpeed = currentSideSpeed;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public void setVelocity(float x, float y, float z){
		this.velocity.x = x;
		this.velocity.y = y;
		this.velocity.z = z;
	}

	public void setNewPosition(Vector3f newPosition) {
		this.newPosition = newPosition;
	}
}

