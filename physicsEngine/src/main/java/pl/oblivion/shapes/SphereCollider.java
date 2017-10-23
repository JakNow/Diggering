package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.HashMap;
import java.util.Map;

public class SphereCollider extends CollisionShape {

    private final Vector3f center;
    private final float radius;

    public SphereCollider(Vector3f center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Creates map of SphereColliders for every possible textured mesh inside model
     *
     * @param model creates SphereCollider for each textured mesh inside each model part in model
     * @return map of every SphereCollider inside moel with textured mesh as keys
     */
    public static Map<TexturedMesh, SphereCollider> createsFullMapSphereCollider(Model model) {
        Map<TexturedMesh, SphereCollider> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                map.put(texturedMesh, createSpehereCollider(texturedMesh));
            }
        }

        return map;
    }

    /**
     * Creates map of SphereColliders for each model part in model
     *
     * @param model creates SphereCollider for each model part inside model view
     * @return map of SphereColliders with model parts as keys
     */
    public static Map<ModelPart, SphereCollider> createsMapSphereCollider(Model model) {
        Map<ModelPart, SphereCollider> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createSpehereCollider(modelPart));
        }

        return map;
    }

    /**
     * Creates map of SphereColliders for each TexturedMesh in model part
     *
     * @param modelPart creates SphereCollider for each textured mesh inside model part
     * @return map of SphereColliders with textured meshes as keys
     */
    public static Map<TexturedMesh, SphereCollider> createsMapSphereCollider(ModelPart modelPart) {
        Map<TexturedMesh, SphereCollider> map = new HashMap<>();
        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            map.put(texturedMesh, createSpehereCollider(texturedMesh));
        }

        return map;
    }

    /**
     * Creates Sphere Collider for a whole model
     *
     * @param model model to generate SphereCollider from
     * @return SphereCollider based on the model
     */
    public static SphereCollider createSpehereCollider(Model model) {
        AABB tempAABB = AABB.createAABB(model);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);
        float length2 = center.distance(max);

        return new SphereCollider(center, length);
    }

    /**
     * Creates SphereCollider for a single model part (from model) based on AABB for it
     *
     * @param modelPart model part to generate SphereCollider from
     * @return SphereCollider based on model part
     */
    public static SphereCollider createSpehereCollider(ModelPart modelPart) {
        AABB tempAABB = AABB.createAABB(modelPart);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);
        float length2 = center.distance(max);

        return new SphereCollider(center, length);
    }

    /**
     * Creates SphereCollider for a single textured mesh (from model part) based on AABB for it
     *
     * @param texturedMesh model part to generate SphereCollider from
     * @return SphereCollider based on the textured mesh
     */
    public static SphereCollider createSpehereCollider(TexturedMesh texturedMesh) {
        AABB tempAABB = AABB.createAABB(texturedMesh);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);
        float length2 = center.distance(max);

        return new SphereCollider(center, length);
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

    public Vector3f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
