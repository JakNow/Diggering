package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.HashMap;
import java.util.Map;

public class CylinderCollider extends CollisionShape {

    private Vector3f centerDown;
    private Vector3f centerTop;
    private float radius;

    private CylinderCollider(Vector3f centerDown, Vector3f centerTop, float radius) {
        this.centerDown = centerDown;
        this.centerTop = centerTop;
        this.radius = radius;
    }

    public CylinderCollider() {
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

    @Override
    public void updatePosition() {

    }

    @Override
    public CollisionShape createShape(TexturedMesh texturedMesh) {
        AABB initAABB = new AABB();
        AABB aabb = (AABB) initAABB.createShape(texturedMesh);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();
        float xDistance = Math.abs(max.x - min.x);
        float yDistance = Math.abs(max.y - min.y);
        float zDistance = Math.abs(max.z - min.z);

        float xAxis = zDistance * yDistance;
        float yAxis = xDistance * zDistance;
        float zAxis = xDistance * yDistance;

        Vector3f centerDown = new Vector3f();
        Vector3f centerTop = new Vector3f();
        float radius = 0;
        //xAxis
        if (xAxis <= yAxis && xAxis <= zAxis) {
            centerDown = new Vector3f(min.x, max.y - min.y, max.z - min.z);
            centerTop = new Vector3f(max.x, max.y - min.y, max.z - min.z);
            radius = centerDown.distance(min);

        }
        //yAxis
        else if (yAxis <= xAxis && yAxis <= zAxis) {
            centerDown = new Vector3f(max.x - min.x, min.y, max.z - min.z);
            centerTop = new Vector3f(max.x - min.x, max.y, max.z - min.z);
            radius = centerDown.distance(min);
        }
        //zAxis
        else if (zAxis <= yAxis && zAxis <= yAxis) {
            centerDown = new Vector3f(max.x - min.x, max.y - min.y, min.z);
            centerTop = new Vector3f(max.x - min.x, max.y - min.y, max.z);
            radius = centerDown.distance(min);
        }

        return new CylinderCollider(centerDown, centerTop, radius);
    }

    @Override
    public CollisionShape createShape(ModelPart modelPart) {
        AABB initAABB = new AABB();
        AABB aabb = (AABB) initAABB.createShape(model);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();
        float xDistance = Math.abs(max.x - min.x);
        float yDistance = Math.abs(max.y - min.y);
        float zDistance = Math.abs(max.z - min.z);

        float xAxis = zDistance * yDistance;
        float yAxis = xDistance * zDistance;
        float zAxis = xDistance * yDistance;
        Vector3f centerDown = new Vector3f();
        Vector3f centerTop = new Vector3f();
        float radius = 0;
        //xAxis
        if (xAxis <= yAxis && xAxis <= zAxis) {
            centerDown = new Vector3f(min.x, max.y - min.y, max.z - min.z);
            centerTop = new Vector3f(max.x, max.y - min.y, max.z - min.z);
            radius = centerDown.distance(min);

        }
        //yAxis
        else if (yAxis <= xAxis && yAxis <= zAxis) {
            centerDown = new Vector3f(max.x - min.x, min.y, max.z - min.z);
            centerTop = new Vector3f(max.x - min.x, max.y, max.z - min.z);
            radius = centerDown.distance(min);
        }
        //zAxis
        else if (zAxis <= yAxis && zAxis <= yAxis) {
            centerDown = new Vector3f(max.x - min.x, max.y - min.y, min.z);
            centerTop = new Vector3f(max.x - min.x, max.y - min.y, max.z);
            radius = centerDown.distance(min);
        }

        return new CylinderCollider(centerDown, centerTop, radius);
    }

    @Override
    public CollisionShape createShape(Model model) {
        AABB initAABB = new AABB();
        AABB aabb = (AABB) initAABB.createShape(model);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();
        float xDistance = Math.abs(max.x - min.x);
        float yDistance = Math.abs(max.y - min.y);
        float zDistance = Math.abs(max.z - min.z);

        float xAxis = zDistance * yDistance;
        float yAxis = xDistance * zDistance;
        float zAxis = xDistance * yDistance;

        Vector3f centerDown = new Vector3f();
        Vector3f centerTop = new Vector3f();
        float radius = 0;
        //xAxis
        if (xAxis <= yAxis && xAxis <= zAxis) {
            centerDown = new Vector3f(min.x, max.y - min.y, max.z - min.z);
            centerTop = new Vector3f(max.x, max.y - min.y, max.z - min.z);
            radius = centerDown.distance(min);

        }
        //yAxis
        else if (yAxis <= xAxis && yAxis <= zAxis) {
            centerDown = new Vector3f(max.x - min.x, min.y, max.z - min.z);
            centerTop = new Vector3f(max.x - min.x, max.y, max.z - min.z);
            radius = centerDown.distance(min);
        }
        //zAxis
        else if (zAxis <= yAxis && zAxis <= yAxis) {
            centerDown = new Vector3f(max.x - min.x, max.y - min.y, min.z);
            centerTop = new Vector3f(max.x - min.x, max.y - min.y, max.z);
            radius = centerDown.distance(min);
        }

        return new CylinderCollider(centerDown, centerTop, radius);
    }

    @Override
    public Map<TexturedMesh, CollisionShape> createMapShape(ModelPart modelPart) {
        Map<TexturedMesh, CollisionShape> map = new HashMap<>();
        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            map.put(texturedMesh, createShape(texturedMesh));
        }

        return map;
    }

    @Override
    public Map<ModelPart, CollisionShape> createMapShape(Model model) {
        Map<ModelPart, CollisionShape> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createShape(modelPart));
        }

        return map;
    }

    @Override
    public Map<TexturedMesh, CollisionShape> createFullMapShape(Model model) {
        Map<TexturedMesh, CollisionShape> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                map.put(texturedMesh, createShape(texturedMesh));
            }
        }
        return map;
    }
}
