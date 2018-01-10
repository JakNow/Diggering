package pl.oblivion.lighting;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author jakubnowakowski
 * Created at 08.01.2018
 */
public class PointLight extends Light {

    public PointLight(Vector3f position, Vector4f colour) {
        super(position, colour);
    }
}
