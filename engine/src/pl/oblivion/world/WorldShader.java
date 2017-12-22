package pl.oblivion.world;

import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.materials.Material;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.shaders.uniforms.UniformMaterial;
import pl.oblivion.shaders.uniforms.UniformMatrix;
import pl.oblivion.shaders.uniforms.UniformSampler;
import pl.oblivion.utils.Maths;
import pl.oblivion.utils.MyFile;

public class WorldShader extends ShaderProgram {

	protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
	protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");
	protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");

	protected UniformMaterial material = new UniformMaterial("material");
	protected UniformSampler diffuseTexture = new UniformSampler("diffuseTexture");
	protected UniformSampler normalTexture = new UniformSampler("normalTexture");

	public WorldShader() {
		super(new MyFile(SimpleApp.properties.getProperty("shader.world.vertex")), new MyFile(SimpleApp.properties.getProperty("shader.world.fragment")), "in_position", "in_textures");
		super.storeAllUniformLocations(projectionMatrix, viewMatrix, transformationMatrix, diffuseTexture,
				normalTexture);
		this.storeMaterialUniforms();
		connectTextureUnits();
	}

	private void storeMaterialUniforms() {
		material.ambientColour.storeUniformLocation(this.programID);
		material.diffuseColour.storeUniformLocation(this.programID);
		material.specularColour.storeUniformLocation(this.programID);

		material.hasTexture.storeUniformLocation(this.programID);
		material.hasNormalMap.storeUniformLocation(this.programID);
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
