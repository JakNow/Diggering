package pl.oblivion.collision;

import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.MeshCollider;
import pl.oblivion.shapes.SphereCollider;

public interface CollisionIntersection {

    boolean intersectAABB(AABB aabb);

    boolean intersectSphere(SphereCollider sphereCollider);

    boolean intersectMesh(MeshCollider meshCollider);

    boolean intersectCylinder(CylinderCollider cylinderCollider);
}
