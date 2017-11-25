package shapes;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.Mesh;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;

public class SphereCollider extends CollisionShape {

    private final Vector3f center;
    private Vector3f tempCenter;
    private float radius;

    public SphereCollider(Model model, Vector3f center, float radius) {
        super(model);
        this.center = center;
        this.tempCenter = center;
        this.radius = radius;
        this.setTranslation(new Vector3f(center).sub(model.getPosition()));
        this.setTexturedMesh(createTexturedMesh());
    }

    public static SphereCollider create(Model model) {
        AABB aabb = AABB.create(model);
        return new SphereCollider(model, aabb.getCenter(), aabb.getCenter().distance(aabb.getCornerMin()));
    }

    @Override
    public TexturedMesh createTexturedMesh() {
        TexturedMesh texturedMesh = null;
        try {
            texturedMesh = StaticMeshLoader.load("primitives/sphere.obj.", null).getModelParts()[0].getTexturedMeshes()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        AABB aabb = AABB.create(getModel());
        float radius = aabb.getCenter().distance(aabb.getCornerMin());
        float[] sphereVertices = texturedMesh.getMeshData().vertices;
        for (int i = 0; i < sphereVertices.length; i += 3) {
            sphereVertices[i] = sphereVertices[i] * radius + aabb.getCenter().x;
            sphereVertices[i + 1] = sphereVertices[i + 1] * radius + aabb.getCenter().y;
            sphereVertices[i + 2] = sphereVertices[i + 2] * radius + aabb.getCenter().z;
        }

        Mesh mesh = Mesh.create();
        mesh.bind();
        mesh.createIndexBuffer(texturedMesh.getMeshData().indices);
        mesh.createAttribute(0, sphereVertices, 3);
        mesh.createAttribute(1, texturedMesh.getMeshData().textures, 2);
        mesh.unbind();

        return new TexturedMesh(mesh, texturedMesh.getMaterial());
    }

    @Override
    public void update() {
        if (isMoving()) {
            this.tempCenter = new Vector3f(getModel().getPosition()).add(this.getTranslation());
        }
    }

    @Override
    public boolean intersection(AABB aabb) {
        boolean isIntersecting = false;
        if ((Math.abs(this.tempCenter.x - aabb.getTempCenter().x) < (this.radius + aabb.getWidth())) &&
                (Math.abs(this.tempCenter.y - aabb.getTempCenter().y) < (this.radius + aabb.getHeight())) &&
                (Math.abs(this.tempCenter.z - aabb.getTempCenter().z) < (this.radius + aabb.getDepth())))
            isIntersecting = true;

        changeColour(isIntersecting, aabb);

        return isIntersecting;
    }

    @Override
    public boolean intersection(SphereCollider sphereCollider) {

        boolean isIntersecting = (this.tempCenter.distance(sphereCollider.tempCenter) < (this.radius + sphereCollider.radius));


        changeColour(isIntersecting, sphereCollider);

        return isIntersecting;
    }

    @Override
    public boolean intersection(CylinderCollider cylinderCollider) {
        boolean isIntersecting = false;

        if ((Math.abs(this.tempCenter.x - cylinderCollider.getTempCenter().x) < (this.radius + cylinderCollider.getRadius())) &&
                (Math.abs(this.tempCenter.y - cylinderCollider.getTempCenter().y) < (this.radius + cylinderCollider.getHeight())) &&
                (Math.abs(this.tempCenter.z - cylinderCollider.getTempCenter().z) < (this.radius + cylinderCollider.getRadius())))
            isIntersecting = true;
        changeColour(isIntersecting, cylinderCollider);

        return isIntersecting;
    }

    @Override
    public boolean intersection(CapsuleCollider capsuleCollider) {
        return false;
    }

    public Vector3f getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    public Vector3f getTempCenter() {
        return tempCenter;
    }
}
