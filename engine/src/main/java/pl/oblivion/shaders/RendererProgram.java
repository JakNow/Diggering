package pl.oblivion.shaders;

import org.joml.Matrix4f;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;

public abstract class RendererProgram {

    private ShaderProgram shader;
    private RendererHandler rendererHandler;
    private Matrix4f projectionMatrix;


    public RendererProgram(ShaderProgram shader, RendererHandler rendererHandler, Window window) {
        this.shader = shader;
        this.rendererHandler = rendererHandler;
        this.projectionMatrix = window.getProjectionMatrix();
    }

    public abstract void render(Window window, Camera camera);

    public abstract void prepare(Window window, Camera camera);

    public void cleanUp() {
        shader.cleanUp();
        rendererHandler.delete();
    }

    public abstract void delete();

    public abstract void end();

    public abstract RendererHandler getRendererHandler();

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
