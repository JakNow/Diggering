package pl.oblivion.game;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWScrollCallback;
import pl.oblivion.core.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

	private final Vector2d previousPos;
	private final Vector2d currentPos;
	private final Vector2f displVec;
	double mouseYoffset;
	private double previousWheel;
	private double currentWheel;
	private float wheelVec;
	private boolean inWindow = false;
	private boolean leftButtonPressed = false;
	private boolean rightButtonPressed = false;
	private boolean middleButtonPressed = false;
	private boolean wheelScroll = false;
	private Window window;
	private GLFWScrollCallback sCallback;

	public MouseInput(Window window) {
		this.window = window;
		previousPos = new Vector2d(- 1, - 1);
		currentPos = new Vector2d(0, 0);
		displVec = new Vector2f();

		this.init();
	}

	private void init() {
		glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
			currentPos.x = xpos;
			currentPos.y = ypos;
		});

		glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
			inWindow = entered;
		});

		glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
			leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
			rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
			middleButtonPressed = button == GLFW_MOUSE_BUTTON_3 && action == GLFW_PRESS;
		});

		glfwSetScrollCallback(window.getWindowHandle(), (windowHandle, xoffset, yoffset) -> {
			currentWheel += yoffset;

		});
	}

	public Vector2f getDisplVec() {
		return displVec;
	}

	public float getWheelY() {
		return wheelVec;
	}

	public void input() {
		displVec.x = 0;
		displVec.y = 0;

		if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
			double deltaX = currentPos.x - previousPos.x;
			double deltaY = currentPos.y - previousPos.y;

			boolean rotateX = deltaX != 0;
			boolean rotateY = deltaY != 0;

			if (rotateX) { displVec.y = (float) deltaX; }

			if (rotateY) { displVec.x = (float) deltaY; }
		}

		previousPos.x = currentPos.x;
		previousPos.y = currentPos.y;

		wheelVec = 0;

		if (previousWheel != 0 && inWindow) {
			double deltaY = currentWheel - previousWheel;
			boolean rotateY = deltaY != 0;

			if (rotateY) { wheelVec = (float) deltaY; }
		}
		previousWheel = currentWheel;

	}

	public boolean isLeftButtonPressed() {
		return leftButtonPressed;
	}

	public boolean isRightButtonPressed() {
		return rightButtonPressed;
	}

	public boolean isMiddleButtonPressed() {
		return middleButtonPressed;
	}
}
