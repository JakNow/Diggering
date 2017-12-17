package pl.oblivion.game;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyInput extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65536];

	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
	}
}