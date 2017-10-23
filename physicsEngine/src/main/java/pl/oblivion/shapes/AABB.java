package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.HashMap;
import java.util.Map;

public class AABB extends CollisionShape {

    private final Vector3f min;
    private final Vector3f max;

    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Creates map of AABB for every possible textured mesh inside model
     *
     * @param model creates AABB for each textured mesh inside each model part in model
     * @return map of every AABB inside model with textured mesh as keys
     */
    public static Map<TexturedMesh, AABB> createFullMapAABB(Model model) {
        Map<TexturedMesh, AABB> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                map.put(texturedMesh, createAABB(texturedMesh));
            }
        }

        return map;
    }

    /**
     * Creates map of AABB for each model part in model
     *
     * @param model creates AABB for each model part inside model view
     * @return map of AABB with model parts as keys
     */
    public static Map<ModelPart, AABB> createMapAABB(Model model) {
        Map<ModelPart, AABB> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createAABB(modelPart));
        }

        return map;
    }

    /**
     * Creates map of AABB for each Textured Mesh in model part
     *
     * @param modelPart creates AABB for each textured mesh inside model part
     * @return map of AABB with textured meshes as keys
     */
    public static Map<TexturedMesh, AABB> createMapAABB(ModelPart modelPart) {
        Map<TexturedMesh, AABB> map = new HashMap<>();
        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            map.put(texturedMesh, createAABB(texturedMesh));
        }
        return map;
    }

    /**
     * Creates AABB for a whole model, retrieving smallest and highest values of x,y and z axis and generates min and max points from it
     *
     * @param model model to generate AABB from
     * @return AABB based on the model
     */
    public static AABB createAABB(Model model) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);

        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                float[] vertices = texturedMesh.getStaticMeshData().getVertices();
                for (int i = 0; i < vertices.length; i += 3) {
                    min.x = getMin(vertices[i], min.x);
                    min.y = getMin(vertices[i + 1], min.y);
                    min.z = getMin(vertices[i + 2], min.z);

                    max.x = getMax(vertices[i], max.x);
                    max.y = getMax(vertices[i + 1], max.y);
                    max.z = getMax(vertices[i + 2], max.z);
                }
            }
        }
        return new AABB(min, max);
    }

    /**
     * Creates AABB for a single model part (from model), retriving smallest and highest values of x,y and z axis and generates min and max points from it
     *
     * @param modelPart model part to generate AABB from
     * @return AABB based on the model part
     */
    public static AABB createAABB(ModelPart modelPart) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);

        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            float[] vertices = texturedMesh.getStaticMeshData().getVertices();
            for (int i = 0; i < vertices.length; i += 3) {
                min.x = getMin(vertices[i], min.x);
                min.y = getMin(vertices[i + 1], min.y);
                min.z = getMin(vertices[i + 2], min.z);

                max.x = getMax(vertices[i], max.x);
                max.y = getMax(vertices[i + 1], max.y);
                max.z = getMax(vertices[i + 2], max.z);
            }
        }

        return new AABB(min, max);
    }

    /**
     * Creates AABB for a single textured mesh, retrieving smallest and highest values of x,y and z axis and generates min and max points from it
     *
     * @param texturedMesh model part to generate AABB from
     * @return AABB based on the textured mesh
     */
    public static AABB createAABB(TexturedMesh texturedMesh) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);
        float[] vertices = texturedMesh.getStaticMeshData().getVertices();
        for (int i = 0; i < vertices.length; i += 3) {
            min.x = getMin(vertices[i], min.x);
            min.y = getMin(vertices[i + 1], min.y);
            min.z = getMin(vertices[i + 2], min.z);

            max.x = getMax(vertices[i], max.x);
            max.y = getMax(vertices[i + 1], max.y);
            max.z = getMax(vertices[i + 2], max.z);
        }

        return new AABB(min, max);
    }

    private static float getMin(float compare, float min) {
        if (compare < min)
            return compare;
        return min;
    }

    private static float getMax(float compare, float max) {
        if (compare > max)
            return compare;
        return max;
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

    public Vector3f getMin() {
        return min;
    }

    public Vector3f getMax() {
        return max;
    }

}
