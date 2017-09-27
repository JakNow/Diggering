package pl.oblivion.shaders;

import pl.oblivion.game.RendererHandler;

public abstract class RendererProgram {


    public RendererProgram() {
        RendererHandler.rendererProgramList.add(this);
    }

    public abstract void render();

    public abstract void cleanUp();
}
