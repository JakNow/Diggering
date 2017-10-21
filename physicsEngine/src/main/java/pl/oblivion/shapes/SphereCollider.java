package pl.oblivion.shapes;

import org.joml.Vector3f;

public class SphereCollider extends CollisionShape{

    private final Vector3f center;
    private final float radius;

    public SphereCollider(Vector3f center, float radius){
        this.center = center;
        this.radius = radius;
    }

    public Vector3f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
