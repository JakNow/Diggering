package pl.oblivion.components.collision;

import pl.oblivion.base.Model;
import pl.oblivion.colliders.BasicCollider;
import pl.oblivion.core.broadPhase.Octree;

public class StaticCollisionComponent extends CollisionComponent {

    public StaticCollisionComponent(Model model, Octree octree, BasicCollider broadCollider) {
        super(model, octree, broadCollider, null);
    }

    @Override
    public void update(float delta) {

    }
}
