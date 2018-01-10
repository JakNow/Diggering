package pl.oblivion.shaders.uniforms;

import org.apache.log4j.Logger;
import pl.oblivion.lighting.Light;

/**
 * @author jakubnowakowski
 * Created at 08.01.2018
 */
public class UniformLight extends Uniform {

    private UniformVec3 position;
    private UniformVec4 colour;

    public UniformLight(String name) {
        super(name);
        this.position = new UniformVec3(name+".position");
        this.colour = new UniformVec4(name+".colour");
    }

    public void loadLight(Light light){
        this.position.loadVec3(light.getPosition());
        this.colour.loadVec4(light.getColour());
    }

    @Override
    public void logMissingUniform() {
        logger = Logger.getLogger(UniformLight.class);
        logger.error("No uniform variable called " + name + " found!");
    }

    public Uniform[] getAllUniforms(){
        return new Uniform[]{position,colour};
    }
}
