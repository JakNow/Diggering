package pl.oblivion.base;

import pl.oblivion.objParser.ParsedObjectData;

public abstract class MeshData {

    private float[] vertices;
    private int[] indices;
    private float[] textures;

    public MeshData(float[] vertices, int[] indices, float[] textures) {
        this.vertices = vertices;
        this.indices = indices;
        this.textures = textures;
    }

    public MeshData(ParsedObjectData parsedObjectData){
        this.vertices = parsedObjectData.getVertices();
        this.indices = parsedObjectData.getIndices();
        this.textures = parsedObjectData.getTextures();
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public float[] getTextures() {
        return textures;
    }
}
