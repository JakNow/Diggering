package pl.oblivion.base;

public abstract class Model {

    private final TexturedMesh[] texturedMeshes;

    public Model(TexturedMesh... texturedMeshes) {
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
}
