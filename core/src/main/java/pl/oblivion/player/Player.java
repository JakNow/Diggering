package pl.oblivion.player;

import org.joml.Vector3f;
import pl.oblivion.base.ModelView;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Window;
import pl.oblivion.main.Config;
import pl.oblivion.staticModels.StaticModel;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends StaticModel {

    private static final float SCALE = 1f;
    private static Vector3f POSITION = new Vector3f(0, 0, -10);
    private static Vector3f ROTATION = new Vector3f(0, 0, 0);
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;

    private boolean isInAir = false;
    private MoveComponent moveComponent;
    private RotateComponent rotateComponent;

    public Player(ModelView modelView) {
        super(POSITION, ROTATION, SCALE, modelView);

        moveComponent = new MoveComponent(this, Config.RUN_SPEED);
        rotateComponent = new RotateComponent(this, Config.ROTATION_SPEED);

        this.addComponent(moveComponent);
        this.addComponent(rotateComponent);
    }

    public void update(Window window, float delta) {
        checkInputs(window);
        rotateComponent.rotate(new Vector3f(0, currentTurnSpeed * delta, 0));
        float distance = currentSpeed * delta;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y)));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y)));

        upwardSpeed += Config.GRAVITY;
        moveComponent.move(dx, upwardSpeed * delta, dz);
        if (super.getPosition().y < 0) {
            upwardSpeed = 0;
            super.getPosition().y = 0;
            isInAir = false;
        }

    }

    private void checkInputs(Window window) {
        if (window.isKeyPressed(GLFW_KEY_W)) {
            this.currentSpeed = Config.RUN_SPEED;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            this.currentSpeed = -Config.RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (window.isKeyPressed(GLFW_KEY_D)) {
            this.currentTurnSpeed = -Config.ROTATION_SPEED;
        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            this.currentTurnSpeed = Config.ROTATION_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            jump();
        }
    }

    private void jump() {
        if (!isInAir) {
            this.upwardSpeed = Config.JUMP_POWER;
            isInAir = true;
        }
    }
}
