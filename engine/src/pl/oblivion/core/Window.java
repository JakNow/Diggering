package pl.oblivion.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

	private final Matrix4f projectionMatrix;
	// The window handle
	private long window;
	private int width;
	private int height;
	private String title;
	private boolean resized;
	private boolean vSync;

	public Window() {
		this.width = Config.WIDTH;
		this.height = Config.HEIGHT;
		this.title = Config.TITLE;
		this.resized = false;
		this.vSync = true;
		projectionMatrix = new Matrix4f();
		this.init();
	}

	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (! glfwInit()) { throw new IllegalStateException("Unable to initialize GLFW"); }

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		if (System.getProperty("os.name").equals("Mac OS X")) {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		}
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);

		glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.setResized(true);
		});

		if (window == NULL) { throw new RuntimeException("Failed to create the GLFW window"); }

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		if (isvSync()) {
			// Enable v-sync
			glfwSwapInterval(1);
		}

		// Make the window visible
		glfwShowWindow(window);
		GL.createCapabilities();

		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	}

	public boolean isvSync() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}

	public static Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
		float aspectRatio = (float) width / (float) height;
		return matrix.setPerspective(Config.FOV, aspectRatio, Config.NEAR, Config.FAR);
	}

	public void destroy() {

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void updateAfter() {
		glfwSwapBuffers(window); // swap the color buffers
		glfwPollEvents();
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(window);
	}

	public boolean isResized() {
		return resized;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public Matrix4f updateProjectionMatrix() {
		float aspectRatio = (float) width / (float) height;
		return projectionMatrix.setPerspective(Config.FOV, aspectRatio, Config.NEAR, Config.FAR);
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public long getWindowHandle() {
		return window;
	}

	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
