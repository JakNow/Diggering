package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.staticModels.StaticMeshData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshCollider extends CollisionShape {

    private List<Face> faces;

    public MeshCollider(List<Face> faces) {
        this.faces = faces;
    }

    /**
     * Creates map of MeshColliders for every possible textured mesh inside model
     *
     * @param model used to retrieve every textured mesh of every model part inside
     * @return map of every MeshCollider inside model with textured mesh as keys
     */
    public static Map<TexturedMesh, MeshCollider> createFullMapMeshCollider(Model model) {
        Map<TexturedMesh, MeshCollider> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                map.put(texturedMesh, createMeshCollider(texturedMesh));
            }
        }

        return map;
    }

    /**
     * Creates map of MeshCollider for each model part in model
     *
     * @param model used to retrieve model parts of the model to create MeshColliders of it
     * @return map of MeshColliders with model parts as keys
     */
    public static Map<ModelPart, MeshCollider> createMapMeshCollider(Model model) {
        Map<ModelPart, MeshCollider> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            map.put(modelPart, createMeshCollider(modelPart));
        }

        return map;
    }

    /**
     * Creates map of MeshColliders for each Textured Mesh in model part
     *
     * @param modelPart used to retrieve Textured Meshes of model part for MeshCollider
     * @return map of MeshCollider with textured meshes as keys
     */
    public static Map<TexturedMesh, MeshCollider> createMapMeshCollider(ModelPart modelPart) {
        Map<TexturedMesh, MeshCollider> map = new HashMap<>();

        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            map.put(texturedMesh, createMeshCollider(texturedMesh));
        }

        return map;
    }

    /**
     * Creates MeshCollider of whole model
     *
     * @param model retrieve every textured mesh inside every model part inside model
     * @return MeshCollider with list of all faces inside model
     */
    public static MeshCollider createMeshCollider(Model model) {
        List<Face> faceList = new ArrayList<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            faceList.addAll(createMeshCollider(modelPart).faces);
        }
        return new MeshCollider(faceList);
    }

    /**
     * Creates MeshCollider of model part
     *
     * @param modelPart retrieve every textured mesh inside model part to generate list of faces
     * @return MeshCollider with list of faces for every textured mesh inside model part
     */
    public static MeshCollider createMeshCollider(ModelPart modelPart) {
        List<Face> faceList = new ArrayList<>();
        for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
            faceList.addAll(createMeshCollider(texturedMesh).faces);
        }
        return new MeshCollider(faceList);
    }

    /**
     * Creates MeshCollider of textured mesh
     *
     * @param texturedMesh textured mesh to retrieve data about vertices and normals
     * @return MeshCollider with list of faces for textured mesh
     */
    public static MeshCollider createMeshCollider(TexturedMesh texturedMesh) {
        StaticMeshData staticMeshData = texturedMesh.getStaticMeshData();
        float[] vertices = staticMeshData.getVertices();
        float[] normals = staticMeshData.getNormals();
        int[] indices = staticMeshData.getIndices();

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

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }
}
