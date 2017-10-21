package pl.oblivion.staticModels;


import pl.oblivion.base.ModelView;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;
import pl.oblivion.shaders.RendererProgram;

public class StaticRenderer extends RendererProgram {

    private static StaticShader shader = new StaticShader();
    private static StaticRendererHandler rendererHandler = new StaticRendererHandler(shader);

    public StaticRenderer(Window window) {
        super(shader, rendererHandler, window);

        shader.start();
        shader.projectionMatrix.loadMatrix(this.getProjectionMatrix());
        shader.stop();

    }


    @Override
    public void render(Window window, Camera camera) {
        prepare(window, camera);
        for (ModelView modelView : rendererHandler.getModels().keySet()) {
            rendererHandler.prepareModel(modelView);
            rendererHandler.unbindTexturedMesh(modelView);
        }
        end();
    }

    private void prepare(Window window, Camera camera) {
        shader.start();
        shader.projectionMatrix.loadMatrix(window.getProjectionMatrix());
        shader.loadViewMatrix(camera);

    }

    private void end() {
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
