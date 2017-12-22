package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class UniformSampler extends Uniform {

	private int currentValue;
	private boolean used = false;

	public UniformSampler(String name) {
		super(name);
	}

	public void loadTexUnit(int texUnit) {
		if (! used || currentValue != texUnit) {
			GL20.glUniform1i(super.getLocation(), texUnit);
			used = true;
			currentValue = texUnit;
		}
	}

	@Override
	public void logMissingUniform() {
		logger = Logger.getLogger(UniformSampler.class);
		logger.error("No uniform variable called "+name +" found!");
	}
}
