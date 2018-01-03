package pl.oblivion.components;

import pl.oblivion.base.Model;

public class BaseComponent {

    private final ComponentType componentType;
    private final Model model;


    public BaseComponent(Model model, ComponentType componentType){
        this.model = model;
        this.componentType = componentType;
        this.model.addComponent(this);
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public Model getModel() {
        return model;
    }
}
