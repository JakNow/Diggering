package pl.oblivion.staticModels;


import org.joml.Matrix4f;
import pl.oblivion.core.Config;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.shaders.UniformMatrix;
import pl.oblivion.utils.MyFile;

public class StaticShader extends ShaderProgram {

    protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");

    public StaticShader() {
        super(new MyFile(Config.STATIC_VERT), new MyFile(Config.STATIC_FRAG), "in_position","in_textures");
        super.storeAllUniformLocations(transformationMatrix);
    }

}
