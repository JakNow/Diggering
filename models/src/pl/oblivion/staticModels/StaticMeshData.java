package pl.oblivion.staticModels;

import pl.oblivion.base.MeshData;

public class StaticMeshData extends MeshData {

	public StaticMeshData(int[] indices, float[] vertices, float[] textures, float[] normals, float[] tangents) {
		this.indices = indices;
		this.vertices = vertices;
		this.textures = textures;
		this.normals = normals;
		this.tangents = tangents;
	}
}
