package pl.oblivion.base;

public class ModelPart {

    private final AABB aabb;
    private final TexturedMesh[] texturedMeshes;

    public ModelPart(AABB aabb, TexturedMesh... texturedMeshes) {
        this.aabb = aabb;
        this.texturedMeshes = texturedMeshes;
    }

    public AABB getAabb() {
        return aabb;
    }

    public TexturedMesh[] getTexturedMeshes() {
        return texturedMeshes;
    }
}
