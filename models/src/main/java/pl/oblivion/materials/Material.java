package pl.oblivion.materials;

import org.joml.Vector3f;

public class Material {

    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float shiniess;

    private Texture diffuseTexture;
    private Texture normalTexture;

    public Material() {

    }

    public void delete() {
        if (diffuseTexture != null)
            diffuseTexture.delete();
        if (normalTexture != null)
            normalTexture.delete();
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

    public float getShiniess() {
        return shiniess;
    }

    public void setShiniess(float shiniess) {
        this.shiniess = shiniess;
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
}
