package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.MeshData;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshCollider extends CollisionShape {

	private Map<ModelPart, List<Face>> faces;

	public MeshCollider(Model model, Map<ModelPart, List<Face>> faces) {
		super(model);
		this.faces = faces;
	}

	public static MeshCollider create(Model model) {
		Map<ModelPart, List<Face>> faces = new HashMap<>();
		for (ModelPart modelPart : model.getModelView().getModelParts()) {
			List<Face> newBatch = new ArrayList<>();
			for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
				MeshData meshData = texturedMesh.getMeshData();
				for (int i = 0; i < meshData.indices.length; i += 3) {
					int[] indexes = {meshData.indices[i], meshData.indices[i + 1], meshData.indices[i + 2]};
					Face.Vertex vertex1 = getVertex(indexes[0], meshData);
					Face.Vertex vertex2 = getVertex(indexes[1], meshData);
					Face.Vertex vertex3 = getVertex(indexes[2], meshData);

					newBatch.add(new Face(vertex1, vertex2, vertex3));
				}
			}
			faces.put(modelPart, newBatch);
		}

		return new MeshCollider(model, faces);
	}

	private static Face.Vertex getVertex(int index, MeshData meshData) {
		Vector3f pos = new Vector3f(meshData.vertices[index * 3], meshData.vertices[index * 3 + 1],
				meshData.vertices[index * 3 + 2]);
		Vector3f norm = new Vector3f(meshData.normals[index * 3], meshData.normals[index * 3 + 1],
				meshData.normals[index * 3 + 2]);
		return new Face.Vertex(pos, norm);
	}

	@Override
	public TexturedMesh createTexturedMesh() {
		return null;
	}

	@Override
	public void update() {

	}

	@Override
	public boolean intersection(AABB aabb) {
		return false;
	}

	@Override
	public boolean intersection(SphereCollider sphereCollider) {
		return false;
	}

	@Override
	public boolean intersection(CylinderCollider cylinderCollider) {
		return false;
	}

	@Override
	public boolean intersection(MeshCollider meshCollider) {
		return false;
	}

	public Map<ModelPart, List<Face>> getFaces() {
		return faces;
	}
}
