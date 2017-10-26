package pl.oblivion.core;


import pl.oblivion.collision.broadPhase.BroadPhase;
import pl.oblivion.collision.narrowPhase.NarrowPhase;

public class PhysicsState {

    public static ColliderGenerator COLLIDER_GENERATOR = new ColliderGenerator();
    private BroadPhase broadPhase;

    public PhysicsState() {
        broadPhase = new BroadPhase(new NarrowPhase());
    }

    void update() {
        broadPhase.update();
    }
}
