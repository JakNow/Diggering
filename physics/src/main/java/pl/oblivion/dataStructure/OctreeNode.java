package pl.oblivion.dataStructure;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

import java.util.ArrayList;

public class OctreeNode {

    private final int currentDepth;
    private final Vector3f center;
    private final OctreeNode[] children;

    private final ArrayList<Model> objects;

    OctreeNode(Vector3f pos, float halfWidth, int stopDepth) {
        this.currentDepth = stopDepth;
        this.center = pos;
        this.objects = new ArrayList<>();

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
            objects.add(model);
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
        if(currentDepth > 0){
            for(OctreeNode child : children){
                child.update();
            }
        }
    }
}
