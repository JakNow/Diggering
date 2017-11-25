package shapes;

/*
Interface used for CollisionShape to set up intersection between different CollisionShapes
Adding new CollisionShape requires adding new boolean to check intersection within this shape
 */
public interface Intersection {

    boolean intersection(AABB aabb);

    boolean intersection(SphereCollider sphereCollider);

    boolean intersection(CylinderCollider cylinderCollider);

    boolean intersection(CapsuleCollider capsuleCollider);

    void changeColour(boolean isIntersecting, CollisionShape collisionShape);

}
