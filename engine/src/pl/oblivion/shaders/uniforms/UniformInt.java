package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class UniformInt extends Uniform {

	private int currentValue;
	private boolean used = false;

	public UniformInt(String name) {
		super(name);
	}

	public void loadInt(int value) {
		if (! used || currentValue != value) {
			GL20.glUniform1i(super.getLocation(), value);
			used = true;
			currentValue = value;
		}
	}

	@Override
	public void logMissingUniform() {
		logger = Logger.getLogger(UniformInt.class);
		logger.error("No uniform variable called "+name +" found!");
	}
}
