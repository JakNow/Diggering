package pl.oblivion.collisionMesh;

import org.joml.Matrix4f;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.shaders.RendererHandler;
import pl.oblivion.shapes.CollisionShape;
import pl.oblivion.utils.Maths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionMeshHandler extends RendererHandler {

    private Map<CollisionShape, List<Model>> collisionShapeListMap = new HashMap<>();

    private CollisionMeshShader shader;

    CollisionMeshHandler(CollisionMeshShader shader) {
        this.shader = shader;
        bindingAttributes = new int[]{0};
    }

    @Override
    public void delete() {
        for (CollisionShape collisionShape : collisionShapeListMap.keySet()) {
            for (Model model : collisionShapeListMap.get(collisionShape)) {
                model.delete();
            }
        }
    }

    @Override
    public void prepareModel(TexturedMesh texturedMesh) {
        //IGNORE
    }

    @Override
    public void prepareInstance(Model model) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrixMesh(model);
        shader.transformationMatrix.loadMatrix(transformationMatrix);
    }

    @Override
    public void unbindTexturedMesh(TexturedMesh texturedMesh) {
        //IGNORE
    }

    public void prepareModel(CollisionShape collisionShape) {
        collisionShape.getTexturedMesh().getMesh().bind(bindingAttributes);
        shader.meshColour.loadVec4(collisionShape.getMeshColour());
    }

    public void unbindCollisionShape(CollisionShape collisionShape) {
        collisionShape.getTexturedMesh().getMesh().unbind();
    }


    @Override
    public void processModel(Model model) {

        List<Model> batch = collisionShapeListMap.get(model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());
        if (batch != null) {
            batch.add(model);
        } else {
            List<Model> newBatch = new ArrayList<>();
            newBatch.add(model);
            collisionShapeListMap.put(model.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape(), newBatch);
        }
    }

    Map<CollisionShape, List<Model>> getCollisionShapeListMap() {
        return collisionShapeListMap;
    }
}
