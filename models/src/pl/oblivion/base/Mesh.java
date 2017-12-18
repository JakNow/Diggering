package pl.oblivion.base;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

	private static final int BYTES_PER_FLOAT = 4;
	private static final int BYTES_PER_INT = 4;
	public final int id;
	private List<Vbo> dataVbos = new ArrayList<>();
	private Vbo indexVbo;
	private int indexCount;

	private Mesh(int id) {
		this.id = id;
	}

	public static Mesh create() {
		int id = glGenVertexArrays();
		return new Mesh(id);
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void bind(int... attributes) {
		bind();
		for (int i : attributes) {
			glEnableVertexAttribArray(i);
		}
	}

	private void bind() {
		glBindVertexArray(id);
	}

	public void unbind(int... attributes) {
		for (int i : attributes) {
			glDisableVertexAttribArray(i);
		}
		unbind();
	}

	private void unbind() {
		glBindVertexArray(0);
	}

	public void createIndexBuffer(int[] indices) {
		this.indexVbo = Vbo.create(GL_ELEMENT_ARRAY_BUFFER);
		indexVbo.bind();
		indexVbo.storeData(indices);
		this.indexCount = indices.length;
	}

	public void createAttribute(int attribute, float[] data, int attrSize) {
		Vbo dataVbo = Vbo.create(GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeData(data);
		glVertexAttribPointer(attribute, attrSize, GL_FLOAT, false, attrSize * BYTES_PER_FLOAT, 0);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void createIntAttribute(int attribute, int[] data, int attrSize) {
		Vbo dataVbo = Vbo.create(GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeData(data);
		glVertexAttribIPointer(attribute, attrSize, GL_INT, attrSize * BYTES_PER_INT, 0);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void delete() {
		glDeleteVertexArrays(id);
		for (Vbo vbo : dataVbos) {
			vbo.delete();
		}
		indexVbo.delete();
	}
}
