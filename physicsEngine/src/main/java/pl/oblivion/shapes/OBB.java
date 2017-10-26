package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.HashMap;
import java.util.Map;


public class OBB extends CollisionShape {

    private Vector3f p1;
    private Vector3f p2;
    private Vector3f p3;
    private Vector3f p4;

    private Vector3f p5;
    private Vector3f p6;
    private Vector3f p7;
    private Vector3f p8;

    private OBB(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4, Vector3f p5, Vector3f p6, Vector3f p7, Vector3f p8) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.p7 = p7;
        this.p8 = p8;
    }

    @Override
    public void updatePosition() {

    }

    @Override
    public CollisionShape createShape(TexturedMesh texturedMesh) {
        AABB aabbInit = new AABB();
        AABB aabb = (AABB) aabbInit.createShape(texturedMesh);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();

        Vector3f p2 = new Vector3f(min.x, max.y, max.z);
        Vector3f p3 = new Vector3f(min.x, max.y, min.z);
        Vector3f p4 = new Vector3f(max.x, max.y, min.z);
        Vector3f p5 = new Vector3f(max.x, min.y, max.z);
        Vector3f p6 = new Vector3f(min.x, min.y, max.z);
        Vector3f p8 = new Vector3f(max.x, min.y, min.z);

        return new OBB(max, p2, p3, p4, p5, p6, min, p8);
    }

    @Override
    public CollisionShape createShape(ModelPart modelPart) {
        AABB aabbInit = new AABB();
        AABB aabb = (AABB) aabbInit.createShape(modelPart);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();

        Vector3f p2 = new Vector3f(min.x, max.y, max.z);
        Vector3f p3 = new Vector3f(min.x, max.y, min.z);
        Vector3f p4 = new Vector3f(max.x, max.y, min.z);
        Vector3f p5 = new Vector3f(max.x, min.y, max.z);
        Vector3f p6 = new Vector3f(min.x, min.y, max.z);
        Vector3f p8 = new Vector3f(max.x, min.y, min.z);

        return new OBB(max, p2, p3, p4, p5, p6, min, p8);
    }

    @Override
    public CollisionShape createShape(Model model) {
        AABB aabbInit = new AABB();
        AABB aabb = (AABB) aabbInit.createShape(model);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();

        Vector3f p2 = new Vector3f(min.x, max.y, max.z);
        Vector3f p3 = new Vector3f(min.x, max.y, min.z);
        Vector3f p4 = new Vector3f(max.x, max.y, min.z);
        Vector3f p5 = new Vector3f(max.x, min.y, max.z);
        Vector3f p6 = new Vector3f(min.x, min.y, max.z);
        Vector3f p8 = new Vector3f(max.x, min.y, min.z);

        return new OBB(max, p2, p3, p4, p5, p6, min, p8);
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

    @Override
    public boolean intersectAABB(AABB aabb) {
        return false;
    }

    @Override
    public boolean intersectSphere(SphereCollider sphereCollider) {
        return false;
    }

    @Override
    public boolean intersectMesh(MeshCollider meshCollider) {
        return false;
    }

    @Override
    public boolean intersectCylinder(CylinderCollider cylinderCollider) {
        return false;
    }


}
