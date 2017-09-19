package pl.oblivion.shaders;

import pl.oblivion.game.RendererHandler;

public abstract class RendererProgram {

    private ShaderProgram shaderProgram;

    public RendererProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        RendererHandler.rendererProgramList.add(this);
        System.out.println(2);
    }

    public abstract void render();

    public void cleanUp() {
        shaderProgram.cleanUp();
    }
}
