package pl.oblivion.game;

import org.lwjgl.opengl.GL11;
import pl.oblivion.core.Window;
import pl.oblivion.shaders.RendererProgram;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	public static List<RendererProgram> rendererProgramList = new ArrayList<>();

	private Window window;

	public Renderer(Window window) {
		this.window = window;
		enableCullFace();
	}

	public void enableCullFace() {
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public void prepare() {
		glViewport(0, 0, window.getWidth(), window.getHeight());
		window.updateProjectionMatrix();
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
