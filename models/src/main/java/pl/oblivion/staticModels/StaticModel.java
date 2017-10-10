package pl.oblivion.staticModels;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.base.ModelView;

public class StaticModel extends Model {

    public StaticModel(Vector3f position, Vector3f rotation, float scale, ModelView modelView) {
        super(position, rotation, scale, modelView);
    }

    @Override
    public void update() {

    }
}
