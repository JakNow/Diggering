package utils;

import org.joml.Matrix4f;
import pl.oblivion.base.Model;

public class Maths {


    public static Matrix4f createTransformationMatrix(Model model) {
        return new Matrix4f().identity().rotateX((float) Math.toRadians(model.getRotation().x)).rotateY((float) Math.toRadians(model.getRotation().y)).rotateZ((float) Math.toRadians(model.getRotation().z)).scale(model.getScale());
    }
}
