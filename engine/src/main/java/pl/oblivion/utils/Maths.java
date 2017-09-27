package pl.oblivion.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import pl.oblivion.base.Model;

public class Maths {

    /*public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);

        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = cameraPos.negate();
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }*/

    public static Matrix4f createTransformationMatrix(Model model) {
              return new Matrix4f().identity().translate(model.getPosition()).rotateX((float)Math.toRadians(model.getRotation().x)).rotateY((float)Math.toRadians(model.getRotation().y)).rotateZ((float)Math.toRadians(model.getRotation().z)).scale(model.getScale());
    }

}
