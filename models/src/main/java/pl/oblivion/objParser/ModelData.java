package pl.oblivion.objParser;

import pl.oblivion.base.TexturedMesh;

import java.util.ArrayList;
import java.util.List;

public class ModelData {


    private List<Face> faceList;

    public List<ParsedObjectData> parsedObjectDatas;

    public ModelData(){
        this.parsedObjectDatas = new ArrayList<>();
    }

    public List<Face> getFaceList() {
        return faceList;
    }

    public void setFaceList(List<Face> faceList) {
        this.faceList = faceList;
    }

    public void add(ParsedObjectData parsedObjectData) {
        parsedObjectDatas.add(parsedObjectData);
    }

    public List<ParsedObjectData> getParsedObjectDatas() {
        return parsedObjectDatas;
    }
}
