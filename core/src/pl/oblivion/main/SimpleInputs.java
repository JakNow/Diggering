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
            playerMoveComponent.getVelocityGoal().z = 15;
        } else if (getWindow().isKeyPressed(GLFW_KEY_S)) {
            playerMoveComponent.getVelocityGoal().z = -15;
        } else if (getWindow().isKeyReleased(GLFW_KEY_W) || getWindow().isKeyReleased(GLFW_KEY_S)) {
            playerMoveComponent.getVelocityGoal().z = 0;
        }


        if (getWindow().isKeyPressed(GLFW_KEY_A)) {
            playerMoveComponent.getVelocityGoal().x = 15;
        } else if (getWindow().isKeyPressed(GLFW_KEY_D)) {
            playerMoveComponent.getVelocityGoal().x = -15;
        } else if (getWindow().isKeyReleased(GLFW_KEY_A) || getWindow().isKeyReleased(GLFW_KEY_D)) {
            playerMoveComponent.getVelocityGoal().x = 0;
        }

        //JUMPING
        if (getWindow().isKeyPressed(GLFW_KEY_SPACE)) {
        }
    }
}
