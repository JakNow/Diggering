package pl.oblivion.base;

import pl.oblivion.materials.Material;

public class TexturedMesh {

    private final Mesh mesh;
    private final Material material;

    public TexturedMesh(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }
}
