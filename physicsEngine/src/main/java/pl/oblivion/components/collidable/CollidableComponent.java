package pl.oblivion.components.collidable;

import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.collision.broadPhase.BroadPhase;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.core.ColliderGenerator;
import pl.oblivion.core.PhysicsState;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.shapes.TypeOfShape;

import java.util.HashMap;
import java.util.Map;

public class CollidableComponent extends BaseComponent {

    private final Model model;
    private boolean isMoveable = false;
    private CollisionShape broadCollisionShape;
    private Map<ModelPart,CollisionShape> narrowCollisionShape = new HashMap<>();


    public CollidableComponent(Model model, TypeOfShape typeOfShape, boolean isMoveable){
        this.model = model;
        this.broadCollisionShape = generateCollisionShape(typeOfShape);
        this.narrowCollisionShape = generateModelPartCollisionShape();
        this.isMoveable = isMoveable;
        BroadPhase.broadPhaseCollisionMap.add(this.model);
    }

    private CollisionShape generateCollisionShape(TypeOfShape typeOfShape){
        switch (typeOfShape){
            case AABB:
                return ColliderGenerator.createAABB(model);
            case MESH_COLLIDER:
                return ColliderGenerator.createMeshCollider(model);
            case SPHERE_COLLIDER:
                return ColliderGenerator.createSphereCollider(model);
            case CYLINDER_COLLIDER:
                return ColliderGenerator.createCylinderCollider(model);
        }
        return null;
    }

    public Map<ModelPart,CollisionShape> generateModelPartCollisionShape(){
        switch (PhysicsState.DEFAULT_NARROW_COLLISION_SHAPE){
            case AABB:
                return ColliderGenerator.createAABBMap(model);
            case MESH_COLLIDER:
                return ColliderGenerator.createMeshColliderMap(model);
            case SPHERE_COLLIDER:
                return ColliderGenerator.createSphereColliderMap(model);
            case CYLINDER_COLLIDER:
                return ColliderGenerator.createCylinderColliderMap(model);
        }
        return null;
    }
    public Model getModel() {
        return model;
    }

    public boolean isMoveable() {
        return isMoveable;
    }

    public void setMoveable(boolean moveable) {
        isMoveable = moveable;
    }

    public CollisionShape getBroadCollisionShape() {
        return broadCollisionShape;
    }

    public void setBroadCollisionShape(CollisionShape broadCollisionShape) {
        this.broadCollisionShape = broadCollisionShape;
    }

    public Map<ModelPart, CollisionShape> getNarrowCollisionShape() {
        return narrowCollisionShape;
    }

    public void setNarrowCollisionShape(Map<ModelPart, CollisionShape> narrowCollisionShape) {
        this.narrowCollisionShape = narrowCollisionShape;
    }
}
