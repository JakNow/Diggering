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

    private SphereCollider(Model model, Vector3f center, float radius) {
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

        assert texturedMesh != null;
        float[] sphereVertices = texturedMesh.getMeshData().vertices;
        for (int i = 0; i < sphereVertices.length; i += 3) {
            sphereVertices[i] = sphereVertices[i] * radius;
            sphereVertices[i + 1] = sphereVertices[i + 1] * radius + aabb.getHeight();
            sphereVertices[i + 2] = sphereVertices[i + 2] * radius;
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

        float dx = Math.max(aabb.getTempMin().x, Math.min(this.tempCenter.x, aabb.getTempMax().x));
        float dy = Math.max(aabb.getTempMin().y, Math.min(this.tempCenter.y, aabb.getTempMax().y));
        float dz = Math.max(aabb.getTempMin().z, Math.min(this.tempCenter.z, aabb.getTempMax().z));

        float distance = (float) Math.sqrt((dx - this.tempCenter.x) * (dx - this.tempCenter.x) +
                (dy - this.tempCenter.y) * (dy - this.tempCenter.y) +
                (dz - this.tempCenter.z) * (dz - this.tempCenter.z));

        if (distance < this.radius)
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

    //Not perfect
    @Override
    public boolean intersection(CylinderCollider cylinderCollider) {
        boolean isIntersecting = false;

        float dy = Math.max(cylinderCollider.getTempCenter().y - cylinderCollider.getHeight(), Math.min(this.tempCenter.y, cylinderCollider.getTempCenter().y + cylinderCollider.getHeight()));

        float distance = (float) Math.sqrt((this.tempCenter.x - cylinderCollider.getTempCenter().x) * (this.tempCenter.x - cylinderCollider.getTempCenter().x) + (dy - cylinderCollider.getTempCenter().y) * (dy - cylinderCollider.getTempCenter().y) + (this.tempCenter.z - cylinderCollider.getTempCenter().z) * (this.tempCenter.z - cylinderCollider.getTempCenter().z));

        if (distance < cylinderCollider.getRadius() + this.radius)
            isIntersecting = true;

        changeColour(isIntersecting, cylinderCollider);

        return isIntersecting;
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
