package pl.oblivion.world;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.SphereCollider;

public class GenerateCollisionShapes {

    /**
     * Creates AABB for a whole model, retriving smallest and highest values of x,y and z axis and generates min and max points from it
     *
     * @param model model to generate AABB from
     * @return AABB based on the model
     */
    public static AABB createAABB(Model model){
        Vector3f min = new Vector3f(10000,10000,10000);
        Vector3f max = new Vector3f(-10000,-10000,-10000);

        for(ModelPart modelPart : model.getModelView().getModelParts()){
            for(TexturedMesh texturedMesh : modelPart.getTexturedMeshes()){
                float[] vertices = texturedMesh.getStaticMeshData().getVertices();
                for(int i = 0; i < vertices.length;i+=3){
                    min.x = getMin(vertices[i],min.x);
                    min.y = getMin(vertices[i+1],min.y);
                    min.z = getMin(vertices[i+2],min.z);

                    max.x = getMax(vertices[i],max.x);
                    max.y = getMax(vertices[i+1],max.y);
                    max.z = getMax(vertices[i+2],max.z);
                }
            }
        }
        return new AABB(min,max);
    }

    /**
     * Creates AABB for a single model part (from model), retriving smallest and highest values of x,y and z axis and generates min and max points from it
     * @param modelPart model part to generate AAB from
     * @return AABB based on the model part
     */
    public static AABB createAABB(ModelPart modelPart){
        Vector3f min = new Vector3f(10000,10000,10000);
        Vector3f max = new Vector3f(-10000,-10000,-10000);

        for(TexturedMesh texturedMesh : modelPart.getTexturedMeshes()){
            float[] vertices = texturedMesh.getStaticMeshData().getVertices();
            for(int i = 0; i < vertices.length;i+=3){
                min.x = getMin(vertices[i],min.x);
                min.y = getMin(vertices[i+1],min.y);
                min.z = getMin(vertices[i+2],min.z);

                max.x = getMax(vertices[i],max.x);
                max.y = getMax(vertices[i+1],max.y);
                max.z = getMax(vertices[i+2],max.z);
            }
        }

        return new AABB(min, max);
    }

    /**
     * Creates AABB for a single textured mesh (from model part), retriving smallest and highest values of x,y and z axis and generates min and max points from it
     * @param texturedMesh model part to generate AAB from
     * @return AABB based on the textured mesh
     */
    public static AABB createAABB(TexturedMesh texturedMesh){
        Vector3f min = new Vector3f(10000,10000,10000);
        Vector3f max = new Vector3f(-10000,-10000,-10000);
        float[] vertices = texturedMesh.getStaticMeshData().getVertices();
        for(int i = 0; i < vertices.length;i+=3){
            min.x = getMin(vertices[i],min.x);
            min.y = getMin(vertices[i+1],min.y);
            min.z = getMin(vertices[i+2],min.z);

            max.x = getMax(vertices[i],max.x);
            max.y = getMax(vertices[i+1],max.y);
            max.z = getMax(vertices[i+2],max.z);
        }

        return new AABB(min,max);
    }

    public static SphereCollider getSpehereCollider(Model model){
        AABB tempAABB = createAABB(model);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x+max.x)/2,(min.y+max.y)/2,(min.z+max.z)/2);
        float length = center.distance(min);
        float length2 = center.distance(max);

        System.out.println("Min: "+min);
        System.out.println("Max: "+max);
        System.out.println("Center: "+center);
        System.out.println("length 1: "+length);
        System.out.println("length 2: "+length2);
        return new SphereCollider(center,length);
    }

    public static SphereCollider getSpehereCollider(ModelPart modelPart){
        AABB tempAABB = createAABB(modelPart);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x+max.x)/2,(min.y+max.y)/2,(min.z+max.z)/2);
        float length = center.distance(min);
        float length2 = center.distance(max);

        System.out.println("Min: "+min);
        System.out.println("Max: "+max);
        System.out.println("Center: "+center);
        System.out.println("length 1: "+length);
        System.out.println("length 2: "+length2);
        return new SphereCollider(center,length);
    }

    public static SphereCollider getSpehereCollider(TexturedMesh texturedMesh){
        AABB tempAABB = createAABB(texturedMesh);
        Vector3f min = tempAABB.getMin();
        Vector3f max = tempAABB.getMax();

        Vector3f center = new Vector3f((min.x+max.x)/2,(min.y+max.y)/2,(min.z+max.z)/2);
        float length = center.distance(min);
        float length2 = center.distance(max);

        System.out.println("Min: "+min);
        System.out.println("Max: "+max);
        System.out.println("Center: "+center);
        System.out.println("length 1: "+length);
        System.out.println("length 2: "+length2);
        return new SphereCollider(center,length);
    }

    private static float getMin(float compare, float min){
        if(compare < min)
            return compare;
        return min;
    }

    private static float getMax(float compare, float max){
        if (compare > max)
            return compare;
        return max;
    }
}
