package pl.oblivion.shapes;

public class Face {

    private Vertex point1;
    private Vertex point2;
    private Vertex point3;

    public Face(Vertex point1, Vertex point2, Vertex point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Vertex getPoint1() {
        return point1;
    }

    public void setPoint1(Vertex point1) {
        this.point1 = point1;
    }

    public Vertex getPoint2() {
        return point2;
    }

    public void setPoint2(Vertex point2) {
        this.point2 = point2;
    }

    public Vertex getPoint3() {
        return point3;
    }

    public void setPoint3(Vertex point3) {
        this.point3 = point3;
    }

    public String toString() {

        return "Face: " + point1.getPosition() + "/" + point1.getNormal() + " " + point2.getPosition() + "/" + point2.getNormal() + " " + point3.getPosition() + "/" + point3.getNormal();
    }
}
