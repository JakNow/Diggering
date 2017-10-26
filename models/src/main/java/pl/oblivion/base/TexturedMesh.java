package pl.oblivion.base;

import pl.oblivion.loaders.StaticModelLoader;
import pl.oblivion.materials.Material;
import pl.oblivion.staticModels.StaticMeshData;

public class TexturedMesh {

    private final MeshData meshData;
    private final Mesh mesh;
    private final float furthestPoint;
    private final Material material;

    public TexturedMesh(MeshData meshData, Material material, float furthestPoint) {
        this.meshData = meshData;
        this.material = material;
        this.furthestPoint = furthestPoint;
        this.mesh = StaticModelLoader.createMesh((StaticMeshData) meshData);
    }

    public float getFurthestPoint() {
        return furthestPoint;
    }

    public MeshData getMeshData() {
        return meshData;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }
}
