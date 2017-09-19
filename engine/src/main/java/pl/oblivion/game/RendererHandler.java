package pl.oblivion.game;


import pl.oblivion.shaders.RendererProgram;

import java.util.ArrayList;
import java.util.List;

public class RendererHandler {

    public static List<RendererProgram> rendererProgramList = new ArrayList<>();

    public RendererHandler() {

    }

    public void render() {
        for (RendererProgram rendererProgram : rendererProgramList) {
            rendererProgram.render();
        }
    }

    public void cleanUp() {
        for (RendererProgram rendererProgram : rendererProgramList) {
            rendererProgram.cleanUp();
        }
    }
}
