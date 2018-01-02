package pl.oblivion.main;

import pl.oblivion.components.ComponentType;
import pl.oblivion.components.collision.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Inputs;
import pl.oblivion.core.Window;
import pl.oblivion.player.Player;

import static org.lwjgl.glfw.GLFW.*;

public class SimpleInputs extends Inputs {

	private MoveComponent playerMoveComponent;
	private RotateComponent playerRotateComponent;
	private CollisionComponent playerCollisionComponent;

	public SimpleInputs(Window window, Player player) {
		super(window);
		this.playerMoveComponent = player.getComponent(ComponentType.MOVE);
		this.playerRotateComponent = player.getComponent(ComponentType.ROTATE);
		this.playerCollisionComponent = player.getComponent(ComponentType.COLLISION);
	}

	public void checkPlayerInputs() {
		//MOVEMENT
		if (getWindow().isKeyPressed(GLFW_KEY_W)) {
			playerMoveComponent.setCurrentSpeed(playerMoveComponent.getRunSpeed());
		} else if (getWindow().isKeyPressed(GLFW_KEY_S)) {
			playerMoveComponent.setCurrentSpeed(- playerMoveComponent.getRunSpeed());
		} else {
			playerMoveComponent.setCurrentSpeed(0);
		}

		if (getWindow().isKeyPressed(GLFW_KEY_Q)) {
			playerMoveComponent.setCurrentSideSpeed(playerMoveComponent.getRunSpeed());

		} else if (getWindow().isKeyPressed(GLFW_KEY_E)) {
			playerMoveComponent.setCurrentSideSpeed(- playerMoveComponent.getRunSpeed());
		} else {
			playerMoveComponent.setCurrentSideSpeed(0);
		}

		//JUMPING
		if (getWindow().isKeyPressed(GLFW_KEY_SPACE)) {
			playerMoveComponent.jump();
		}

		//ROTATION
		if (getWindow().isKeyPressed(GLFW_KEY_D)) {
			playerRotateComponent.setCurrentTurnSpeed(- playerRotateComponent.getRotationSpeed());
		} else if (getWindow().isKeyPressed(GLFW_KEY_A)) {
			playerRotateComponent.setCurrentTurnSpeed(playerRotateComponent.getRotationSpeed());
		} else {
			playerRotateComponent.setCurrentTurnSpeed(0);
		}
	}
}
