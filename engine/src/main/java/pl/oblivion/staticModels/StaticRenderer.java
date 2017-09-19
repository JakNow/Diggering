package pl.oblivion.staticModels;


import org.lwjgl.opengl.GL11;
import pl.oblivion.base.Mesh;
import pl.oblivion.loaders.StaticModelLoader;
import pl.oblivion.shaders.RendererProgram;

public class StaticRenderer extends RendererProgram {

    private static final StaticShader STATIC_SHADER = new StaticShader();

    private Mesh mesh;

    public StaticRenderer() {
        super(STATIC_SHADER);


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

        mesh = StaticModelLoader.createMesh(new StaticMeshData(vertices, indices));
    }


    @Override
    public void render() {
        mesh.bind(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
        mesh.unbind(0);

    }
}
