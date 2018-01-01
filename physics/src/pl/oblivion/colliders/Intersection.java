package pl.oblivion.colliders;

/*
Interface used for BasicCollider to set up intersection between different CollisionShapes
Adding new BasicCollider requires adding new boolean to check intersection within this shape
 */
public interface Intersection {

	boolean intersection(AABB aabb);

	boolean intersection(SphereCollider sphereCollider);

	boolean intersection(CylinderCollider cylinderCollider);

	boolean intersection(MeshCollider meshCollider);

}
