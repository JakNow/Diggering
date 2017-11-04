package pl.oblivion.collision.narrowPhase;

import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.components.collidable.CollidableComponent;
import pl.oblivion.shapes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NarrowPhase {

    public Map<Model,List<Model>> narrowPhaseCollisionMap;
    public NarrowPhase() {
        narrowPhaseCollisionMap = new HashMap<>();
    }

    public void update() {
        for(Model model : narrowPhaseCollisionMap.keySet()){
            for(Model model2 : narrowPhaseCollisionMap.get(model)){
                Map<ModelPart,CollisionShape> cs1 = getCollisionShape(model);
                Map<ModelPart,CollisionShape> cs2 = getCollisionShape(model2);

                checkloop:
                for(ModelPart modelPart : cs1.keySet()){
                    for(ModelPart modelPart2 : cs2.keySet()){
                        if(checkCollision(cs1.get(modelPart),cs2.get(modelPart2))){
                            break checkloop;
                        }
                    }
                }
            }
        }
    }

    private Map<ModelPart,CollisionShape> getCollisionShape(Model model){
        CollidableComponent collidableComponent = model.getComponent(CollidableComponent.class);
        Map<ModelPart,CollisionShape> collisionShape = collidableComponent.getNarrowCollisionShape();

        if(collidableComponent.isMoveable()){
            for(ModelPart modelPart : collisionShape.keySet()){
                collisionShape.get(modelPart).updatePosition();
            }
        }

        return collisionShape;
    }

    private boolean checkCollision(CollisionShape cs1,CollisionShape cs2){
        switch (cs2.getClass().getName().split("\\.")[3]) {
            case "SphereCollider":
             //   System.out.println("Sphere collides");
                return cs1.intersectSphere((SphereCollider) cs2);
            case "AABB":
              //  System.out.println("AABB collides");
                return cs1.intersectAABB((AABB) cs2);
            case "CylinderCollider":
              //  System.out.println("CylinderCollider collides");
                return cs1.intersectCylinder((CylinderCollider) cs2);
            case "MeshCollider":
             //   System.out.println("MeshCollider collides");
                return cs1.intersectMesh((MeshCollider) cs2);
        }
        return false;
    }
}
