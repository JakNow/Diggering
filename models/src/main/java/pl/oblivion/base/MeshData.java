package pl.oblivion.base;

public abstract class MeshData {

	public int[] indices;
	public float[] vertices;
	public float[] textures;
	public float[] normals;
	public float[] tangents;

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getTextures() {
		return textures;
	}

	public void setTextures(float[] textures) {
		this.textures = textures;
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public float[] getTangents() {
		return tangents;
	}

	public void setTangents(float[] tangents) {
		this.tangents = tangents;
	}
}
