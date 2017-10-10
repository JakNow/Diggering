package pl.oblivion.base;

public abstract class ModelView {

    private final ModelPart[] modelParts;

    public ModelView(ModelPart... modelParts) {
        this.modelParts = modelParts;
    }

    public ModelPart[] getModelParts() {
        return modelParts;
    }
}
