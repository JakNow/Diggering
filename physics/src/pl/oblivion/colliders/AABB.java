package pl.oblivion.colliders;

import org.joml.Math;
import org.joml.*;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.utils.Face;
import pl.oblivion.utils.PMaths;

public class AABB extends BasicCollider {

	private static final ColliderType colliderType = ColliderType.AABB;
	private Vector3f cornerMin, cornerMax;
	private Vector3f center;
	private float width, height, depth;


	private AABB(Model model, Vector3f cornerMin, Vector3f cornerMax) {
		super(model,colliderType);
		this.cornerMin = cornerMin;
		this.cornerMax = cornerMax;

		processShape();

		this.center = new Vector3f(cornerMin).add(cornerMax).div(2);
		this.width = Math.abs(cornerMax.x - cornerMin.x) / 2;
		this.height = Math.abs(cornerMax.y - cornerMin.y) / 2;
		this.depth = Math.abs(cornerMax.z - cornerMin.z) / 2;

		this.setTranslation(new Vector3f(center).sub(model.getPosition()));
	}

	private void processShape() {
		Matrix4f transformationMatrix = PMaths.createTransformationMatrix(this.getModel());
		Vector4f[] corners = {
				new Vector4f(cornerMin.x, cornerMin.y, cornerMax.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMax.x, cornerMin.y, cornerMax.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMax.x, cornerMax.y, cornerMax.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMin.x, cornerMax.y, cornerMax.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMin.x, cornerMin.y, cornerMin.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMax.x, cornerMin.y, cornerMin.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMax.x, cornerMax.y, cornerMin.z, 1.0f).mul(transformationMatrix),
				new Vector4f(cornerMin.x, cornerMax.y, cornerMin.z, 1.0f).mul(transformationMatrix)};

		Vector3f tempMin = new Vector3f(10000, 10000, 10000);
		Vector3f tempMax = new Vector3f(- 10000, - 10000, - 10000);
		for (Vector4f vector4f : corners) {
			tempMin = getMin(vector4f, tempMin);
			tempMax = getMax(vector4f, tempMax);
		}

		cornerMax.set(tempMax);
		cornerMin.set(tempMin);
	}

	private static Vector3f getMin(Vector4f src, Vector3f dest) {
		return new Vector3f(getMin(src.x, dest.x), getMin(src.y, dest.y), getMin(src.z, dest.z));
	}

	private static Vector3f getMax(Vector4f src, Vector3f dest) {
		return new Vector3f(getMax(src.x, dest.x), getMax(src.y, dest.y), getMax(src.z, dest.z));
	}

	private static float getMin(float src, float dest) {
		if (src < dest) { return src; } else { return dest; }
	}

	private static float getMax(float src, float dest) {
		if (src > dest) { return src; } else { return dest; }
	}

	public static AABB create(Model model) {
		Vector3f tempMin = new Vector3f(10000, 10000, 10000);
		Vector3f tempMax = new Vector3f(- 10000, - 10000, - 10000);
		for (ModelPart modelPart : model.getModelView().getModelParts()) {
			for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
				float[] vertices = texturedMesh.getMeshData().vertices;
				for (int i = 0; i < vertices.length; i += 3) {
					Vector4f tempPoint = new Vector4f(vertices[i], vertices[i + 1], vertices[i + 2], 1.0f);
					tempMin = getMin(tempPoint, tempMin);
					tempMax = getMax(tempPoint, tempMax);
				}
			}
		}

		return new AABB(model, tempMin, tempMax);
	}

	@Override
	public void update(Vector3f position) {
		this.center = new Vector3f(position).add(getTranslation());
		this.cornerMin = new Vector3f(center.x-width,center.y-height,center.z-depth);
		this.cornerMax = new Vector3f(center.x+width,center.y+height,center.z+depth);
	}

	public Vector3f getCenter() {
		return center;
	}

	@Override
	public boolean intersection(AABB aabb) {
		return ((Math.abs(this.center.x - aabb.center.x) < (this.width + aabb.width)) &&
				(Math.abs(this.center.y - aabb.center.y) < (this.height + aabb.height)) &&
				(Math.abs(this.center.z - aabb.center.z) < (this.depth + aabb.depth)));
	}

	@Override
	public boolean intersection(SphereCollider sphereCollider) {

		float dx = Math.max(this.cornerMin.x, Math.min(sphereCollider.getCenter().x, this.cornerMax.x));
		float dy = Math.max(this.cornerMin.y, Math.min(sphereCollider.getCenter().y, this.cornerMax.y));
		float dz = Math.max(this.cornerMin.z, Math.min(sphereCollider.getCenter().z, this.cornerMax.z));

		float distance =
				(float) Math.sqrt((dx - sphereCollider.getCenter().x) * (dx - sphereCollider.getCenter().x) +
						(dy - sphereCollider.getCenter().y) * (dy - sphereCollider.getCenter().y) +
						(dz - sphereCollider.getCenter().z) * (dz - sphereCollider.getCenter().z));

		return distance < sphereCollider.getRadius();
	}

	@Override
	public boolean intersection(CylinderCollider cylinderCollider) {
		float dx = Math.max(this.cornerMin.x, Math.min(cylinderCollider.getCenter().x, this.cornerMax.x));
		float dz = Math.max(this.cornerMin.z, Math.min(cylinderCollider.getCenter().z, this.cornerMax.z));

		float distance = (float) Math
				.sqrt((dx - cylinderCollider.getCenter().x) * (dx - cylinderCollider.getCenter().x) +
						(dz - cylinderCollider.getCenter().z) * (dz - cylinderCollider.getCenter().z));

		return distance < cylinderCollider.getRadius() &&
				(Math.abs(this.center.y - cylinderCollider.getCenter().y) <
						(this.height + cylinderCollider.getHeight()));
	}

	@Override
	public boolean intersection(MeshCollider meshCollider) {
		for (Face face : meshCollider.getFaces()) {
			if (pointInTriangle(getModel().getPosition(), face.getPoint1(), face.getPoint2(), face.getPoint3())) {
				getModel().setHeight(PMaths.barryCentric(getModel().getPosition(), face.getPoint1(), face.getPoint2(),
						face.getPoint3()));
			}
		}
		return false;
	}

	private boolean pointInTriangle(Vector3f pt, Vector3f v1, Vector3f v2, Vector3f v3) {

		Vector2f ptV1 = new Vector2f(pt.x, pt.z).sub(v1.x, v1.z);
		Vector2f ptV2 = new Vector2f(pt.x, pt.z).sub(v2.x, v2.z);
		Vector2f ptV3 = new Vector2f(pt.x, pt.z).sub(v3.x, v3.z);
		float angleV1PV2 = Math.abs((float) Math.toDegrees(ptV1.angle(ptV2)));
		float angleV2PV3 = Math.abs((float) Math.toDegrees(ptV2.angle(ptV3)));
		float angleV3PV1 = Math.abs((float) Math.toDegrees(ptV3.angle(ptV1)));

		float sum = angleV1PV2 + angleV2PV3 + angleV3PV1;

		return sum == 360.0f;
	}

	public Vector3f getCornerMin() {
		return cornerMin;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getDepth() {
		return depth;
	}

	public Vector3f getCornerMax() {
		return cornerMax;
	}
}
