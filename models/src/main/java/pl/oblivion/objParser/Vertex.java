package pl.oblivion.objParser;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

class Vertex {

    private static final int NO_INDEX = -1;

    private Vector3f position;
    private int textureIndex = NO_INDEX;
    private int normalIndex = NO_INDEX;
    private Vertex duplicateVertex = null;
    private int index;
    private float length;
    private List<Vector3f> tangents = new ArrayList<>();
    private Vector3f averagedTangent = new Vector3f(0, 0, 0);
    private Vector2f textureCoord;
    private Vector3f normalCoord;

    public Vertex(int index, Vector3f position) {
        this.index = index;
        this.position = position;
        this.length = position.length();
    }

    public Vertex(int index, Vector3f position, Vector2f textureCoord, Vector3f normalCoord){
        this.index = index;
        this.position = position;
        this.textureCoord = textureCoord;
        this.normalCoord = normalCoord;
        this.length = position.length();
    }


    public void addTangent(Vector3f tangent) {
        tangents.add(tangent);
    }

    //NEW
    public Vertex duplicate(int newIndex) {
        Vertex vertex = new Vertex(newIndex, position);
        vertex.tangents = this.tangents;
        return vertex;
    }

    public void averageTangents() {
        if (tangents.isEmpty()) {
            return;
        }
        for (Vector3f tangent : tangents) {
            averagedTangent.add(tangent);
        }
        averagedTangent.normalize();
    }

    public Vector3f getAverageTangent() {
        return averagedTangent;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getLength() {
        return length;
    }

    public boolean isSet() {
        return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
    }

    public boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther) {
        return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public int getNormalIndex() {
        return normalIndex;
    }

    public void setNormalIndex(int normalIndex) {
        this.normalIndex = normalIndex;
    }

    public Vertex getDuplicateVertex() {
        return duplicateVertex;
    }

    public void setDuplicateVertex(Vertex duplicateVertex) {
        this.duplicateVertex = duplicateVertex;
    }

}
