package pl.oblivion.shapes;

import org.joml.Vector3f;

public class Vertex {
    private Vector3f position;
    private final Vector3f normal;

    public Vertex(Vector3f position, Vector3f normal) {
        this.position = position;
        this.normal = normal;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getNormal() {
        return normal;
    }
}