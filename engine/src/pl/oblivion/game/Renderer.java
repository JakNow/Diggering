package pl.oblivion.game;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import pl.oblivion.core.Window;
import pl.oblivion.shaders.RendererProgram;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	private static final Logger logger = Logger.getLogger(Renderer.class);
	private static List<RendererProgram> rendererProgramList = new ArrayList<>();

	private Window window;
	private float currentWidht;
	private float currentHeight;

	public static float BRIGHTNESS = 1.0f;

	public Renderer(Window window) {
		this.window = window;
		enableCullFace();
		logger.info("Creating Renderer was successful.");
	}

	private void enableCullFace() {
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public void prepare() {
		glViewport(0, 0, window.getWidth(), window.getHeight());
		if (currentWidht != window.getWidth() || currentHeight != window.getHeight()) {
			window.updateProjectionMatrix();
			currentWidht = window.getWidth();
			currentHeight = window.getHeight();
		}
		glEnable(GL_DEPTH_TEST);
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		glClearColor(Window.RED, Window.GREEN, Window.BLUE, Window.ALPHA);
	}

	public void render(Window window, Camera camera) {
		for (RendererProgram rendererProgram : rendererProgramList) {
			rendererProgram.render(window, camera);
		}
	}

	public void cleanUp() {
		for (RendererProgram rendererProgram : rendererProgramList) {
			rendererProgram.cleanUp();
			rendererProgram.delete();
		}
	}

	public void addRendererProgram(RendererProgram rendererProgram) {
		rendererProgramList.add(rendererProgram);
	}

	public void disableCullFace() {
		glDisable(GL_CULL_FACE);
	}

}
