package pl.oblivion.core;


import pl.oblivion.collision.broadPhase.BroadPhase;
import pl.oblivion.collision.narrowPhase.NarrowPhase;
import pl.oblivion.shapes.TypeOfShape;

public class PhysicsState {

    public static TypeOfShape DEFAULT_NARROW_COLLISION_SHAPE = TypeOfShape.AABB;

    private BroadPhase broadPhase;


    public PhysicsState() {
        broadPhase = new BroadPhase(new NarrowPhase());
    }

    void update() {
        broadPhase.update();
    }
}
