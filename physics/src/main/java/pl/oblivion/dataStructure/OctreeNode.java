package pl.oblivion.dataStructure;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.SphereCollider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OctreeNode {

    private final int currentDepth;
    private final Vector3f center;
    private final OctreeNode[] children;

    private final Map<Model,Model> objects;

    private Map<Model,Model> tempObjects;

    OctreeNode(Vector3f pos, float halfWidth, int stopDepth) {
        this.currentDepth = stopDepth;
        this.center = pos;
        this.objects = new HashMap<>();
        this.tempObjects = new HashMap<>();
        Vector3f offset = new Vector3f();

        if(stopDepth > 0){
            this.children = new OctreeNode[8];
            float step = halfWidth * 0.5f;

            for(int i = 0; i < 8; i++){
                offset.x = (((i & 1) == 0) ? step : -step);
                offset.y = (((i & 2) == 0) ? step : -step);
                offset.z = (((i & 4) == 0) ? step : -step);

                children[i] = new OctreeNode(new Vector3f(pos).add(offset),step,stopDepth-1);
            }
        }else{
            this.children = null;
        }
        }

    public void insertModel(final Model model){
        int index = 0;
        boolean straddle = false;
        float delta;


        final float[] objPos = {model.getPosition().x,model.getPosition().y,model.getPosition().z};
        final float[] nodePos = {center.x,center.y,center.z};

        for( int i = 0; i < 3; i++){
            delta = nodePos[i]-objPos[i];

            if(Math.abs(delta) <= model.getModelView().getFurthestPoint()){
                straddle = true;
                break;
            }

            if (delta > 0.0f)
                index |= (1<<i);
        }

        if(!straddle && currentDepth >0)
            children[index].insertModel(model);
        else {
            objects.put(model,model);
        }

    }

    public void clean(){
        this.objects.clear();

        if (currentDepth > 0){
            for(OctreeNode child : children){
                child.clean();
            }
        }
    }

    public void update(){
        this.tempObjects.clear();
        createTempObjectsList();
        if(currentDepth > 0){
            for(OctreeNode child : children){
                child.update();
            }
        }
    }

    private void createTempObjectsList(){
        this.tempObjects.putAll(objects);
        if(currentDepth>0){
            for(OctreeNode child : children){
                child.createTempObjectsList();
            }
        }
        if(this.tempObjects.size() != 0)
            collisionCheck();
    }

    private void collisionCheck(){
        List<ModelPair> pairList = new ArrayList<>();
        Map<Model,Model> tempMap = new HashMap<>(tempObjects);

       for(Model model : tempObjects.keySet()){
           tempMap.remove(model);
           CollisionShape collisionShape1 = tempObjects.get(model).getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
           for(Model testModel : tempMap.keySet()){
               CollisionShape collisionShape2 = tempObjects.get(testModel).getComponent(CollisionComponent.class).getBroadPhaseCollisionShape();
               if(intersection(collisionShape1,collisionShape2))
                   pairList.add(new ModelPair(tempObjects.get(model),tempObjects.get(testModel)));

           }
       }
    }

    private boolean intersection(CollisionShape collisionShape1, CollisionShape collisionShape2){
        String className = collisionShape2.getClass().getName();
        if(className.contains("AABB"))
            return collisionShape1.intersection((AABB) collisionShape2);
        else if (className.contains("CylinderCollider"))
            return collisionShape1.intersection((CylinderCollider) collisionShape2);
        else
            return className.contains("SphereCollider") && collisionShape1.intersection((SphereCollider) collisionShape2);
    }
}
