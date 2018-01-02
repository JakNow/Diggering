package pl.oblivion.components;

public class BaseComponent {

    private final ComponentType componentType;

    public BaseComponent(ComponentType componentType){
        this.componentType = componentType;
    }

    public ComponentType getComponentType() {
        return componentType;
    }
}
