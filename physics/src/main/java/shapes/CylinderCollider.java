package shapes;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.Mesh;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;

public class CylinderCollider extends CollisionShape {

    private static boolean isInscribed = true;
    private final Vector3f center;
    private Vector3f tempCenter;
    private float height, radius;

    public CylinderCollider(Model model, Vector3f center, float height, float radius) {
        super(model);
        this.center = center;
        this.tempCenter = center;
        this.height = height;
        this.radius = radius;
        this.setTranslation(new Vector3f(center).sub(model.getPosition()));
        this.setTexturedMesh(createTexturedMesh());
    }

    public static CylinderCollider create(Model model, boolean inscribed) {
        isInscribed = inscribed;
        AABB aabb = AABB.create(model);
        float radius = getRadius(aabb.getDepth(), aabb.getWidth());

        return new CylinderCollider(model, aabb.getCenter(), aabb.getHeight(), radius);
    }

    public static CylinderCollider create(Model model) {
        AABB aabb = AABB.create(model);
        float radius = getRadius(aabb.getDepth(), aabb.getWidth());

        return new CylinderCollider(model, aabb.getCenter(), aabb.getHeight(), radius);
    }

    private static float getRadius(float comp1, float comp2) {
        if (isInscribed)//(a>b?a:b);
            return comp1 > comp2 ? comp1 : comp2;
        else
            return (float) (Math.sqrt(Math.pow(comp1, 2) + Math.pow(comp2, 2)));

    }

    @Override
    public TexturedMesh createTexturedMesh() {
        TexturedMesh texturedMesh = null;
        try {
            texturedMesh = StaticMeshLoader.load("primitives/cylinder.obj.", null).getModelParts()[0].getTexturedMeshes()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        AABB aabb = AABB.create(getModel());
        float radius = getRadius(aabb.getDepth(), aabb.getWidth());

        float[] cylinderVertices = texturedMesh.getMeshData().vertices;
        for (int i = 0; i < cylinderVertices.length; i += 3) {
            cylinderVertices[i] = cylinderVertices[i] * radius + aabb.getCenter().x;
            cylinderVertices[i + 1] = cylinderVertices[i + 1] * aabb.getHeight() + aabb.getCenter().y;
            cylinderVertices[i + 2] = cylinderVertices[i + 2] * radius + aabb.getCenter().z;
        }
        Mesh mesh = Mesh.create();
        mesh.bind();
        mesh.createIndexBuffer(texturedMesh.getMeshData().indices);
        mesh.createAttribute(0, cylinderVertices, 3);
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
                (Math.abs(this.tempCenter.y - aabb.getTempCenter().y) < (this.height + aabb.getHeight())) &&
                (Math.abs(this.tempCenter.z - aabb.getTempCenter().z) < (this.radius + aabb.getDepth())))
            isIntersecting = true;
        changeColour(isIntersecting, aabb);

        return isIntersecting;
    }

    @Override
    public boolean intersection(SphereCollider sphereCollider) {
        boolean isIntersecting = false;

        if ((Math.abs(this.tempCenter.x - sphereCollider.getTempCenter().x) < (this.radius + sphereCollider.getRadius())) && (Math.abs(this.tempCenter.y - sphereCollider.getTempCenter().y) < (this.height + sphereCollider.getRadius())) && (Math.abs(this.tempCenter.z - sphereCollider.getTempCenter().z) < (this.radius + sphereCollider.getRadius())))
            isIntersecting = true;
        changeColour(isIntersecting, sphereCollider);

        return isIntersecting;
    }

    @Override
    public boolean intersection(CylinderCollider cylinderCollider) {
        boolean isIntersecting = false;

        if (Math.abs(this.tempCenter.x - cylinderCollider.tempCenter.x) < (this.radius + cylinderCollider.radius) &&
                (Math.abs(this.tempCenter.y - cylinderCollider.tempCenter.y) < (this.height + cylinderCollider.height) &&
                        (Math.abs(this.tempCenter.z - cylinderCollider.tempCenter.z) < (this.radius + cylinderCollider.radius))))
            isIntersecting = true;

        changeColour(isIntersecting, cylinderCollider);

        return isIntersecting;
    }

    @Override
    public boolean intersection(CapsuleCollider capsuleCollider) {
        return false;
    }

    public Vector3f getTempCenter() {
        return tempCenter;
    }

    public float getHeight() {
        return height;
    }

    public float getRadius() {
        return radius;
    }
}
