package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class UniformMatrix extends Uniform {

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public UniformMatrix(String name) {
		super(name);
	}

	public void loadMatrix(Matrix4f matrix) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			matrix.get(fb);
			GL20.glUniformMatrix4fv(super.getLocation(), false, fb);
		}
	}

	@Override
	public void logMissingUniform() {
		logger = Logger.getLogger(UniformMatrix.class);
		logger.error("No uniform variable called "+name +" found!");
	}
}
