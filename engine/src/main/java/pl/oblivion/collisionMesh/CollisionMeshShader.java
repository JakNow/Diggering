package pl.oblivion.collisionMesh;

import pl.oblivion.core.Config;
import pl.oblivion.game.Camera;
import pl.oblivion.shaders.ShaderProgram;
import pl.oblivion.shaders.UniformMatrix;
import pl.oblivion.shaders.UniformVec4;
import pl.oblivion.utils.Maths;
import pl.oblivion.utils.MyFile;

public class CollisionMeshShader extends ShaderProgram {

    protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
    UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");
    UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");

    UniformVec4 meshColour = new UniformVec4("meshColour");

    public CollisionMeshShader() {
        super(new MyFile(Config.COLLISION_VERT), new MyFile(Config.COLLISION_FRAG), "in_position", "in_textures");
        super.storeAllUniformLocations(projectionMatrix, transformationMatrix, viewMatrix, meshColour);
    }


    public void loadViewMatrix(Camera camera) {
        viewMatrix.loadMatrix(Maths.getViewMatrix(camera));
    }
}
