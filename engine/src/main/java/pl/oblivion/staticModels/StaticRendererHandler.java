package pl.oblivion.staticModels;

import org.joml.Matrix4f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelPart;
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

    private Map<TexturedMesh, List<StaticModel>> texturedMeshMap = new HashMap<>();

    private StaticShader shader;

    StaticRendererHandler(StaticShader shader) {
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
    public void processWorld(World world) {
        for (Object scene : world) {
            for (Object model : (Scene) scene) {
                processModel((Model) model);
            }
        }
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

    @Override
    public void prepareModel(TexturedMesh texturedMesh) {
        texturedMesh.getMesh().bind(bindingAttributes);
        shader.material.loadMaterial(texturedMesh.getMaterial());
    }

    @Override
    public void unbindTexturedMesh(TexturedMesh texturedMesh) {
        texturedMesh.getMesh().unbind(bindingAttributes);
    }

    @Override
    public void prepareInstance(Model model) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(model);
        shader.transformationMatrix.loadMatrix(transformationMatrix);

    }

    Map<TexturedMesh, List<StaticModel>> getTexturedMeshMap() {
        return texturedMeshMap;
    }
}
