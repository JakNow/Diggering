package pl.oblivion.player;

import pl.oblivion.base.Model;
import pl.oblivion.colliders.*;
import pl.oblivion.components.ComponentType;
import pl.oblivion.components.collision.CollisionComponent;
import pl.oblivion.core.broadPhase.Octree;

import java.util.ArrayList;
import java.util.List;

public class PlayerCollisionComponent extends CollisionComponent {

    private List<Model> collidableObjects = new ArrayList<>();
    private List<CollidedPairs> collidedPairs = new ArrayList<>();

    PlayerCollisionComponent(Model model, Octree octree, BasicCollider broadCollider, BasicCollider narrowCollider) {
        super(model, octree, broadCollider, narrowCollider);
    }

    @Override
    public void update(float delta) {
        getOctree().updateModelsPosition(getModel());
        collidableObjects = getOctree().getCollidableModelsList(getModel());
        checkForPossibleCollision();
    }

    private void checkForPossibleCollision() {
        getBroadCollider().update();
        collidedPairs.clear();
        for (Model model : collidableObjects) {
            if (model == getModel())
                continue;
            collisionCheck(model);
        }
        if (collidedPairs.size() != 0)
            System.out.println(collidedPairs.size());

    }

    private void collisionCheck(final Model model) {

        CollisionComponent modelsCollisionComponent = model.getComponent(ComponentType.COLLISION);
        BasicCollider modelsBroadCollider = modelsCollisionComponent.getBroadCollider();
        switch (modelsBroadCollider.getColliderType()) {
            case AABB:
                if (this.getBroadCollider().intersection((AABB) modelsBroadCollider)) {
                    collidedPairs.add(new CollidedPairs(this.getModel(), model));
                }
                break;
            case SPHERE:
                if (this.getBroadCollider().intersection((SphereCollider) modelsBroadCollider)) {
                    collidedPairs.add(new CollidedPairs(this.getModel(), model));
                }

                break;
            case CYLINDER:
                if (this.getBroadCollider().intersection((CylinderCollider) modelsBroadCollider)) {
                    collidedPairs.add(new CollidedPairs(this.getModel(), model));
                }
                break;
            case MESH:
                if (this.getBroadCollider().intersection((MeshCollider) modelsBroadCollider)) {
                    collidedPairs.add(new CollidedPairs(this.getModel(), model));
                }
                break;
        }


    }

    public class CollidedPairs {
        private final Model model1;
        private final Model model2;

        public CollidedPairs(Model model1, Model model2) {
            this.model1 = model1;
            this.model2 = model2;
        }


    }
}
