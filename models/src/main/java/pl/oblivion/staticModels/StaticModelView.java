package pl.oblivion.staticModels;

import pl.oblivion.base.ModelPart;
import pl.oblivion.base.ModelView;

public class StaticModelView extends ModelView {

    private final ModelPart[] modelParts;

    public StaticModelView(ModelPart... modelParts) {
        this.modelParts = modelParts;
    }

    public ModelPart[] getModelParts() {
        return modelParts;
    }

}
