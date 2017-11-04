package pl.oblivion.shapes;

import org.joml.Vector3f;


public class AABB extends CollisionShape {

    private final Vector3f min;
    private final Vector3f max;

    private Vector3f currentMin;
    private Vector3f currentMax;

    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
        this.currentMin = min;
        this.currentMax = max;
    }


    @Override
    public boolean intersectAABB(AABB aabb) {
              return (this.getCurrentMin().x <= aabb.getCurrentMax().x && this.getCurrentMax().x >= aabb.getCurrentMin().x) &&
                     (this.getCurrentMin().y <= aabb.getCurrentMax().y && this.getCurrentMax().y >= aabb.getCurrentMin().y) &&
                     (this.getCurrentMin().z <= aabb.getCurrentMax().z && this.getCurrentMax().z >= aabb.getCurrentMin().z);
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

    public Vector3f getMin() {
        return min;
    }

    public Vector3f getMax() {
        return max;
    }

    public Vector3f getCurrentMin() {
        return currentMin;
    }

    public Vector3f getCurrentMax() {
        return currentMax;
    }

    @Override
    public void updatePosition() {
        this.currentMin = this.getMin().add(this.getModel().getPosition(),this.currentMin);
        this.currentMax = this.getMax().add(this.getModel().getPosition(),this.currentMin);
    }
}
