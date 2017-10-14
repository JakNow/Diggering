package pl.oblivion.shaders;

import pl.oblivion.base.Model;
import pl.oblivion.base.ModelView;

public abstract class RendererHandler {

    protected int[] bindingAttributes;

    protected RendererHandler() {
    }

    public abstract void delete();

    public abstract void prepareModel(ModelView modelView);

    public abstract void prepareInstance(Model model);

    public abstract void unbindTexturedMesh(ModelView modelView);

    public abstract void processModel(Model model);
}
