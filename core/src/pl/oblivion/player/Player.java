package pl.oblivion.player;

import org.joml.Vector3f;
import pl.oblivion.base.ModelView;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Window;
import pl.oblivion.main.Main;
import pl.oblivion.staticModels.StaticModel;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends StaticModel {

	private static final float SCALE = 1f;
	private static Vector3f POSITION = new Vector3f(0, 0, 0);
	private static Vector3f ROTATION = new Vector3f(0, 0, 0);
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardSpeed = 0;
	private float currentSideSpeed = 0;

	private float gravity = Float.parseFloat(Main.properties.getProperty("world.gravity"));
	private float runSpeed = Float.parseFloat(Main.properties.getProperty("player.run_speed"));
	private float rotationSpeed = Float.parseFloat(Main.properties.getProperty("player.rotation_speed"));
	private float jumpPower= Float.parseFloat(Main.properties.getProperty("player.jump_power"));

	private boolean isInAir = false;
	private MoveComponent moveComponent;
	private RotateComponent rotateComponent;

	public Player(ModelView modelView) {
		super(POSITION, ROTATION, SCALE, modelView);

		moveComponent = new MoveComponent(this);
		rotateComponent = new RotateComponent(this);

		this.addComponent(moveComponent);
		this.addComponent(rotateComponent);
	}

	public void update(Window window, float delta) {
		checkInputs(window);
		rotateComponent.rotate(0, currentTurnSpeed, 0, delta);
		float dx = (float) (currentSpeed * Math.sin(Math.toRadians(super.getRotation().y)));
		float dz = (float) (currentSpeed * Math.cos(Math.toRadians(super.getRotation().y)));

		dx += (float) (currentSideSpeed * Math.sin(Math.toRadians(super.getRotation().y+90)));
		dz += (float) (currentSideSpeed * Math.cos(Math.toRadians(super.getRotation().y+90)));

		upwardSpeed -= gravity;
		moveComponent.move(dx, upwardSpeed, dz, delta);
		if (super.getPosition().y < 0) {
			upwardSpeed = 0;
			super.getPosition().y = 0;
			isInAir = false;
		}

	}

	private void checkInputs(Window window) {
		if (window.isKeyPressed(GLFW_KEY_W)) {
			this.currentSpeed = runSpeed;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			this.currentSpeed = - runSpeed;
		} else {
			this.currentSpeed = 0;
		}

		if(window.isKeyPressed(GLFW_KEY_Q)){
			this.currentSideSpeed = runSpeed;
		} else if (window.isKeyPressed(GLFW_KEY_E)){
			this.currentSideSpeed = -runSpeed;
		} else{
			this.currentSideSpeed = 0;
		}

		if (window.isKeyPressed(GLFW_KEY_D)) {
			this.currentTurnSpeed = - rotationSpeed;
		} else if (window.isKeyPressed(GLFW_KEY_A)) {
			this.currentTurnSpeed = rotationSpeed;
		} else {
			this.currentTurnSpeed = 0;
		}

		if (window.isKeyPressed(GLFW_KEY_SPACE)) {
			jump();
		}
		if (this.currentSpeed != 0 || isInAir) {
			this.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().setMoving(true);
		} else { this.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().setMoving(false); }

	}

	private void jump() {
		if (! isInAir) {
			this.upwardSpeed = jumpPower;
			isInAir = true;
		}
	}
}
