package pl.oblivion.staticModels;

import pl.oblivion.base.MeshData;

import java.util.Arrays;

public class StaticMeshData extends MeshData {

	public StaticMeshData(int[] indices, float[] vertices, float[] textures, float[] normals, float[] tangents) {
		this.indices = indices;
		this.vertices = vertices;
		this.textures = textures;
		this.normals = normals;
		this.tangents = tangents;
	}

	public StaticMeshData(MeshData meshData) {
		this.indices = Arrays.copyOf(meshData.indices, meshData.indices.length);
		this.vertices = Arrays.copyOf(meshData.vertices, meshData.vertices.length);
		this.textures = Arrays.copyOf(meshData.textures, meshData.textures.length);
		this.normals = Arrays.copyOf(meshData.normals, meshData.normals.length);
		this.tangents = (meshData.tangents != null) ? Arrays.copyOf(meshData.tangents, meshData.tangents.length) : null;
	}

}
