package pl.oblivion.dataStructure;

import pl.oblivion.base.Model;

public class ModelPair {

    private final Model model1;
    private final Model model2;

    public ModelPair(Model model1, Model model2) {
        this.model1 = model1;
        this.model2 = model2;
    }

    public Model getModel1() {
        return model1;
    }

    public Model getModel2() {
        return model2;
    }
}
