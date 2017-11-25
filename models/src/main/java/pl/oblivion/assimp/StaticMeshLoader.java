package pl.oblivion.assimp;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import pl.oblivion.base.Config;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.ModelView;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.materials.Material;
import pl.oblivion.materials.Texture;
import pl.oblivion.staticModels.StaticMeshData;
import pl.oblivion.staticModels.StaticModelView;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public class StaticMeshLoader {

    public static ModelView load(String resourcePath, String texturesDir) throws Exception {
        return load(Config.MODELS + resourcePath, texturesDir, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
    }

    private static ModelView load(String resourcePath, String texturesDir, int flags) throws Exception {
        AIScene aiScene = aiImportFile(resourcePath, flags);
        if (aiScene == null)
            throw new Exception("Error loading model");

        int numMaterials = aiScene.mNumMaterials();

        PointerBuffer aiMaterials = aiScene.mMaterials();
        List<Material> materials = new ArrayList<>();

        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
            processMaterial(aiMaterial, materials, texturesDir);
        }


        AINode rootNode = aiScene.mRootNode();
        int numParts = rootNode.mNumChildren();
        ModelPart[] modelParts = new ModelPart[numParts];

        PointerBuffer aiParts = rootNode.mChildren();
        PointerBuffer aiMeshes = aiScene.mMeshes();

        int pointer = 0;
        for (int i = 0; i < numParts; i++) {
            AINode part = AINode.create(aiParts.get(i));
            int numMeshesInPart = part.mNumMeshes();
            TexturedMesh[] texturedMeshes = new TexturedMesh[numMeshesInPart];

            for (int j = 0; j < numMeshesInPart; j++) {
                AIMesh aiMesh = AIMesh.create(aiMeshes.get(pointer));
                TexturedMesh texturedMesh = processMesh(aiMesh, materials);
                texturedMeshes[j] = texturedMesh;
                pointer++;
            }

            modelParts[i] = new ModelPart(texturedMeshes);
        }

        return new StaticModelView(modelParts);
    }

    private static void processMaterial(AIMaterial aiMaterial, List<Material> materials, String texturesDir) throws Exception {
        AIColor4D color = AIColor4D.create();

        AIString path = AIString.calloc();
        aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
        String textPath = path.dataString();

        Texture diffuseTexture = null;
        if (texturesDir != null) {

            TextureCache textCache = TextureCache.getInstance();
            diffuseTexture = textCache.getTexture(Config.TEXTURES + texturesDir);
        } else {
            assert textPath != null;
            if (textPath.length() > 0) {
                TextureCache textCache = TextureCache.getInstance();
                diffuseTexture = textCache.getTexture(textPath);
            }
        }

        Vector4f ambient = Material.DEFAULT_COLOUR;
        int result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, color);
        if (result == 0)
            ambient = new Vector4f(color.r(), color.g(), color.b(), color.a());

        Vector4f diffuse = Material.DEFAULT_COLOUR;
        result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, color);
        if (result == 0)
            diffuse = new Vector4f(color.r(), color.g(), color.b(), color.a());


        Vector4f specular = Material.DEFAULT_COLOUR;
        result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_SPECULAR, aiTextureType_NONE, 0, color);
        if (result == 0)
            specular = new Vector4f(color.r(), color.g(), color.b(), color.a());

        AIString aiName = AIString.create();
        String name = null;
        result = aiGetMaterialString(aiMaterial, AI_MATKEY_NAME, aiTextureType_NONE, 0, aiName);
        if (result == 0)
            name = aiName.dataString();

        Material material = new Material(name);
        material.setDiffuseTexture(diffuseTexture);
        material.setAmbient(ambient);
        material.setDiffuse(diffuse);
        material.setSpecular(specular);


        materials.add(material);
    }

    private static TexturedMesh processMesh(AIMesh aiMesh, List<Material> materials) {
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float furthestPoint = processVertices(aiMesh, vertices);
        processNormals(aiMesh, normals);
        processTextCoords(aiMesh, textures);
        processIndices(aiMesh, indices);

        StaticMeshData mesh = new StaticMeshData(listIntToArray(indices), listToArray(vertices), listToArray(textures), listToArray(normals), null);

        Material material;
        int materialIdx = aiMesh.mMaterialIndex();
        if (materialIdx >= 0 && materialIdx < materials.size()) {
            material = materials.get(materialIdx);
        } else {
            material = new Material();
        }
        return new TexturedMesh(mesh, material, furthestPoint);
    }

    private static float processVertices(AIMesh aiMesh, List<Float> vertices) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        float furthestPoint = 0;
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            Vector3f vertex = new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z());
            if (vertex.length() > furthestPoint) {
                furthestPoint = vertex.length();
            }

            vertices.add(vertex.x);
            vertices.add(vertex.y);
            vertices.add(vertex.z);
        }

        return furthestPoint;
    }

    private static void processNormals(AIMesh aiMesh, List<Float> normals) {
        AIVector3D.Buffer aiNormals = aiMesh.mNormals();
        while (aiNormals != null && aiNormals.remaining() > 0) {
            AIVector3D aiNormal = aiNormals.get();
            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }
    }

    private static void processTextCoords(AIMesh aiMesh, List<Float> textures) {
        AIVector3D.Buffer textCoords = aiMesh.mTextureCoords(0);
        int numTextCoords = textCoords != null ? textCoords.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D textCoord = textCoords.get();
            textures.add(textCoord.x());
            textures.add(1 - textCoord.y());
        }
    }

    private static void processIndices(AIMesh aiMesh, List<Integer> indices) {
        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }
    }

    private static int[] listIntToArray(List<Integer> list) {
        return list.stream().mapToInt((Integer v) -> v).toArray();
    }

    private static float[] listToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }
}
