package pl.oblivion.staticModels;


import pl.oblivion.core.Config;
import pl.oblivion.game.Camera;
import pl.oblivion.materials.Material;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.shaders.UniformMaterial;
import pl.oblivion.shaders.UniformMatrix;
import pl.oblivion.shaders.UniformSampler;
import pl.oblivion.utils.Maths;
import pl.oblivion.utils.MyFile;

public class StaticShader extends ShaderProgram {

    protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
    protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
    protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");

    protected UniformMaterial material = new UniformMaterial("material");
    protected UniformSampler diffuseTexture = new UniformSampler("diffuseTexture");
    protected UniformSampler normalTexture = new UniformSampler("normalTexture");


    public StaticShader() {
        super(new MyFile(Config.STATIC_VERT), new MyFile(Config.STATIC_FRAG), "in_position", "in_textures");
        super.storeAllUniformLocations(projectionMatrix, viewMatrix, transformationMatrix, diffuseTexture, normalTexture, material.ambientColour, material.diffuseColour, material.specularColour, material.hasTexture, material.hasNormalMap);
        connectTextureUnits();
    }

    private void connectTextureUnits() {
        super.start();
        diffuseTexture.loadTexUnit(Material.DIFFUSE_TEXTURE_UNIT);
        normalTexture.loadTexUnit(Material.NORMAL_TEXTURE_UNIT);
        super.stop();
    }


    public void loadViewMatrix(Camera camera) {
        viewMatrix.loadMatrix(Maths.getViewMatrix(camera));
    }
}
