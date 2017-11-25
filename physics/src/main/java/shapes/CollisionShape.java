package shapes;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;
import utils.Color;

public abstract class CollisionShape implements Intersection {

    protected final float meshThickness = 0.02f;
    private final Color isIntersectingColour = new Color(255, 0, 0, 255);
    private final Color isNotIntersectingColour = new Color(42, 135, 211, 255);
    private final Model model;
    private boolean isMoving;
    private TexturedMesh mesh;
    private Vector4f meshColour = isNotIntersectingColour.getFloats();
    private Vector3f translation;

    public CollisionShape(Model model) {
        this.model = model;
    }

    @Override
    public void changeColour(boolean isIntersecting, CollisionShape collisionShape) {
        if (isIntersecting) {
            collisionShape.setMeshColour(collisionShape.isIntersectingColour);
            this.setMeshColour(collisionShape.isIntersectingColour);
        } else {
            collisionShape.setMeshColour(collisionShape.isNotIntersectingColour);
            this.setMeshColour(collisionShape.isNotIntersectingColour);
        }
    }

    public Model getModel() {
        return model;
    }

    public abstract TexturedMesh createTexturedMesh();

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public TexturedMesh getTexturedMesh() {
        return mesh;
    }

    public void setTexturedMesh(TexturedMesh mesh) {
        this.mesh = mesh;
    }

    public abstract void update();

    public Vector4f getMeshColour() {
        return meshColour;
    }

    void setMeshColour(Color color) {
        this.meshColour = color.getFloats();
    }

    Vector3f getTranslation() {
        return translation;
    }

    void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

}
