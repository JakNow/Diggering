package pl.oblivion.staticModels;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.lighting.Light;
import pl.oblivion.materials.Material;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.shaders.uniforms.*;
import pl.oblivion.utils.Maths;
import pl.oblivion.utils.MyFile;

public class StaticShader extends ShaderProgram {

	protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");

	protected UniformMaterial material = new UniformMaterial("material");
	protected UniformSampler diffuseTexture = new UniformSampler("diffuseTexture");
	protected UniformSampler normalTexture = new UniformSampler("normalTexture");

	protected UniformLight light = new UniformLight("light");

	protected UniformFloat brightness = new UniformFloat("brightness");

	public StaticShader() {
		super(new MyFile(SimpleApp.properties.getProperty("shader.static.vertex")),
				new MyFile(SimpleApp.properties.getProperty("shader.static.fragment")), "in_position", "in_textures","in_normals");
		super.storeAllUniformLocations(projectionMatrix, viewMatrix, transformationMatrix, diffuseTexture,
				normalTexture,brightness);
		super.storeAllComplexUniformLocation(material.getAllUniforms(),light.getAllUniforms());
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
