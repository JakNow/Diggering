package pl.oblivion.staticModels;

import pl.oblivion.shaders.RendererHandler;

public class StaticRendererHandler extends RendererHandler {

    private StaticModel staticModel;

    public StaticRendererHandler() {

    }

    public StaticModel getStaticModel() {
        return staticModel;
    }

    public void setStaticModel(StaticModel staticModel) {
        this.staticModel = staticModel;
    }

    @Override
    public void delete() {

    }
}
