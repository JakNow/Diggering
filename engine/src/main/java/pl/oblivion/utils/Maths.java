package pl.oblivion.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.game.Camera;

public class Maths {

    public static Matrix4f createTransformationMatrix(Model model) {
        return new Matrix4f().identity().translate(model.getPosition()).rotateX((float) Math.toRadians(model.getRotation().x)).rotateY((float) Math.toRadians(model.getRotation().y)).rotateZ((float) Math.toRadians(model.getRotation().z)).scale(model.getScale());
    }

    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return new Matrix4f().identity()
                .rotate((float) Math.toRadians(camera.getRotation().x), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(camera.getRotation().y), new Vector3f(0, 1, 0))
                .rotate((float) Math.toRadians(camera.getRotation().z), new Vector3f(0, 0, 1))
                .translate(negativeCameraPos);
    }


}
