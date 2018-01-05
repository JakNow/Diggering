package pl.oblivion.player;

import pl.oblivion.base.Model;
import pl.oblivion.colliders.*;
import pl.oblivion.components.ComponentType;
import pl.oblivion.components.collision.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.core.broadPhase.Octree;

import java.util.ArrayList;
import java.util.List;

public class PlayerCollisionComponent extends CollisionComponent {

    private List<Model> collidableObjects = new ArrayList<>();
    private MoveComponent moveComponent;

    PlayerCollisionComponent(Model model, Octree octree, BasicCollider broadCollider, BasicCollider narrowCollider) {
        super(model, octree, broadCollider, narrowCollider);

        moveComponent = getModel().getComponent(ComponentType.MOVE);
    }

    @Override
    public void update(float delta) {
        getOctree().updateModelsPosition(getModel());
        collidableObjects = getOctree().getCollidableModelsList(getModel());
        checkForPossibleCollision(delta);
    }

    private void checkForPossibleCollision(float delta) {
        //TODO calculate before move position to find if spot is moveable
        getBroadCollider().update(moveComponent.getModel().getPosition());
        for (Model model : collidableObjects) {
            if (model == getModel())
                continue;
            collisionCheck(model);
        }


    }

    private void collisionCheck(final Model model) {

        CollisionComponent modelsCollisionComponent = model.getComponent(ComponentType.COLLISION);
        BasicCollider modelsBroadCollider = modelsCollisionComponent.getBroadCollider();
        boolean collisionOccures = false;
        switch (modelsBroadCollider.getColliderType()) {
            case AABB:
                if (this.getBroadCollider().intersection((AABB) modelsBroadCollider)) {
                    collisionOccures = true;
                }
                break;
            case SPHERE:
                if (this.getBroadCollider().intersection((SphereCollider) modelsBroadCollider)) {
                    collisionOccures = true;
                }
                break;
            case CYLINDER:
                if (this.getBroadCollider().intersection((CylinderCollider) modelsBroadCollider)) {
                    collisionOccures = true;
                }
                break;
            case MESH:
                if (this.getBroadCollider().intersection((MeshCollider) modelsBroadCollider)) {
                    collisionOccures = true;
                }
                break;
        }
        if (collisionOccures) {
            reactForCollision();
        }
    }

    private void reactForCollision() {
        //TODO react for collision
    }
}

