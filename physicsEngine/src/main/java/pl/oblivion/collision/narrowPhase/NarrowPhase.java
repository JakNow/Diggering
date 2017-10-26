package pl.oblivion.collision.narrowPhase;

import pl.oblivion.base.Model;

import java.util.HashMap;
import java.util.Map;

public class NarrowPhase {

    public Map<Model, Model> narrowPhaseCollisionMap;

    public NarrowPhase() {
        narrowPhaseCollisionMap = new HashMap<>();
    }

    public void update() {
    }
}
