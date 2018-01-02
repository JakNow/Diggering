package pl.oblivion.colliders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.MeshData;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.utils.Face;
import pl.oblivion.utils.PMaths;

import java.util.ArrayList;
import java.util.List;

public class MeshCollider extends BasicCollider {

	private static final ColliderType colliderType = ColliderType.MESH;

	private List<Face> faces;

	private MeshCollider(Model model, List<Face> faces) {
		super(model,colliderType);
		this.faces = faces;
	}

	public static MeshCollider create(Model model) {
		Matrix4f transofrmationMatrix = PMaths.createTransformationMatrix(model);
		List<Face> faces = new ArrayList<>();
		for (ModelPart modelPart : model.getModelView().getModelParts()) {
			for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
				MeshData meshData = texturedMesh.getMeshData();
				for (int i = 0; i < meshData.indices.length; i += 3) {
					int[] indexes = {meshData.indices[i], meshData.indices[i + 1], meshData.indices[i + 2]};
					Vector3f point1 = PMaths.createVector3f(
							transofrmationMatrix.transform(getVector(indexes[0], meshData.vertices)));
					Vector3f point2 = PMaths.createVector3f(
							transofrmationMatrix.transform(getVector(indexes[1], meshData.vertices)));
					Vector3f point3 = PMaths.createVector3f(
							transofrmationMatrix.transform(getVector(indexes[2], meshData.vertices)));

					Vector3f normal = PMaths.createVector3f(
							transofrmationMatrix.transform(getVector(indexes[0], meshData.normals)));
					faces.add(new Face(point1, point2, point3, normal));
				}
			}
		}

		return new MeshCollider(model, faces);
	}

	private static Vector4f getVector(int index, float[] data) {
		return new Vector4f(data[index * 3], data[index * 3 + 1],
				data[index * 3 + 2], 1.0f);
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

	public List<Face> getFaces() {
		return faces;
	}

	@Override
	public void update() {

	}
}
