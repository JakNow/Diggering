package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public abstract class Uniform {

	static Logger logger = Logger.getLogger(Uniform.class);
	private static final int NOT_FOUND = - 1;
	String name;
	private int location;

	public Uniform(String name) {
		this.name = name;
	}

	public void storeUniformLocation(int programID) {
		location = GL20.glGetUniformLocation(programID, name);
		if (location == NOT_FOUND) {
			logMissingUniform();
		}
	}

	public abstract void logMissingUniform();

	int getLocation() {
		return location;
	}
}
