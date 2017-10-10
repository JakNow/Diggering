package pl.oblivion.staticModels;

public class StaticMeshData {

    private int[] indices;
    private float[] vertices;
    private float[] textures;
    private float[] normals;
    private float[] tangents;

    public StaticMeshData(int[] indices, float[] vertices, float[] textures, float[] normals, float[] tangents) {
        this.indices = indices;
        this.vertices = vertices;
        this.textures = textures;
        this.normals = normals;
        this.tangents = tangents;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public float[] getTextures() {
        return textures;
    }

    public void setTextures(float[] textures) {
        this.textures = textures;
    }

    public float[] getNormals() {
        return normals;
    }

    public void setNormals(float[] normals) {
        this.normals = normals;
    }

    public float[] getTangents() {
        return tangents;
    }

    public void setTangents(float[] tangents) {
        this.tangents = tangents;
    }
}
