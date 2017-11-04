package pl.oblivion.collision.broadPhase;

import pl.oblivion.base.Model;
import pl.oblivion.collision.narrowPhase.NarrowPhase;
import pl.oblivion.components.collidable.CollidableComponent;
import pl.oblivion.shapes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadPhase {

    public static List<Model> broadPhaseCollisionMap;
    private boolean[][] checkedPairs;

    private NarrowPhase narrowPhase;

    public BroadPhase(NarrowPhase narrowPhase) {
        this.narrowPhase = narrowPhase;
        broadPhaseCollisionMap = new ArrayList<>();
    }

    public void update() {
        narrowPhase.narrowPhaseCollisionMap.clear();
        checkedPairs = new boolean[broadPhaseCollisionMap.size()][broadPhaseCollisionMap.size()];
        if (!broadPhaseCollisionMap.isEmpty()) {
            for(int i = 0; i < broadPhaseCollisionMap.size();i++){
                List<Model> collideModels = new ArrayList<>();
                CollisionShape cs1 = getCollisionShape(broadPhaseCollisionMap.get(i));
                if(broadPhaseCollisionMap.get(i).getComponent(CollidableComponent.class).isMoveable()){
                    for(int j = 0; j< broadPhaseCollisionMap.size();j++){
                      if(i==j || checkedPairs[i][j])
                          continue;

                       CollisionShape cs2 = getCollisionShape(broadPhaseCollisionMap.get(j));
                        if (checkCollision(cs1, cs2)) {
                            collideModels.add(broadPhaseCollisionMap.get(j));
                            checkedPairs[i][j] = true;
                        }
                    }
                }
                if(!collideModels.isEmpty())
                    narrowPhase.narrowPhaseCollisionMap.put(broadPhaseCollisionMap.get(i),collideModels);

            }
        }

        if (!narrowPhase.narrowPhaseCollisionMap.isEmpty())
            narrowPhase.update();
    }

    private CollisionShape getCollisionShape(Model model){
        CollidableComponent collidableComponent = model.getComponent(CollidableComponent.class);
        CollisionShape collisionShape = collidableComponent.getBroadCollisionShape();
        if(collidableComponent.isMoveable())
            collisionShape.updatePosition();

        return collisionShape;
    }
    private boolean checkCollision(CollisionShape cs1, CollisionShape cs2) {
        switch (cs2.getClass().getName().split("\\.")[3]) {
            case "SphereCollider":
                return cs1.intersectSphere((SphereCollider) cs2);
            case "AABB":
                return cs1.intersectAABB((AABB) cs2);
            case "CylinderCollider":
                return cs1.intersectCylinder((CylinderCollider) cs2);
            case "MeshCollider":
                return cs1.intersectMesh((MeshCollider) cs2);
        }
        return false;
    }
}
