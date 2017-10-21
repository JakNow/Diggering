package pl.oblivion.shapes;

import org.joml.Vector3f;

public class CylinderCollider extends CollisionShape {

    private final Vector3f center;
    private final float halfSize;
    private final float radius;

    public CylinderCollider(Vector3f center, float halfSize, float radius) {
        this.center = center;
        this.halfSize = halfSize;
        this.radius = radius;
    }


    public Vector3f getCenter() {
        return center;
    }

    public float getHalfSize() {
        return halfSize;
    }

    public float getRadius() {
        return radius;
    }
}
