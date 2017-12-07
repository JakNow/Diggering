package pl.oblivion.player;

import org.joml.Vector3f;
import pl.oblivion.base.ModelView;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Window;
import pl.oblivion.main.Config;
import pl.oblivion.staticModels.StaticModel;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends StaticModel {

    private static final float SCALE = 1f;
    private static Vector3f POSITION = new Vector3f(0, 0, 0);
    private static Vector3f ROTATION = new Vector3f(0, 0, 0);
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;

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

        upwardSpeed += Config.GRAVITY;
        moveComponent.move(dx, upwardSpeed, dz, delta);
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
        if (this.currentSpeed != 0 || isInAir)
            this.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().setMoving(true);
        else
            this.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().setMoving(false);

    }

    private void jump() {
        if (!isInAir) {
            this.upwardSpeed = Config.JUMP_POWER;
            isInAir = true;
        }
    }
}
