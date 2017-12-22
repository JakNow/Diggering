package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform {

	private boolean currentBool;
	private boolean used = false;

	public UniformBoolean(String name) {
		super(name);
	}

	@Override
	public void logMissingUniform() {
		logger = Logger.getLogger(UniformBoolean.class);
		logger.error("No uniform variable called "+name +" found!");
	}

	public void loadBoolean(boolean bool) {
		if (! used || currentBool != bool) {
			GL20.glUniform1i(super.getLocation(), bool ? 1 : 0);
			used = true;
			currentBool = bool;
		}
	}
}
