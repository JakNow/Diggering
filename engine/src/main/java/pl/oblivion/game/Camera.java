package pl.oblivion.game;

import org.joml.Vector3f;
import pl.oblivion.core.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private final Vector3f position;
    private final Vector3f rotation;
    private final Window window;

    public Camera(Window window) {
        this.window = window;
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Window window, Vector3f position, Vector3f rotation) {
        this.window = window;
        this.position = position;
        this.rotation = rotation;
    }

    public void update(float speed) {
        if (window.isKeyPressed(GLFW_KEY_W))
            this.position.z -= speed;
        if (window.isKeyPressed(GLFW_KEY_S)) {
            this.position.z += speed;
        }

        if (window.isKeyPressed(GLFW_KEY_A))
            this.position.x -= speed;
        if (window.isKeyPressed(GLFW_KEY_D))
            this.position.x += speed;

        if (window.isKeyPressed(GLFW_KEY_Q))
            this.position.y -= speed;
        if (window.isKeyPressed(GLFW_KEY_E))
            this.position.y += speed;
    }

    private void move(float offsetX, float offsetY, float offsetZ) {
        if (offsetZ != 0) {
            this.position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            this.position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if (offsetX != 0) {
            this.position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            this.position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    private void rotate(float offsetX, float offsetY, float offsetZ) {
        this.rotation.x += offsetX;
        this.rotation.y += offsetY;
        this.rotation.z += offsetZ;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
}
