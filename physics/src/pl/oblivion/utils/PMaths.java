package pl.oblivion.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
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
}
