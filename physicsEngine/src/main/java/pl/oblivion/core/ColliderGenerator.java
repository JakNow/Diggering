package pl.oblivion.core;

import org.joml.Vector3f;
import pl.oblivion.base.MeshData;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.shapes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColliderGenerator {


    public ColliderGenerator() {
    }

    public static AABB createAABB(Model model) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);

        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            AABB aabb = createAABB(modelPart,model);
            min.x = getMin(aabb.getMin().x,min.x);
            min.y = getMin(aabb.getMin().y,min.y);
            min.z = getMin(aabb.getMin().z,min.z);

            max.x = getMax(aabb.getMax().x,max.x);
            max.y = getMax(aabb.getMax().y,max.y);
            max.z = getMax(aabb.getMax().z,max.z);

        }
        AABB aabb = new AABB(min, max);
        aabb.setModel(model);
        return aabb;
    }


    public static AABB createAABB(ModelPart modelPart, Model model) {
        Vector3f min = new Vector3f(10000, 10000, 10000);
        Vector3f max = new Vector3f(-10000, -10000, -10000);

        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            float[] vertices = texturedMesh.getMeshData().getVertices();
            for (int i = 0; i < vertices.length; i += 3) {
                min.x = getMin(vertices[i] * model.getScale(), min.x);
                min.y = getMin(vertices[i + 1] * model.getScale(), min.y);
                min.z = getMin(vertices[i + 2] * model.getScale(), min.z);

                max.x = getMax(vertices[i] * model.getScale(), max.x);
                max.y = getMax(vertices[i + 1] * model.getScale(), max.y);
                max.z = getMax(vertices[i + 2] * model.getScale(), max.z);
            }
        }

        AABB aabb = new AABB(min.mul(model.getScale()), max.mul(model.getScale()));
        aabb.setModel(model);
        return aabb;
    }


    public static Map<ModelPart, CollisionShape> createAABBMap(Model model) {
        Map<ModelPart, CollisionShape> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createAABB(modelPart, model));
        }
        return map;
    }


    public static CylinderCollider createCylinderCollider(ModelPart modelPart, Model model) {
        AABB aabb = createAABB(modelPart, model);
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

        CylinderCollider cylinderCollider = new CylinderCollider(centerDown, centerTop, radius);
        cylinderCollider.setModel(model);
        return cylinderCollider;
    }

    public static CollisionShape createCylinderCollider(Model model) {
        AABB aabb = createAABB(model);
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
        CylinderCollider cylinderCollider = new CylinderCollider(centerDown, centerTop, radius);
        cylinderCollider.setModel(model);
        return cylinderCollider;
    }


    public static Map<ModelPart, CollisionShape> createCylinderColliderMap(Model model) {
        Map<ModelPart, CollisionShape> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createCylinderCollider(modelPart, model));
        }

        return map;
    }


    private static MeshCollider createMeshCollider(TexturedMesh texturedMesh, Model model) {
        MeshData meshData = texturedMesh.getMeshData();
        float[] vertices = meshData.getVertices();
        float[] normals = meshData.getNormals();
        int[] indices = meshData.getIndices();

        List<Face> faceList = new ArrayList<>();
        for (int i = 0; i < indices.length; i += 3) {
            int pointer1 = indices[i];
            int pointer2 = indices[i + 1];
            int pointer3 = indices[i + 2];

            Vertex vertex1 = new Vertex(new Vector3f(vertices[pointer1 * 3], vertices[pointer1 * 3 + 1], vertices[pointer1 * 3 + 2]).mul(model.getScale()), new Vector3f(normals[pointer1 * 3], normals[pointer1 * 3 + 1], normals[pointer1 * 3 + 2]));
            Vertex vertex2 = new Vertex(new Vector3f(vertices[pointer2 * 3], vertices[pointer2 * 3 + 1], vertices[pointer2 * 3 + 2]).mul(model.getScale()), new Vector3f(normals[pointer2 * 3], normals[pointer2 * 3 + 1], normals[pointer2 * 3 + 2]));
            Vertex vertex3 = new Vertex(new Vector3f(vertices[pointer3 * 3], vertices[pointer3 * 3 + 1], vertices[pointer3 * 3 + 2]).mul(model.getScale()), new Vector3f(normals[pointer3 * 3], normals[pointer3 * 3 + 1], normals[pointer3 * 3 + 2]));

            faceList.add(new Face(vertex1, vertex2, vertex3));
        }

        MeshCollider meshCollider = new MeshCollider(faceList);
        meshCollider.setModel(model);
        return meshCollider;
    }

    public static MeshCollider createMeshCollider(ModelPart modelPart, Model model) {
        List<Face> faceList = new ArrayList<>();
        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            MeshCollider meshCollider = createMeshCollider(texturedMesh, model);
            faceList.addAll(meshCollider.getFaces());
        }
        MeshCollider meshCollider = new MeshCollider(faceList);
        meshCollider.setModel(model);
        return meshCollider;
    }

    public static MeshCollider createMeshCollider(Model model) {
        List<Face> faceList = new ArrayList<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            MeshCollider meshCollider = createMeshCollider(modelPart, model);
            faceList.addAll(meshCollider.getFaces());
        }
        MeshCollider meshCollider = new MeshCollider(faceList);
        meshCollider.setModel(model);
        return meshCollider;
    }

    public static Map<ModelPart, CollisionShape> createMeshColliderMap(Model model) {
        Map<ModelPart, CollisionShape> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createMeshCollider(modelPart, model));
        }

        return map;
    }


    public static SphereCollider createSphereCollider(ModelPart modelPart, Model model) {
        AABB tempAABB = createAABB(modelPart, model);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);

        SphereCollider sphereCollider = new SphereCollider(center, length);
        sphereCollider.setModel(model);
        return sphereCollider;
    }

    public static SphereCollider createSphereCollider(Model model) {
        AABB tempAABB = createAABB(model);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x + max.x) / 2, (min.y + max.y) / 2, (min.z + max.z) / 2);
        float length = center.distance(min);

        SphereCollider sphereCollider = new SphereCollider(center, length);
        sphereCollider.setModel(model);
        return sphereCollider;
    }



    public static Map<ModelPart, CollisionShape> createSphereColliderMap(Model model) {
        Map<ModelPart, CollisionShape> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createSphereCollider(modelPart, model));
        }

        return map;
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
}
