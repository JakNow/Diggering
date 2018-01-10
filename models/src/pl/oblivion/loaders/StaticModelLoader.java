package pl.oblivion.loaders;

import pl.oblivion.base.Mesh;
import pl.oblivion.staticModels.StaticMeshData;

public class StaticModelLoader {

	public static Mesh createMesh(StaticMeshData staticMeshData) {
		Mesh mesh = Mesh.create();
		mesh.bind();
		mesh.createIndexBuffer(staticMeshData.getIndices());
		mesh.createAttribute(0, staticMeshData.getVertices(), 3);
		mesh.createAttribute(1, staticMeshData.getTextures(), 2);
		mesh.createAttribute(2, staticMeshData.getNormals(), 3);
		mesh.unbind();

		return mesh;
	}
}
