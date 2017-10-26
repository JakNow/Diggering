package pl.oblivion.core;

import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.MeshCollider;
import pl.oblivion.shapes.SphereCollider;

public class ColliderGenerator {

    public final AABB aabb;
    public final CylinderCollider cylinderCollider;
    public final MeshCollider meshCollider;
    public final SphereCollider sphereCollider;

    public ColliderGenerator() {
        aabb = new AABB();
        cylinderCollider = new CylinderCollider();
        meshCollider = new MeshCollider();
        sphereCollider = new SphereCollider();
    }
}
