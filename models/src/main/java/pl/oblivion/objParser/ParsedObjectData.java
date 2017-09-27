package pl.oblivion.objParser;

import pl.oblivion.base.AABB;

public class ParsedObjectData {

    private final int[] indices;
    private final float[] vertices;
    private final float[] textures;
    private final float[] normals;
    private final AABB aabb;

    public ParsedObjectData(int[] indices, float[] vertices, float[] textures, float[] normals, AABB aabb) {
        this.indices = indices;
        this.vertices = vertices;
        this.textures = textures;
        this.normals = normals;
        this.aabb = aabb;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextures() {
        return textures;
    }

    public float[] getNormals() {
        return normals;
    }

    public AABB getAabb() {
        return aabb;
    }
}
