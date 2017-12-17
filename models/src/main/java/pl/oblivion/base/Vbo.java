package pl.oblivion.base;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Vbo {

	private final int id;
	private final int type;

	private Vbo(int id, int type) {
		this.id = id;
		this.type = type;
	}

	public static Vbo create(int type) {
		int id = glGenBuffers();
		return new Vbo(id, type);
	}

	public void bind() {
		glBindBuffer(type, id);
	}

	public void unbind() {
		glBindBuffer(type, 0);
	}

	public void storeData(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		storeData(buffer);
	}

	public void storeData(FloatBuffer data) {
		glBufferData(type, data, GL_STATIC_DRAW);
	}

	public void storeData(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		storeData(buffer);
	}

	public void storeData(IntBuffer data) {
		glBufferData(type, data, GL_STATIC_DRAW);
	}

	public void delete() {
		glDeleteBuffers(id);
	}
}
