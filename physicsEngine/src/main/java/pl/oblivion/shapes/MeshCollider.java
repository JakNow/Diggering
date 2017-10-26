package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.MeshData;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshCollider extends CollisionShape {

    private List<Face> faces;

    private MeshCollider(List<Face> faces) {
        this.faces = faces;
    }

    public MeshCollider() {
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
        MeshData meshData = texturedMesh.getMeshData();
        float[] vertices = meshData.getVertices();
        float[] normals = meshData.getNormals();
        int[] indices = meshData.getIndices();

        List<Face> faceList = new ArrayList<>();
        for (int i = 0; i < indices.length; i += 3) {
            int pointer1 = indices[i];
            int pointer2 = indices[i + 1];
            int pointer3 = indices[i + 2];

            Vertex vertex1 = new Vertex(new Vector3f(vertices[pointer1 * 3], vertices[pointer1 * 3 + 1], vertices[pointer1 * 3 + 2]), new Vector3f(normals[pointer1 * 3], normals[pointer1 * 3 + 1], normals[pointer1 * 3 + 2]));
            Vertex vertex2 = new Vertex(new Vector3f(vertices[pointer2 * 3], vertices[pointer2 * 3 + 1], vertices[pointer2 * 3 + 2]), new Vector3f(normals[pointer2 * 3], normals[pointer2 * 3 + 1], normals[pointer2 * 3 + 2]));
            Vertex vertex3 = new Vertex(new Vector3f(vertices[pointer3 * 3], vertices[pointer3 * 3 + 1], vertices[pointer3 * 3 + 2]), new Vector3f(normals[pointer3 * 3], normals[pointer3 * 3 + 1], normals[pointer3 * 3 + 2]));

            faceList.add(new Face(vertex1, vertex2, vertex3));
        }

        return new MeshCollider(faceList);
    }

    @Override
    public CollisionShape createShape(ModelPart modelPart) {
        List<Face> faceList = new ArrayList<>();
        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            MeshCollider meshCollider = (MeshCollider) createShape(texturedMesh);
            faceList.addAll(meshCollider.faces);
        }
        return new MeshCollider(faceList);
    }

    @Override
    public CollisionShape createShape(Model model) {
        List<Face> faceList = new ArrayList<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            MeshCollider meshCollider = (MeshCollider) createShape(modelPart);
            faceList.addAll(meshCollider.faces);
        }
        return new MeshCollider(faceList);
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
