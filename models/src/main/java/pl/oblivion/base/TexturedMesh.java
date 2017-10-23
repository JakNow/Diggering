package pl.oblivion.base;

import pl.oblivion.loaders.StaticModelLoader;
import pl.oblivion.materials.Material;
import pl.oblivion.staticModels.StaticMeshData;

public class TexturedMesh {

    private final StaticMeshData staticMeshData;
    private final Mesh mesh;
    private final float furthestPoint;
    private final Material material;

    public TexturedMesh(StaticMeshData staticMeshData, Material material, float furthestPoint) {
        this.staticMeshData = staticMeshData;
        this.material = material;
        this.furthestPoint = furthestPoint;
        this.mesh = StaticModelLoader.createMesh(staticMeshData);
    }

    public float getFurthestPoint() {
        return furthestPoint;
    }

    public StaticMeshData getStaticMeshData() {
        return staticMeshData;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }
}
