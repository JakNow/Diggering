package pl.oblivion.materials;

import org.joml.Vector3f;

public class Material {

    private String name;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float specularExponent;

    private Texture ambientTexture;
    private Texture diffuseTexture;
    private Texture normalTexture;
    private Texture alphaTexture;
    private Texture bumpTexture;
    private Texture displacementTexture;
    private Texture specularTexture;
    private Texture specularExponentTexture;


    public Material() {

    }

    public Material(String name){
        this.name = name;
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

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = specular;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
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
}
