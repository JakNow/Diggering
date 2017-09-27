package pl.oblivion.staticModels;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;

public class StaticModel extends Model {

    public StaticModel(Vector3f position, Vector3f rotation, float scale, TexturedMesh... texturedMeshes) {
        super(position, rotation, scale, texturedMeshes);
    }
}
