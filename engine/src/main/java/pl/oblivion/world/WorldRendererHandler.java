package pl.oblivion.world;

import org.joml.Matrix4f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.shaders.RendererHandler;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.utils.PMaths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldRendererHandler extends RendererHandler {

    private Map<TexturedMesh, List<StaticModel>> texturedMeshMap = new HashMap<>();

    private WorldShader shader;

    WorldRendererHandler(WorldShader shader) {
        this.shader = shader;
        bindingAttributes = new int[]{0, 1};
    }

    @Override
    public void delete() {
        for (TexturedMesh texturedMesh : texturedMeshMap.keySet()) {
            for (StaticModel staticModel : texturedMeshMap.get(texturedMesh)) {
                staticModel.delete();
            }
        }
    }

    @Override
    public void prepareModel(TexturedMesh texturedMesh) {
        texturedMesh.getMesh().bind(bindingAttributes);
        shader.material.loadMaterial(texturedMesh.getMaterial());
    }

    @Override
    public void prepareInstance(Model model) {
        Matrix4f transformationMatrix = PMaths.createTransformationMatrix(model);
        shader.transformationMatrix.loadMatrix(transformationMatrix);
    }

    @Override
    public void unbindTexturedMesh(TexturedMesh texturedMesh) {
        texturedMesh.getMesh().unbind(bindingAttributes);
    }

    @Override
    public void processModel(Model model) {
        for (ModelPart modelPart : model.getModelView().getModelParts()) {
            for (TexturedMesh texturedMesh : modelPart.getTexturedMeshes()) {
                List<StaticModel> batch = texturedMeshMap.get(texturedMesh);
                if (batch != null) {
                    batch.add((StaticModel) model);
                } else {
                    List<StaticModel> newBatch = new ArrayList<>();
                    newBatch.add((StaticModel) model);
                    texturedMeshMap.put(texturedMesh, newBatch);
                }
            }
        }
    }

    Map<TexturedMesh, List<StaticModel>> getTexturedMeshMap() {
        return texturedMeshMap;
    }
}
