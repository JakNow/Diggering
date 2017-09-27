package pl.oblivion.staticModels;

import pl.oblivion.base.MeshData;
import pl.oblivion.objParser.ParsedObjectData;

public class StaticMeshData extends MeshData {


    public StaticMeshData(float[] vertices, int[] indices, float[] textures) {
        super(vertices, indices,textures);
    }

    public StaticMeshData(ParsedObjectData parsedObjectData) {
        super(parsedObjectData);
    }
}
