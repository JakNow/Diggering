package pl.oblivion.shaders;

import pl.oblivion.materials.Material;

public class UniformMaterial extends Uniform {

    public UniformVec4 ambientColour;
    public UniformVec4 diffuseColour;
    public UniformVec4 specularColour;

    public UniformBoolean hasTexture;
    public UniformBoolean hasNormalMap;


    public UniformMaterial(String name) {
        super(name);
        ambientColour = new UniformVec4(name + ".ambientColour");
        diffuseColour = new UniformVec4(name + ".diffuseColour");
        specularColour = new UniformVec4(name + ".specularColour");

        hasTexture = new UniformBoolean(name + ".hasTexture");
        hasNormalMap = new UniformBoolean(name + ".hasNormalMap");
    }

    public void loadMaterial(Material material) {
        ambientColour.loadVec4(material.getAmbient());
        diffuseColour.loadVec4(material.getDiffuse());
        specularColour.loadVec4(material.getSpecular());

        hasTexture.loadBoolean(material.isTextured());
        hasNormalMap.loadBoolean(material.isNormal());
    }
}
