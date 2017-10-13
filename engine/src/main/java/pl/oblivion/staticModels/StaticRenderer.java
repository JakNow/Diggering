package pl.oblivion.staticModels;


import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Window;
import pl.oblivion.game.Camera;
import pl.oblivion.shaders.RendererProgram;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.utils.Maths;

public class StaticRenderer extends RendererProgram {

    private static StaticShader shader = new StaticShader();
    private static StaticRendererHandler rendererHandler = new StaticRendererHandler();

    public StaticRenderer(Window window) {
        super(shader, rendererHandler, window);

        shader.start();
        shader.projectionMatrix.loadMatrix(projectionMatrix);
        shader.stop();
    }


    @Override
    public void render(Window window, Camera camera) {
        //staticModel.getComponent(MoveComponent.class).move(new Vector3f(0.0f,0.f,-0.002f));
        shader.start();
        shader.projectionMatrix.loadMatrix(window.getProjectionMatrix());
        shader.loadViewMatrix(camera);


        StaticModel staticModel = rendererHandler.getStaticModel();
        for (int i = 0; i < staticModel.getModelView().getModelParts().length; i++) {
            ModelPart modelPart = staticModel.getModelView().getModelParts()[i];
            for (int j = 0; j < modelPart.getTexturedMeshes().length; j++) {

                TexturedMesh texturedMesh = modelPart.getTexturedMeshes()[j];
                texturedMesh.getMesh().bind(0, 1);
                shader.material.loadMaterial(texturedMesh.getMaterial());

                Matrix4f transformationMatrix = Maths.createTransformationMatrix(rendererHandler.getStaticModel());
                shader.transformationMatrix.loadMatrix(transformationMatrix);
                GL11.glDrawElements(GL11.GL_TRIANGLES, texturedMesh.getMesh().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
                texturedMesh.getMesh().unbind(0, 1);
            }
        }

        shader.stop();
    }

    @Override
    public void delete() {
        rendererHandler.getStaticModel().delete();
    }

    @Override
    public StaticRendererHandler getRendererHandler() {
        return rendererHandler;
    }


}
