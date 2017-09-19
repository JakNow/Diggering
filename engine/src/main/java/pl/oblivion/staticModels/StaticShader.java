package pl.oblivion.staticModels;


import pl.oblivion.core.Config;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.utils.MyFile;

public class StaticShader extends ShaderProgram {

    public StaticShader() {
        super(new MyFile(Config.STATIC_FRAG), new MyFile(Config.STATIC_FRAG), "in_position");
        storeAllUniformLocations();
    }
}
