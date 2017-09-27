package pl.oblivion.objParser;

import pl.oblivion.base.MeshData;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.materials.Material;

import java.util.ArrayList;
import java.util.List;

public class ObjData {

    private List<ModelData> modelDataList = new ArrayList<>();

    public ObjData(){

    }

    public ModelData getModel(){
        return modelDataList.get(0);
    }

    public List<ModelData> getModels(){
        return modelDataList;
    }

    public void addModelData(ModelData modelData) {
        modelDataList.add(modelData);
    }
}
