package pl.oblivion.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.Model;

public class PMaths {

	public static Matrix4f createTransformationMatrix(Model model) {
		return new Matrix4f().identity().translate(model.getPosition())
				.rotateX((float) Math.toRadians(model.getRotation().x))
				.rotateY((float) Math.toRadians(model.getRotation().y))
				.rotateZ((float) Math.toRadians(model.getRotation().z)).scale(model.getScale());
	}

	public static float[] vecToArray(Vector3f vector3f) {
		return new float[]{vector3f.x, vector3f.y, vector3f.z};
	}

	public static float barryCentric(Vector2f pos, Vector3f p1, Vector3f p2, Vector3f p3) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static float barryCentric(Vector3f pos, Vector3f p1, Vector3f p2, Vector3f p3) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Vector3f createVector3f(Vector4f vector4f) {
		return new Vector3f(vector4f.x, vector4f.y, vector4f.z);
	}
}
