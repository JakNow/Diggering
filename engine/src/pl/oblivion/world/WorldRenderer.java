package pl.oblivion.world;

import org.lwjgl.opengl.GL11;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;
import pl.oblivion.game.Renderer;
import pl.oblivion.lighting.Light;
import pl.oblivion.shaders.RendererProgram;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.staticModels.StaticModel;

import java.util.List;

public class WorldRenderer extends RendererProgram {

	private static WorldShader shader = new WorldShader();
	private static WorldRendererHandler rendererHandler = new WorldRendererHandler(shader);

	public WorldRenderer(Window window) {
		super(shader, rendererHandler, window);
		shader.start();
		shader.projectionMatrix.loadMatrix(this.getProjectionMatrix());
		shader.brightness.loadFloat(Renderer.BRIGHTNESS);
		shader.stop();
	}

	@Override
	public void render(Window window, Camera camera) {
		this.prepare(window, camera);
		for (TexturedMesh texturedMesh : rendererHandler.getTexturedMeshMap().keySet()) {
			rendererHandler.prepareModel(texturedMesh);
			List<StaticModel> batch = rendererHandler.getTexturedMeshMap().get(texturedMesh);
			for (StaticModel staticModel : batch) {
				rendererHandler.prepareInstance(staticModel);
				GL11.glDrawElements(GL11.GL_TRIANGLES, texturedMesh.getMesh().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			rendererHandler.unbindTexturedMesh(texturedMesh);
		}
		end();
	}

	@Override
	public void prepare(Window window, Camera camera) {
		shader.start();
		shader.projectionMatrix.loadMatrix(window.getProjectionMatrix());
		shader.loadViewMatrix(camera);
		if(rendererHandler.getLight()!=null) //will be change to set of lights later
			shader.light.loadLight(rendererHandler.getLight());
	}

	@Override
	public void delete() {
		rendererHandler.delete();
	}

	@Override
	public void end() {
		shader.stop();
	}

	@Override
	public WorldRendererHandler getRendererHandler() {
		return rendererHandler;
	}

	@Override
	public WorldShader getShader() {
		return shader;
	}
}
