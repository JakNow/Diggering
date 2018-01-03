package pl.oblivion.components.collision;

import pl.oblivion.base.Model;
import pl.oblivion.colliders.BasicCollider;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;
import pl.oblivion.core.broadPhase.Octree;

import java.util.List;

public abstract class CollisionComponent extends BaseComponent {

    private final Octree octree;
    private static final ComponentType componnentType = ComponentType.COLLISION;
    private BasicCollider broadCollider;
    private BasicCollider narrowCollider;


    public CollisionComponent(Model model, Octree octree, BasicCollider broadCollider, BasicCollider narrowCollider) {
        super(model,componnentType);
        this.octree = octree;
        this.broadCollider = broadCollider;
        if (narrowCollider == null) {
            this.narrowCollider = broadCollider;
        } else {
            this.narrowCollider = narrowCollider;
        }
        this.octree.insertObject(this.getModel());
    }

    public abstract void update(float delta);

    public List<Model> getCollidableModelsList() {
        return octree.getCollidableModelsList(getModel());
    }

    public Octree getOctree() {
        return octree;
    }

    public BasicCollider getBroadCollider() {
        return broadCollider;
    }

    public BasicCollider getNarrowCollider() {
        return narrowCollider;
    }
}
