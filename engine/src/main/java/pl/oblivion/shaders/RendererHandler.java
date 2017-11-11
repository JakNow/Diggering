package pl.oblivion.shaders;

import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;

public abstract class RendererHandler {

    protected int[] bindingAttributes;

    protected RendererHandler() {
    }

    public abstract void delete();

    public abstract void prepareModel(TexturedMesh texturedMesh);

    public abstract void prepareInstance(Model model);

    public abstract void unbindTexturedMesh(TexturedMesh texturedMesh);

    public abstract void processModel(Model model);
}
