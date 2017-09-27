package pl.oblivion.staticModels;


import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.oblivion.base.Mesh;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.loaders.StaticModelLoader;
import pl.oblivion.materials.Material;
import pl.oblivion.materials.Texture;
import pl.oblivion.objParser.ObjData;
import pl.oblivion.objParser.ObjParser;
import pl.oblivion.shaders.RendererProgram;
import pl.oblivion.utils.Maths;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class StaticRenderer extends RendererProgram {

    private StaticShader shader;

    private StaticModel staticModel;

    public StaticRenderer(Texture texture) {
        this.shader = new StaticShader();


        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textures = {
                0,0,
                0,1,
                1,1,
                1,0
        };
        ObjData objData =  ObjParser.loadObjFile("test_cube.obj");

       // mesh = StaticModelLoader.createMesh(new StaticMeshData(objData.getModel().getParsedObjectDatas().get(0)));
       Mesh mesh = StaticModelLoader.createMesh(new StaticMeshData(vertices, indices,textures));
       Material mat = new Material();
       mat.setDiffuseTexture(texture);
       staticModel = new StaticModel(new Vector3f(-1,0,0),new Vector3f(0,0,0),1,new TexturedMesh(mesh,mat));
    }


    @Override
    public void render() {
        shader.start();
        staticModel.getTexturedMesh().getMesh().bind(0,1);

        glActiveTexture(GL_TEXTURE0);
        staticModel.getTexturedMesh().getMaterial().getDiffuseTexture().bind();

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(staticModel);
        shader.transformationMatrix.loadMatrix(transformationMatrix);
        GL11.glDrawElements(GL11.GL_TRIANGLES, staticModel.getTexturedMesh().getMesh().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

        staticModel.getTexturedMesh().getMesh().unbind(0,1);
        shader.stop();
    }

    @Override
    public void cleanUp() {
        shader.cleanUp();
    }
}
