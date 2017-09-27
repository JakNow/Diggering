package pl.oblivion.objParser;

import org.joml.Vector3i;

class Face {

    private String usedMtl;
    private Vector3i vector1;
    private Vector3i vector2;
    private Vector3i vector3;

    public Face(String usedMtl, Vector3i vector1, Vector3i vector2, Vector3i vector3) {
        this.usedMtl = usedMtl;
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.vector3 = vector3;
    }

    public String getUsedMtl(){
        return usedMtl;
    }
    public Vector3i getVector1() {
        return vector1;
    }

    public void setVector1(Vector3i vector1) {
        this.vector1 = vector1;
    }

    public Vector3i getVector2() {
        return vector2;
    }

    public void setVector2(Vector3i vector2) {
        this.vector2 = vector2;
    }

    public Vector3i getVector3() {
        return vector3;
    }

    public void setVector3(Vector3i vector3) {
        this.vector3 = vector3;
    }

    @Override
    public String toString(){
        return "face mat["+this.getUsedMtl()+"], [v,vt,vn] = ["+this.vector1.x+","+this.vector1.y+","+this.vector1.z+"]["+this.vector2.x+","+this.vector2.y+","+this.vector2.z+"]["+this.vector3.x+","+this.vector3.y+","+this.vector3.z+"]";
    }
}
