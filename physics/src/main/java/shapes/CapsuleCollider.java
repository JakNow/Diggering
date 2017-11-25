package shapes;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.Mesh;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;

public class CapsuleCollider extends CollisionShape {

    private static boolean isInscribed = true;
    private final Vector3f center;
    private Vector3f tempCenter;
    private float height, radius;

    public CapsuleCollider(Model model, Vector3f center, float height, float radius) {
        super(model);
        this.center = center;
        this.tempCenter = center;
        this.height = height;
        this.radius = radius;
        this.setTranslation(new Vector3f(center).sub(model.getPosition()));
        this.setTexturedMesh(createTexturedMesh());
    }

    public static CapsuleCollider create(Model model, boolean inscribed) {
        isInscribed = inscribed;
        AABB aabb = AABB.create(model);
        float radius = getRadius(aabb.getDepth(), aabb.getWidth());

        return new CapsuleCollider(model, aabb.getCenter(), aabb.getHeight(), radius);
    }

    public static CapsuleCollider create(Model model) {
        AABB aabb = AABB.create(model);
        float radius = getRadius(aabb.getDepth(), aabb.getWidth());

        return new CapsuleCollider(model, aabb.getCenter(), aabb.getHeight(), radius);
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
            texturedMesh = StaticMeshLoader.load("primitives/capsule.obj", null).getModelParts()[0].getTexturedMeshes()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        AABB aabb = AABB.create(getModel());
        float radius = getRadius(aabb.getDepth(), aabb.getWidth());

        float[] capsuleVertices = texturedMesh.getMeshData().vertices;
        for (int i = 0; i < capsuleVertices.length; i += 3) {
            capsuleVertices[i] = capsuleVertices[i] * radius + aabb.getCenter().x;
            if (capsuleVertices[i + 1] >= 1.0f)
                capsuleVertices[i + 1] = capsuleVertices[i + 1] * radius + aabb.getCenter().y + aabb.getHeight() / 2 - radius / 2;
            else if (capsuleVertices[i + 1] <= -1.0f)
                capsuleVertices[i + 1] = capsuleVertices[i + 1] * radius + aabb.getCenter().y - aabb.getHeight() / 2 + radius / 2;
            else
                capsuleVertices[i + 1] = capsuleVertices[i + 1] * (aabb.getHeight() - radius) + aabb.getCenter().y;

            capsuleVertices[i + 2] = capsuleVertices[i + 2] * radius + aabb.getCenter().z;
        }

        Mesh mesh = Mesh.create();
        mesh.bind();
        mesh.createIndexBuffer(texturedMesh.getMeshData().indices);
        mesh.createAttribute(0, capsuleVertices, 3);
        mesh.createAttribute(1, texturedMesh.getMeshData().textures, 2);
        mesh.unbind();
        return new TexturedMesh(mesh, texturedMesh.getMaterial());
    }

    @Override
    public void update() {

    }

    @Override
    public boolean intersection(AABB aabb) {
        return false;
    }

    @Override
    public boolean intersection(SphereCollider sphereCollider) {
        return false;
    }

    @Override
    public boolean intersection(CylinderCollider cylinderCollider) {
        return false;
    }

    @Override
    public boolean intersection(CapsuleCollider capsuleCollider) {
        return false;
    }
}
