package pl.oblivion.collision.broadPhase;

import pl.oblivion.base.Model;
import pl.oblivion.collision.narrowPhase.NarrowPhase;
import pl.oblivion.components.collidable.CollidableComponent;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.shapes.SphereCollider;

import java.util.HashMap;
import java.util.Map;

public class BroadPhase {

    public static Map<Model, CollidableComponent> broadPhaseCollisionMap;

    private NarrowPhase narrowPhase;

    public BroadPhase(NarrowPhase narrowPhase) {
        this.narrowPhase = narrowPhase;
        broadPhaseCollisionMap = new HashMap<>();
    }

    public void update() {
        narrowPhase.narrowPhaseCollisionMap.clear();
        if (!broadPhaseCollisionMap.isEmpty()) {
            for (Model model : broadPhaseCollisionMap.keySet()) {
                if (model.getComponent(CollidableComponent.class).isMoveAble()) {
                    for (Model model2 : broadPhaseCollisionMap.keySet()) {
                        if (!model.equals(model2)) {
                            CollisionShape cs1 = model.getComponent(CollidableComponent.class).getModelCollisionShape();
                            CollisionShape cs2 = model2.getComponent(CollidableComponent.class).getModelCollisionShape();
                            if (checkCollision(cs1, cs2)) {
                                narrowPhase.narrowPhaseCollisionMap.put(model, model);
                                narrowPhase.narrowPhaseCollisionMap.put(model2, model2);

                            }
                        }
                    }
                }
            }
        }
        if (!narrowPhase.narrowPhaseCollisionMap.isEmpty()) {
            narrowPhase.update();
        }
    }

    private boolean checkCollision(CollisionShape cs1, CollisionShape cs2) {
        switch (cs1.getClass().getName().split("\\.")[3]) {
            case "SphereCollider":
                return cs1.intersectSphere((SphereCollider) cs2);
        }
        return false;
    }
}
