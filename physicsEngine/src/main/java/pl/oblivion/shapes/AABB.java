package pl.oblivion.shapes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.utils.CollisionMaths;

import java.util.HashMap;
import java.util.Map;


public class AABB extends CollisionShape {

    private Vector3f min;
    private Vector3f max;

    private AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    public AABB() {
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
    public void updatePosition() {

    }

    @Override
    public CollisionShape createShape(TexturedMesh texturedMesh) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);
        float[] vertices = texturedMesh.getMeshData().getVertices();
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

    @Override
    public CollisionShape createShape(ModelPart modelPart) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);

        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            float[] vertices = texturedMesh.getMeshData().getVertices();
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

    @Override
    public CollisionShape createShape(Model model) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);

        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                float[] vertices = texturedMesh.getMeshData().getVertices();
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
        AABB aabb = new AABB(min, max);
        aabb.setModel(model);
        return aabb;
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
    public boolean intersectAABB(AABB aabb) {
        Matrix4f transformModel = CollisionMaths.transformMatrix(model);
        Matrix4f transformAABB = CollisionMaths.transformMatrix(aabb.getModel());

        Vector4f minA = transformModel.transform(new Vector4f(getMin().x, getMin().y, getMin().z, 1.0f));
        Vector4f maxA = transformModel.transform(new Vector4f(getMax().x, getMax().y, getMax().z, 1.0f));
        Vector4f minB = transformAABB.transform(new Vector4f(aabb.getMin().x, aabb.getMin().y, aabb.getMin().z, 1.0f));
        Vector4f maxB = transformAABB.transform(new Vector4f(aabb.getMax().x, aabb.getMax().y, aabb.getMax().z, 1.0f));

        return (minA.x <= maxB.x && maxA.x >= minB.x) &&
                (minA.y <= maxB.y && maxA.y >= minB.y) &&
                (minA.z <= maxB.z && maxA.z >= minB.z);
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

    Vector3f getMin() {
        return min;
    }

    Vector3f getMax() {
        return max;
    }


}
