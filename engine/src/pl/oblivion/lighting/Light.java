package pl.oblivion.lighting;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author jakubnowakowski
 * Created at 08.01.2018
 */
public abstract class Light {

    private Vector3f position;
    private Vector4f colour;

    public Light(Vector3f position, Vector4f colour ) {
        this.position = position;
        this.colour = colour;
    }

    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }


    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector4f getColour() {
        return colour;
    }

    public void setColour(Vector4f colour) {
        this.colour = colour;
    }
}
