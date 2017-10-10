package pl.oblivion.materials;

import org.joml.Vector4f;

public class Material {

    public static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final int DIFFUSE_TEXTURE_UNIT = 0;
    public static final int NORMAL_TEXTURE_UNIT = 1;

    private String name;
    private Vector4f ambientColour;
    private Vector4f diffuseColour;
    private Vector4f specularColour;

    private float shininess;
    private float reflectance;

    private Texture ambientTexture;
    private Texture diffuseTexture;
    private Texture normalTexture;
    private Texture alphaTexture;
    private Texture bumpTexture;
    private Texture displacementTexture;
    private Texture specularTexture;
    private Texture specularExponentTexture;


    public Material() {
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.diffuseTexture = null;
        this.normalTexture = null;
        this.reflectance = 0;
    }

    public Material(String name) {
        this.name = name;
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.diffuseTexture = null;
        this.normalTexture = null;
        this.reflectance = 0;
    }

    public void delete() {
        if (diffuseTexture != null)
            diffuseTexture.delete();
        if (normalTexture != null)
            normalTexture.delete();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector4f getAmbient() {
        return ambientColour;
    }

    public void setAmbient(Vector4f ambient) {
        this.ambientColour = ambient;
    }

    public Vector4f getDiffuse() {
        return diffuseColour;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuseColour = diffuse;
    }

    public Vector4f getSpecular() {
        return specularColour;
    }

    public void setSpecular(Vector4f specular) {
        this.specularColour = specular;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Texture getAmbientTexture() {
        return ambientTexture;
    }

    public void setAmbientTexture(Texture ambientTexture) {
        this.ambientTexture = ambientTexture;
    }

    public Texture getAlphaTexture() {
        return alphaTexture;
    }

    public void setAlphaTexture(Texture alphaTexture) {
        this.alphaTexture = alphaTexture;
    }

    public Texture getDiffuseTexture() {
        return diffuseTexture;
    }

    public void setDiffuseTexture(Texture diffuseTexture) {
        this.diffuseTexture = diffuseTexture;
    }

    public Texture getNormalTexture() {
        return normalTexture;
    }

    public void setNormalTexture(Texture normalTexture) {
        this.normalTexture = normalTexture;
    }

    public Texture getBumpTexture() {
        return bumpTexture;
    }

    public void setBumpTexture(Texture bumpTexture) {
        this.bumpTexture = bumpTexture;
    }

    public Texture getDisplacementTexture() {
        return displacementTexture;
    }

    public void setDisplacementTexture(Texture displacementTexture) {
        this.displacementTexture = displacementTexture;
    }


    public Texture getSpecularTexture() {
        return specularTexture;
    }

    public void setSpecularTexture(Texture specularTexture) {
        this.specularTexture = specularTexture;
    }

    public Texture getSpecularExponentTexture() {
        return specularExponentTexture;
    }

    public void setSpecularExponentTexture(Texture specularExponentTexture) {
        this.specularExponentTexture = specularExponentTexture;
    }

    public boolean isTextured() {
        return diffuseTexture != null;
    }

    public boolean isNormal() {
        return normalTexture != null;
    }
}
