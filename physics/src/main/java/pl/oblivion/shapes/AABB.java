package pl.oblivion.shapes;

import org.joml.Matrix4f;
import org.joml.Planef;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.Mesh;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.utils.PMaths;

import static org.joml.Intersectionf.testAabPlane;

public class AABB extends CollisionShape {

    private final float meshThickness = 0.02f;
    private Vector3f cornerMin, cornerMax, tempMin, tempMax;
    private Vector3f center, tempCenter;
    private float width, height, depth;

    public AABB(Model model, Vector3f cornerMin, Vector3f cornerMax) {
        super(model);
        this.cornerMin = cornerMin;
        this.tempMin = cornerMin;
        this.cornerMax = cornerMax;
        this.tempMax = cornerMax;

        this.setTexturedMesh(createTexturedMesh());

        processShape();

        this.center = new Vector3f(cornerMin).add(cornerMax).div(2);
        this.width = Math.abs(cornerMax.x - cornerMin.x) / 2;
        this.height = Math.abs(cornerMax.y - cornerMin.y) / 2;
        this.depth = Math.abs(cornerMax.z - cornerMin.z) / 2;

        this.tempCenter = center;
        this.setTranslation(new Vector3f(center).sub(model.getPosition()));
    }

    public static AABB create(Model model) {
        Vector3f tempMin = new Vector3f(10000, 10000, 10000);
        Vector3f tempMax = new Vector3f(-10000, -10000, -10000);
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                float[] vertices = texturedMesh.getMeshData().vertices;
                for (int i = 0; i < vertices.length; i += 3) {
                    Vector4f tempPoint = new Vector4f(vertices[i], vertices[i + 1], vertices[i + 2], 1.0f);
                    tempMin = getMin(tempPoint, tempMin);
                    tempMax = getMax(tempPoint, tempMax);
                }
            }
        }

        return new AABB(model, tempMin, tempMax);
    }

    private static Vector3f getMin(Vector4f src, Vector3f dest) {
        return new Vector3f(getMin(src.x, dest.x), getMin(src.y, dest.y), getMin(src.z, dest.z));
    }

    private static Vector3f getMax(Vector4f src, Vector3f dest) {
        return new Vector3f(getMax(src.x, dest.x), getMax(src.y, dest.y), getMax(src.z, dest.z));
    }

    private static float getMin(float src, float dest) {
        if (src < dest)
            return src;
        else
            return dest;
    }

    private static float getMax(float src, float dest) {
        if (src > dest)
            return src;
        else
            return dest;
    }

    private void processShape() {
        Matrix4f transformationMatrix = PMaths.createTransformationMatrix(this.getModel());
        Vector4f[] corners = {
                new Vector4f(cornerMin.x, cornerMin.y, cornerMax.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMax.x, cornerMin.y, cornerMax.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMax.x, cornerMax.y, cornerMax.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMin.x, cornerMax.y, cornerMax.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMin.x, cornerMin.y, cornerMin.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMax.x, cornerMin.y, cornerMin.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMax.x, cornerMax.y, cornerMin.z, 1.0f).mul(transformationMatrix),
                new Vector4f(cornerMin.x, cornerMax.y, cornerMin.z, 1.0f).mul(transformationMatrix)};

        Vector3f tempMin = new Vector3f(10000, 10000, 10000);
        Vector3f tempMax = new Vector3f(-10000, -10000, -10000);
        for (Vector4f vector4f : corners) {
            tempMin = getMin(vector4f, tempMin);
            tempMax = getMax(vector4f, tempMax);
        }

        cornerMax.set(tempMax);
        cornerMin.set(tempMin);
    }

    @Override
    public TexturedMesh createTexturedMesh() {
        TexturedMesh texturedMesh = null;
        try {
            texturedMesh = StaticMeshLoader.load("primitives/cube.obj", null).getModelParts()[0].getTexturedMeshes()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert texturedMesh != null;
        float[] tempVert = texturedMesh.getMeshData().vertices;
        for (int i = 0; i < tempVert.length; i += 3) {
            tempVert[i] = convertVertices(tempVert[i], cornerMin.x, cornerMax.x);
            tempVert[i + 1] = convertVertices(tempVert[i + 1], cornerMin.y, cornerMax.y);
            tempVert[i + 2] = convertVertices(tempVert[i + 2], cornerMin.z, cornerMax.z);
        }

        Mesh mesh = Mesh.create();
        mesh.bind();
        mesh.createIndexBuffer(texturedMesh.getMeshData().indices);
        mesh.createAttribute(0, tempVert, 3);
        mesh.createAttribute(1, texturedMesh.getMeshData().textures, 2);
        mesh.unbind();


        return new TexturedMesh(mesh, texturedMesh.getMaterial());
    }

    private float convertVertices(float vertices, float cornerMin, float cornerMax) {
        if (vertices == 1)
            return cornerMax;
        else if (vertices == -1)
            return cornerMin;
        else if (vertices == 0.98f)
            return cornerMax - this.meshThickness;
        else if (vertices == -0.98f)
            return cornerMin - this.meshThickness;
        else return 0;
    }

    @Override
    public void update() {
        if (isMoving()) {
            this.tempCenter = new Vector3f(getModel().getPosition()).add(this.getTranslation());
            this.tempMax = new Vector3f(tempCenter.x + width, tempCenter.y + height, tempCenter.z + depth);
            this.tempMin = new Vector3f(tempCenter.x - width, tempCenter.y - height, tempCenter.z - depth);
        }
    }


    @Override
    public boolean intersection(MeshCollider meshCollider) {
        for (ModelPart modelPart : meshCollider.getFaces().keySet()) {
            for (Face face : meshCollider.getFaces().get(modelPart)) {
                Planef plane = face.getPlane();
                boolean test = testAabPlane(this.getTempMin(), this.getTempMax(), plane.a, plane.b, plane.c, plane.d);
                System.out.println(test);

            }
        }
        return false;
    }

    @Override
    public boolean intersection(AABB aabb) {
        boolean isIntersecting = false;
        if ((Math.abs(this.tempCenter.x - aabb.tempCenter.x) < (this.width + aabb.width)) &&
                (Math.abs(this.tempCenter.y - aabb.tempCenter.y) < (this.height + aabb.height)) &&
                (Math.abs(this.tempCenter.z - aabb.tempCenter.z) < (this.depth + aabb.depth)))
            isIntersecting = true;

        changeColour(isIntersecting, aabb);

        return isIntersecting;
    }

    @Override
    public boolean intersection(SphereCollider sphereCollider) {
        boolean isIntersecting = false;

        float dx = Math.max(this.tempMin.x, Math.min(sphereCollider.getTempCenter().x, this.tempMax.x));
        float dy = Math.max(this.tempMin.y, Math.min(sphereCollider.getTempCenter().y, this.tempMax.y));
        float dz = Math.max(this.tempMin.z, Math.min(sphereCollider.getTempCenter().z, this.tempMax.z));

        float distance = (float) Math.sqrt((dx - sphereCollider.getTempCenter().x) * (dx - sphereCollider.getTempCenter().x) +
                (dy - sphereCollider.getTempCenter().y) * (dy - sphereCollider.getTempCenter().y) +
                (dz - sphereCollider.getTempCenter().z) * (dz - sphereCollider.getTempCenter().z));

        if (distance < sphereCollider.getRadius())
            isIntersecting = true;

        changeColour(isIntersecting, sphereCollider);

        return isIntersecting;
    }

    @Override
    public boolean intersection(CylinderCollider cylinderCollider) {
        boolean isIntersecting = false;

        float dx = Math.max(this.tempMin.x, Math.min(cylinderCollider.getTempCenter().x, this.tempMax.x));
        float dz = Math.max(this.tempMin.z, Math.min(cylinderCollider.getTempCenter().z, this.tempMax.z));

        float distance = (float) Math.sqrt((dx - cylinderCollider.getTempCenter().x) * (dx - cylinderCollider.getTempCenter().x) +
                (dz - cylinderCollider.getTempCenter().z) * (dz - cylinderCollider.getTempCenter().z));

        if (distance < cylinderCollider.getRadius() && (Math.abs(this.tempCenter.y - cylinderCollider.getTempCenter().y) < (this.height + cylinderCollider.getHeight())))
            isIntersecting = true;

        changeColour(isIntersecting, cylinderCollider);
        return isIntersecting;
    }


    public Vector3f getCornerMin() {
        return cornerMin;
    }

    public Vector3f getCornerMax() {
        return cornerMax;
    }

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f getTempCenter() {
        return tempCenter;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDepth() {
        return depth;
    }

    public Vector3f getTempMin() {
        return tempMin;
    }

    public Vector3f getTempMax() {
        return tempMax;
    }
}
