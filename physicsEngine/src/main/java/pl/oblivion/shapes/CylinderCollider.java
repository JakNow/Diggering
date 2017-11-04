package pl.oblivion.shapes;

import org.joml.Vector3f;

public class CylinderCollider extends CollisionShape {

    private final Vector3f centerDown;
    private final Vector3f centerTop;
    private float radius;

    private Vector3f currentCenterDown;
    private Vector3f currentCenterTop;

    public CylinderCollider(Vector3f centerDown, Vector3f centerTop, float radius) {
        this.centerDown = centerDown;
        this.centerTop = centerTop;
        this.radius = radius;
        this.currentCenterDown = centerDown;
        this.currentCenterTop = centerTop;
    }

    @Override
    public boolean intersectAABB(AABB aabb) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectSphere(SphereCollider sphereCollider) {
        //TODO
        return false;
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

    public Vector3f getCenterDown() {
        return centerDown;
    }

    public Vector3f getCenterTop() {
        return centerTop;
    }

    public Vector3f getCurrentCenterDown() {
        return currentCenterDown;
    }

    public Vector3f getCurrentCenterTop() {
        return currentCenterTop;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void updatePosition() {
        this.currentCenterDown = this.getCenterDown().add(this.getModel().getPosition());
        this.currentCenterTop = this.getCenterDown().add(this.getModel().getPosition());
    }
}
