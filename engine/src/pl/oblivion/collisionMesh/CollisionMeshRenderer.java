package pl.oblivion.collisionMesh;

import org.lwjgl.opengl.GL11;
import pl.oblivion.base.Model;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;
import pl.oblivion.shaders.RendererHandler;
import pl.oblivion.shaders.RendererProgram;
import pl.oblivion.shapes.CollisionShape;

import java.util.List;

public class CollisionMeshRenderer extends RendererProgram {

	public static boolean ENABLE_RENDER = true;
	private static CollisionMeshShader shader = new CollisionMeshShader();
	private static CollisionMeshHandler rendererHandler = new CollisionMeshHandler(shader);

	public CollisionMeshRenderer(Window window) {
		super(shader, rendererHandler, window);
		shader.start();
		shader.projectionMatrix.loadMatrix(this.getProjectionMatrix());
		shader.stop();

	}

	@Override
	public void render(Window window, Camera camera) {
		if (ENABLE_RENDER) {
			this.prepare(window, camera);
			for (CollisionShape collisionShape : rendererHandler.getCollisionShapeListMap().keySet()) {
				rendererHandler.prepareModel(collisionShape);
				List<Model> batch = rendererHandler.getCollisionShapeListMap().get(collisionShape);
				for (Model model : batch) {
					rendererHandler.prepareInstance(model);
					GL11.glDrawElements(GL11.GL_TRIANGLES, collisionShape.getTexturedMesh().getMesh().getIndexCount(),
							GL11.GL_UNSIGNED_INT, 0);
				}
				rendererHandler.unbindCollisionShape(collisionShape);
			}
			end();
		}
	}

	@Override
	public void prepare(Window window, Camera camera) {
		shader.start();
		shader.projectionMatrix.loadMatrix(window.getProjectionMatrix());
		shader.loadViewMatrix(camera);

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
	public RendererHandler getRendererHandler() {
		return rendererHandler;
	}

}
