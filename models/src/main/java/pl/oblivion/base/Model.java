package pl.oblivion.base;

import org.joml.Vector3f;

public abstract class Model {

    private final TexturedMesh[] texturedMeshes;
    private final Vector3f position;
    private final Vector3f rotation;
    private final float scale;

    public Model(Vector3f position, Vector3f rotation, float scale, TexturedMesh... texturedMeshes) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.texturedMeshes = texturedMeshes;
    }

    public TexturedMesh[] getTexturedMeshes() {
        return texturedMeshes;
    }

    public TexturedMesh getTexturedMesh() {
        return texturedMeshes[0];
    }

    public void delete() {
        for (TexturedMesh texturedMesh : texturedMeshes) {
            texturedMesh.getMesh().delete();
            texturedMesh.getMaterial().delete();
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
}
