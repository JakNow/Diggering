package pl.oblivion.staticModels;


import org.lwjgl.opengl.GL11;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;
import pl.oblivion.shaders.RendererProgram;

import java.util.List;

public class StaticRenderer extends RendererProgram {

    private static StaticShader shader = new StaticShader();
    private static StaticRendererHandler rendererHandler = new StaticRendererHandler(shader);

    public StaticRenderer(Window window) {
        super(shader, rendererHandler, window);

        shader.start();
        shader.projectionMatrix.loadMatrix(this.getProjectionMatrix());
        shader.stop();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
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
    }

    @Override
    public void end() {
        shader.stop();
    }

    @Override
    public void delete() {
        rendererHandler.delete();
    }

    @Override
    public StaticRendererHandler getRendererHandler() {
        return rendererHandler;
    }
}
