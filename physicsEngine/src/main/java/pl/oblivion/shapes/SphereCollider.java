package pl.oblivion.shapes;

import org.joml.Vector3f;

public class SphereCollider extends CollisionShape {

    private final Vector3f center;
    private Vector3f currentCenter;
    private float radius;

    public SphereCollider(Vector3f center, float radius) {
        this.center = center;
        this.radius = radius;
    }


    @Override
    public boolean intersectAABB(AABB aabb) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectSphere(SphereCollider sphereCollider) {
        Vector3f centerThis = this.center.add(this.getModel().getPosition());
        Vector3f centerThat = sphereCollider.center.add(sphereCollider.getModel().getPosition());
        float distance = centerThis.distance(centerThat);

        return distance <= sphereCollider.radius + this.radius;

    }

    @Override
    public boolean intersectMesh(MeshCollider meshCollider) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectCylinder(CylinderCollider cylinderCollider) {
        //TODO
        return false;
    }

    @Override
    public void updatePosition() {
        this.currentCenter = center.add(this.getModel().getPosition());
    }
}
