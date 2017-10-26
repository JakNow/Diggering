package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.HashMap;
import java.util.Map;

public class SphereCollider extends CollisionShape {

    private Vector3f center;
    private float radius;

    public SphereCollider(Vector3f center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public SphereCollider() {
    }

    @Override
    public boolean intersectAABB(AABB aabb) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectSphere(SphereCollider sphereCollider) {
        Vector3f centerThis = this.center.add(this.model.getPosition());
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

    }

    @Override
    public CollisionShape createShape(TexturedMesh texturedMesh) {
        AABB initAABB = new AABB();
        AABB tempAABB = (AABB) initAABB.createShape(texturedMesh);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);

        return new SphereCollider(center, length);
    }

    @Override
    public CollisionShape createShape(ModelPart modelPart) {
        AABB initAABB = new AABB();
        AABB tempAABB = (AABB) initAABB.createShape(modelPart);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);

        return new SphereCollider(center, length);
    }

    @Override
    public CollisionShape createShape(Model model) {
        AABB initAABB = new AABB();
        AABB tempAABB = (AABB) initAABB.createShape(model);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);

        SphereCollider sphereCollider = new SphereCollider(center, length);
        sphereCollider.setModel(model);
        return sphereCollider;
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
