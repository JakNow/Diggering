package pl.oblivion.shapes;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;

import java.util.HashMap;
import java.util.Map;

public class CylinderCollider extends CollisionShape {

    private final Vector3f centerDown;
    private final Vector3f centerTop;
    private final float radius;

    public CylinderCollider(Vector3f centerDown, Vector3f centerTop, float radius) {
        this.centerDown = centerDown;
        this.centerTop = centerTop;
        this.radius = radius;
    }

    /**
     * Creates map of CylinderCollider for each possible textured mesh inside model
     *
     * @param model retrieve every textured mesh of every model part inside model
     * @return map of every CylinderCollider inside model with textured meshes as keys
     */
    public static Map<TexturedMesh,CylinderCollider> createFullMapCylidernCollider(Model model){
        Map<TexturedMesh,CylinderCollider> map = new HashMap<>();
        for(ModelPart modelPart : model.getModelView().getModelParts()){
            for(TexturedMesh texturedMesh : modelPart.getTexturedMeshes()){
                map.put(texturedMesh,createCylinderCollider(texturedMesh));
            }
        }
        return map;
    }
    /**
     * Creates map of CylinderCollider for each model part inside model
     *
     * @param model retrieve all model parts inside model
     * @return map of CylinderCollider with model parts as keys
     */
    public static Map<ModelPart,CylinderCollider> createMapCylinderCollider(Model model){
        Map<ModelPart,CylinderCollider> map = new HashMap<>();
        for (ModelPart modelPart : model.getModelView().getModelParts()){
            map.put(modelPart,createCylinderCollider(modelPart));
        }

        return map;
    }
    /**
     * Creates map of CylinderCollider for each Textured Mesh in model part
     *
     * @param modelPart creates CylinderCollider for each textured mesh inside model part
     * @return map of CylinderCollider with textured mesh as keys
     */
    public static Map<TexturedMesh,CylinderCollider> createMapCylidernCollider(ModelPart modelPart){
        Map<TexturedMesh,CylinderCollider> map = new HashMap<>();
        for(TexturedMesh texturedMesh : modelPart.getTexturedMeshes()){
            map.put(texturedMesh,createCylinderCollider(texturedMesh));
        }

        return map;
    }
    /**
     * Creates CylliderCollider  for a whole model, creating AABB from model part, finding axis to create CyllinderCollider on based on the smallest surface area
     * @param model used to create AABB from, finding down and top center points and getting radius of cylinder
     * @return CylinderCollider with center down and top points and radius
     */
    public static CylinderCollider createCylinderCollider(Model model){
        AABB aabb = AABB.createAABB(model);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();
        float xDistance = Math.abs(max.x - min.x);
        float yDistance = Math.abs(max.y - min.y);
        float zDistance = Math.abs(max.z - min.z);

        float xAxis = zDistance*yDistance;
        float yAxis = xDistance*zDistance;
        float zAxis =  xDistance*yDistance;

        //xAxis
        if(xAxis <= yAxis && xAxis <= zAxis){
            Vector3f centerDown = new Vector3f(min.x,max.y - min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x,max.y - min.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);
        }
        //yAxis
        else if (yAxis <= xAxis && yAxis <= zAxis){
            Vector3f centerDown = new Vector3f(max.x - min.x,min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x - min.x,max.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);

        }
        //zAxis
        else if (zAxis <= yAxis && zAxis <= yAxis){
            Vector3f centerDown = new Vector3f(max.x - min.x,max.y - min.y,min.z);
            Vector3f centerTop = new Vector3f(max.x - min.x,max.y - min.y,max.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);

        }else{
            Vector3f centerDown = new Vector3f(min.x,max.y - min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x,max.y - min.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);
        }
    }

    /**
     * Creates CylliderCollider  for a model part, creating AABB from model part, finding axis to create CyllinderCollider on based on the smallest surface area
     * @param modelPart used to create AABB from, finding down and top center points and getting radius of cylinder
     * @return CylinderCollider with center down and top points and radius
     */
    public static CylinderCollider createCylinderCollider(ModelPart modelPart){
        AABB aabb = AABB.createAABB(modelPart);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();
        float xDistance = Math.abs(max.x - min.x);
        float yDistance = Math.abs(max.y - min.y);
        float zDistance = Math.abs(max.z - min.z);

        float xAxis = zDistance*yDistance;
        float yAxis = xDistance*zDistance;
        float zAxis =  xDistance*yDistance;

        //xAxis
        if(xAxis <= yAxis && xAxis <= zAxis){
            Vector3f centerDown = new Vector3f(min.x,max.y - min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x,max.y - min.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);
        }
        //yAxis
        else if (yAxis <= xAxis && yAxis <= zAxis){
            Vector3f centerDown = new Vector3f(max.x - min.x,min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x - min.x,max.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);

        }
        //zAxis
        else if (zAxis <= yAxis && zAxis <= yAxis){
            Vector3f centerDown = new Vector3f(max.x - min.x,max.y - min.y,min.z);
            Vector3f centerTop = new Vector3f(max.x - min.x,max.y - min.y,max.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);

        }else{
            Vector3f centerDown = new Vector3f(min.x,max.y - min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x,max.y - min.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);
        }
    }
    /**
     * Creates CylliderCollider  for a single textured mesh, creating AABB from textured mesh, finding axis to create CyllinderCollider on based on the smallest surface area
     * @param texturedMesh used to create AABB from, finding down and top center points and getting radius of cylinder
     * @return CylinderCollider with center down and top points and radius
     */
    public static CylinderCollider createCylinderCollider(TexturedMesh texturedMesh){
        AABB aabb = AABB.createAABB(texturedMesh);
        Vector3f min = aabb.getMin();
        Vector3f max = aabb.getMax();
        float xDistance = Math.abs(max.x - min.x);
        float yDistance = Math.abs(max.y - min.y);
        float zDistance = Math.abs(max.z - min.z);

        float xAxis = zDistance*yDistance;
        float yAxis = xDistance*zDistance;
        float zAxis =  xDistance*yDistance;

        //xAxis
        if(xAxis <= yAxis && xAxis <= zAxis){
           Vector3f centerDown = new Vector3f(min.x,max.y - min.y,max.z - min.z);
           Vector3f centerTop = new Vector3f(max.x,max.y - min.y,max.z - min.z);
           float radius = centerDown.distance(min);

           return new CylinderCollider(centerDown,centerTop,radius);
        }
        //yAxis
        else if (yAxis <= xAxis && yAxis <= zAxis){
            Vector3f centerDown = new Vector3f(max.x - min.x,min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x - min.x,max.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);

        }
        //zAxis
        else if (zAxis <= yAxis && zAxis <= yAxis){
            Vector3f centerDown = new Vector3f(max.x - min.x,max.y - min.y,min.z);
            Vector3f centerTop = new Vector3f(max.x - min.x,max.y - min.y,max.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);

        }else{
            Vector3f centerDown = new Vector3f(min.x,max.y - min.y,max.z - min.z);
            Vector3f centerTop = new Vector3f(max.x,max.y - min.y,max.z - min.z);
            float radius = centerDown.distance(min);

            return new CylinderCollider(centerDown,centerTop,radius);
        }
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


    public Vector3f getCenterDown() {
        return centerDown;
    }

    public Vector3f getCenterTop() {
        return centerTop;
    }

    public float getRadius() {
        return radius;
    }
}
