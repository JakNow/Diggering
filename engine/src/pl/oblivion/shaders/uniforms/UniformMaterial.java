package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import pl.oblivion.materials.Material;

public class UniformMaterial extends Uniform {

	public UniformVec4 ambientColour;
	public UniformVec4 diffuseColour;
	public UniformVec4 specularColour;

	public UniformBoolean hasTexture;
	public UniformBoolean hasNormalMap;

	public UniformFloat reflectivity;
	public UniformFloat shininess;

	public UniformMaterial(String name) {
		super(name);
		ambientColour = new UniformVec4(name + ".ambientColour");
		diffuseColour = new UniformVec4(name + ".diffuseColour");
		specularColour = new UniformVec4(name + ".specularColour");

		hasTexture = new UniformBoolean(name + ".hasTexture");
		hasNormalMap = new UniformBoolean(name + ".hasNormalMap");

		reflectivity = new UniformFloat(name+".reflectivity");
		shininess = new UniformFloat(name+".shininess");
	}

	public void loadMaterial(Material material) {
		ambientColour.loadVec4(material.getAmbient());
		diffuseColour.loadVec4(material.getDiffuse());
		specularColour.loadVec4(material.getSpecular());

		hasTexture.loadBoolean(material.isTextured());
		hasNormalMap.loadBoolean(material.isNormal());

		reflectivity.loadFloat(material.getReflectance());
		shininess.loadFloat(material.getShininess());
	}

	@Override
	public void logMissingUniform() {
		logger = Logger.getLogger(UniformMaterial.class);
		logger.error("No uniform variable called " + name + " found!");
	}

	public Uniform[] getAllUniforms(){
		return new Uniform[]{ambientColour,diffuseColour,specularColour,hasTexture,hasNormalMap};
	}
}
