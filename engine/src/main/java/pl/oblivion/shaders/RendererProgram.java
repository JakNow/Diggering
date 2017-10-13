package pl.oblivion.shaders;

import org.joml.Matrix4f;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;

public abstract class RendererProgram {

    public ShaderProgram shaderProgram;
    public RendererHandler rendererHandler;
    public Matrix4f projectionMatrix;

    public RendererProgram(ShaderProgram shaderProgram, RendererHandler rendererHandler, Window window) {
        this.shaderProgram = shaderProgram;
        this.rendererHandler = rendererHandler;
        this.projectionMatrix = window.getProjectionMatrix();
    }

    public abstract void render(Window window, Camera camera);

    public void cleanUp() {
        shaderProgram.cleanUp();
        rendererHandler.delete();
    }

    public abstract void delete();

    public abstract RendererHandler getRendererHandler();

}
