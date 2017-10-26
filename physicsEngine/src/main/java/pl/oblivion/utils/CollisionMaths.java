package pl.oblivion.utils;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.Model;

public class CollisionMaths {
    public static Vector3f transformVector(Vector3f vector3f, Model model) {
        Matrix4f matrix4f = new Matrix4f().identity().translate(model.getPosition()).rotateX((float) Math.toRadians(model.getRotation().x)).rotateY((float) Math.toRadians(model.getRotation().y)).rotateZ((float) Math.toRadians(model.getRotation().z)).scale(model.getScale());

        Matrix3f matrix3f = new Matrix3f(matrix4f);
        Vector4f vec = matrix4f.transform(new Vector4f(vector3f, 1.0f));
        return new Vector3f(vec.x, vec.y, vec.z);
    }

    public static Vector3f transformVector(Model model, Vector3f dest) {
        Matrix4f matrix4f = new Matrix4f().identity();
        matrix4f.translate(model.getPosition()).scale(model.getScale());

        Vector4f vector4f = new Vector4f(dest, 1.0f);
        return new Vector3f(vector4f.x, vector4f.y, vector4f.z);
    }

    public static Matrix4f transformMatrix(Model model) {
        return new Matrix4f().identity().translate(model.getPosition()).scale(model.getScale());
    }
}
