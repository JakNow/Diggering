package pl.oblivion.shapes;

import java.util.ArrayList;
import java.util.List;

public class MeshCollider extends CollisionShape {

    private final List<Face> faces;

    private List<Face> currentFaces;

    public MeshCollider(List<Face> faces) {
        this.faces = faces;
        this.currentFaces = new ArrayList<>();
        this.currentFaces.addAll(faces);
    }


    @Override
    public boolean intersectAABB(AABB aabb) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectSphere(SphereCollider sphereCollider) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectMesh(MeshCollider meshCollider) {
        //TODO
        return false;
    }

    @Override
    public boolean intersectCylinder(CylinderCollider cylinderCollider) {
        //TODO
        return false;
    }

    public List<Face> getFaces() {
        return faces;
    }

    @Override
    public void updatePosition() {
        for(int i = 0; i < faces.size();i++){
            currentFaces.get(i).getPoint1().setPosition(faces.get(i).getPoint1().getPosition().add(this.getModel().getPosition()));
            currentFaces.get(i).getPoint2().setPosition(faces.get(i).getPoint2().getPosition().add(this.getModel().getPosition()));
            currentFaces.get(i).getPoint3().setPosition(faces.get(i).getPoint3().getPosition().add(this.getModel().getPosition()));
        }
    }
}
