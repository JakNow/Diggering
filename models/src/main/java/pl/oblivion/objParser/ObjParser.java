package pl.oblivion.objParser;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import pl.oblivion.base.AABB;
import pl.oblivion.base.Config;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.materials.Material;
import pl.oblivion.materials.Texture;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ObjParser {

    public static ObjData loadObjFile(String model){
        BufferedReader reader;

        try{
            reader = getReader(Config.MODELS,model);
            ObjData objData = loadData(reader);
            reader.close();
            return objData;
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Error with OBJ format for: "+model);
            System.exit(0);
            return null;
        }
    }

    private static ObjData loadData(BufferedReader reader) throws Exception {
       String line;
       ObjData objData = new ObjData();
       MaterialData materialData = new MaterialData();
       List<Vector3f> vertexList = new ArrayList<>();
       List<Vector2f> textureList = new ArrayList<>();
       List<Vector3f> normalList = new ArrayList<>();
       AtomicReference<Map<String, TexturedMesh>> texturedMeshMap = new AtomicReference<>(new HashMap<>());
       List<Face>  faceList = new ArrayList<>();
       String usedMtl = null;
        while(true){
           line = reader.readLine();
           if(line == null) {
               break;
           }else if (line.startsWith("mtllib ")){
               loadMaterialLib(materialData,line.replace("mtllib ",""));
           }else if (line.startsWith("o ")){
               if(!objData.getModels().isEmpty()){
                   objData.getModels().get(objData.getModels().size()-1).setFaceList(faceList);
                   faceList = new ArrayList<>();
               }
                objData.addModelData(new ModelData());
           }else if (line.startsWith("v ")){
                String[] currentLine = line.split(" ");
                vertexList.add(new Vector3f(Float.valueOf(currentLine[1]),Float.valueOf(currentLine[2]),Float.valueOf(currentLine[3])));
           }else if (line.startsWith("vt ")){
               String[] currentLine = line.split(" ");
               textureList.add(new Vector2f(Float.valueOf(currentLine[1]),Float.valueOf(currentLine[2])));
           }else if (line.startsWith("vn ")){
               String[] currentLine = line.split(" ");
               normalList.add(new Vector3f(Float.valueOf(currentLine[1]),Float.valueOf(currentLine[2]),Float.valueOf(currentLine[3])));
           }else if (line.startsWith("usemtl ")){
               usedMtl = line.replace("usemtl ","");
               texturedMeshMap.get().put(usedMtl,new TexturedMesh(null,materialData.getMaterials().get(usedMtl)));
           }else if (line.startsWith("f ")){
                String[] currentLine = line.split(" ");

               String[] vec1Data = currentLine[1].split("/");
               String[] vec2Data = currentLine[2].split("/");
               String[] vec3Data = currentLine[3].split("/");

               Vector3i vector1 = new Vector3i(Integer.valueOf(vec1Data[0]),Integer.valueOf(vec1Data[1]),Integer.valueOf(vec1Data[2]));
               Vector3i vector2 = new Vector3i(Integer.valueOf(vec2Data[0]),Integer.valueOf(vec2Data[1]),Integer.valueOf(vec2Data[2]));
               Vector3i vector3 = new Vector3i(Integer.valueOf(vec3Data[0]),Integer.valueOf(vec3Data[1]),Integer.valueOf(vec3Data[2]));

               faceList.add(new Face(usedMtl,vector1,vector2,vector3));
           }
       }

       if(!faceList.isEmpty())
           objData.getModels().get(objData.getModels().size()-1).setFaceList(faceList);

        for(ModelData modelData :  objData.getModels()){
            String currentMaterial = modelData.getFaceList().get(0).getUsedMtl();

            List<Face> faces = new ArrayList<>();

            for(Face face : modelData.getFaceList()) {
              if(currentMaterial.equals(face.getUsedMtl())){
                faces.add(face);
              }else{
                  modelData.add(processFaces(faces,vertexList,textureList,normalList));
                  currentMaterial = face.getUsedMtl();
                  faces.clear();
                  faces.add(face);
              }
            }
            modelData.add(processFaces(faces,vertexList,textureList,normalList));
        }

       return objData;
    }

    private static ParsedObjectData processFaces(List<Face> faces, List<Vector3f> verticesList, List<Vector2f> texturesList , List<Vector3f> normalsList){
        List<Integer> indices = new ArrayList<>();
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        Map<Vector3i,Integer> proccessedFaceMap = new HashMap<>();
        for(Face face : faces){
            processSingleFace(face.getVector1(),proccessedFaceMap,verticesList,texturesList,normalsList,indices,vertices,textures,normals);
            processSingleFace(face.getVector2(),proccessedFaceMap,verticesList,texturesList,normalsList,indices,vertices,textures,normals);
            processSingleFace(face.getVector3(),proccessedFaceMap,verticesList,texturesList,normalsList,indices,vertices,textures,normals);
        }


        int[] indicesArray = new int[indices.size()];
        float[] verticesArray = new float[vertices.size()*3];
        float[] texturesArray = new float[textures.size()*2];
        float[] normalsArray = new float[normals.size()*3];

        AABB aabb = convertListsToArrays(indices,vertices,textures,normals,indicesArray,verticesArray,texturesArray,normalsArray);


        return new ParsedObjectData(indicesArray,verticesArray,texturesArray,normalsArray,aabb);
    }

    private static AABB convertListsToArrays(List<Integer> indices, List<Vector3f> vertices, List<Vector2f> textures, List<Vector3f> normals, int[] indicesArray, float[] verticesArray, float[] texturesArray, float[] normalsArray) {

       for(int i = 0; i < indices.size();i++){
           indicesArray[i] = indices.get(i);
       }

        Vector3f min = vertices.get(0);
        Vector3f max = vertices.get(0);
        for(int i = 0; i < vertices.size();i++){
            Vector3f temp = vertices.get(i);
            processVectors(min,max,temp);
            verticesArray[i*3] = temp.x;
            verticesArray[i*3+1] = temp.y;
            verticesArray[i*3+2] = temp.z;
        }

        for(int i = 0; i < textures.size();i++){
            Vector2f temp = textures.get(i);
            texturesArray[i*2] = temp.x;
            texturesArray[i*2+1] = temp.y;

        }

        for(int i = 0; i < normals.size();i++){
            Vector3f temp = normals.get(i);
            normalsArray[i*3] = temp.x;
            normalsArray[i*3+1] = temp.y;
            normalsArray[i*3+2] = temp.z;
        }

        return new AABB(min,max);
    }

    private static void processVectors(Vector3f min, Vector3f max, Vector3f temp) {
        if(temp.x < min.x){
            min.x = temp.x;
        } else if (temp.x > max.x){
            max.x = temp.x;
        }
        if(temp.y < min.y){
            min.y = temp.y;
        } else if (temp.y > max.y){
            max.y = temp.y;
        }
        if(temp.z < min.z){
            min.z = temp.z;
        } else if (temp.z > max.z){
            max.z = temp.z;
        }
    }

    private static void processSingleFace(Vector3i faceVec,Map<Vector3i,Integer> proccessedFaceMap, List<Vector3f> verticesList, List<Vector2f> texturesList , List<Vector3f> normalsList,List<Integer> indices, List<Vector3f> vertices, List<Vector2f> textures , List<Vector3f> normals){
        try {
            int i = proccessedFaceMap.get(faceVec);
            indices.add(i);
        }catch(NullPointerException e){
            int i = proccessedFaceMap.size();
            vertices.add(verticesList.get(faceVec.x-1));
            textures.add(texturesList.get(faceVec.y-1));
            normals.add(normalsList.get(faceVec.z-1));
            indices.add(i);
            proccessedFaceMap.put(faceVec,i);
        }

    }
    private static void loadMaterialLib(MaterialData materialData, String materialLib){
       BufferedReader reader;
        try (BufferedReader ignored = reader = getReader(Config.MATERIALS, materialLib)) {
            loadMaterialData(materialData,reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadMaterialData(MaterialData materialData, BufferedReader reader) throws IOException {
        String line;
        String usedMtl;
        Material currentMaterial = null;
        while(true){
            line = reader.readLine();
            if(line == null){
                break;
            }
            else if (line.startsWith("newmtl ")){
                usedMtl = line.replace("newmtl ","");
                currentMaterial = new Material(usedMtl);
                materialData.getMaterials().put(usedMtl,currentMaterial);
            } else if(line.startsWith("Ka ")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setAmbient(new Vector3f(Float.valueOf(currentLine[1]),Float.valueOf(currentLine[2]),Float.valueOf(currentLine[3])));
            }else if(line.startsWith("Kd ")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setDiffuse(new Vector3f(Float.valueOf(currentLine[1]),Float.valueOf(currentLine[2]),Float.valueOf(currentLine[3])));
            }else if(line.startsWith("Ks ")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setSpecular(new Vector3f(Float.valueOf(currentLine[1]),Float.valueOf(currentLine[2]),Float.valueOf(currentLine[3])));
            } else if(line.startsWith("Ns ")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setSpecularExponent(Float.valueOf(currentLine[1]));
            } else if(line.startsWith("map_Ka")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setAmbientTexture(Texture.loadTexture(currentLine[1]));
            }else if(line.startsWith("map_Kd")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setDiffuseTexture(Texture.loadTexture(currentLine[1]));
            }else if(line.startsWith("map_Ks")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setSpecularTexture(Texture.loadTexture(currentLine[1]));
            }else if(line.startsWith("map_Ns")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setSpecularExponentTexture(Texture.loadTexture(currentLine[1]));
            }else if(line.startsWith("map_d")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setAlphaTexture(Texture.loadTexture(currentLine[1]));
            }else if(line.startsWith("map_bump")){
                String[] currentLine = line.split(" ");
                assert currentMaterial != null;
                currentMaterial.setBumpTexture(Texture.loadTexture(currentLine[1]));
            }
        }
    }


    private static BufferedReader getReader(String filePath, String fileName) throws FileNotFoundException {
              return new BufferedReader(new FileReader(new File(filePath+fileName)));
    }
}
