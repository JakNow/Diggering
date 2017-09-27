package pl.oblivion.objParser;

import pl.oblivion.materials.Material;

import java.util.HashMap;

public class MaterialData {

    private HashMap<String,Material> materials = new HashMap<>();

    public MaterialData(){

    }

    public HashMap<String, Material> getMaterials() {
        return materials;
    }

    public void setMaterials(HashMap<String, Material> materials) {
        this.materials = materials;
    }
}
