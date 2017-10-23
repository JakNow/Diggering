package pl.oblivion.staticModels;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.ModelView;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.shaders.RendererHandler;
import pl.oblivion.utils.Maths;
import pl.oblivion.world.Scene;
import pl.oblivion.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticRendererHandler extends RendererHandler {

    private Map<ModelView, List<StaticModel>> models = new HashMap<>();
    private StaticShader shader;

    StaticRendererHandler(StaticShader shader) {
        this.shader = shader;
        bindingAttributes = new int[]{0, 1};
    }


    @Override
    public void delete() {
        for (ModelView modelView : models.keySet()) {
            for (StaticModel model : models.get(modelView)) {
                model.delete();
            }
        }
    }

    public void processWorld(World world) {
        for (Object scene : world) {
            for (Object model : (Scene) scene) {
                processModel((Model) model);
            }
        }
    }

    @Override
    public void prepareModel(ModelView modelView) {
        for (ModelPart modelPart : modelView.getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                texturedMesh.getMesh().bind(bindingAttributes);
                shader.material.loadMaterial(texturedMesh.getMaterial());

                List<StaticModel> batch = models.get(modelView);
                for (StaticModel staticModel : batch) {
                    prepareInstance(staticModel);
                }
            }
        }
    }

    @Override
    public void prepareInstance(Model model) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(model);
        shader.transformationMatrix.loadMatrix(transformationMatrix);
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                GL11.glDrawElements(GL11.GL_TRIANGLES, texturedMesh.getMesh().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }
    }

    @Override
    public void unbindTexturedMesh(ModelView modelView) {
        for (ModelPart modelPart : modelView.getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                texturedMesh.getMesh().bind(bindingAttributes);
                shader.material.loadMaterial(texturedMesh.getMaterial());
            }
        }
    }

    @Override
    public void processModel(Model model) {
        ModelView modelView = model.getModelView();
        List<StaticModel> batch = models.get(modelView);
        if (batch != null) {
            batch.add((StaticModel) model);
        } else {
            List<StaticModel> newBatch = new ArrayList<>();
            newBatch.add((StaticModel) model);
            models.put(modelView, newBatch);
        }
    }

    Map<ModelView, List<StaticModel>> getModels() {
        return models;
    }
}
