package pl.oblivion.colliders;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

public class SphereCollider extends BasicCollider {

	private static final ColliderType colliderType = ColliderType.SPHERE;

	private Vector3f center;
	private float radius;

	private SphereCollider(Model model, Vector3f center, float radius) {
		super(model,colliderType);
		this.center = center;
		this.radius = radius;
		this.setTranslation(new Vector3f(center).sub(model.getPosition()));
	}

	public static SphereCollider create(Model model) {
		AABB aabb = AABB.create(model);
		return new SphereCollider(model, aabb.getCenter(), aabb.getCenter().distance(aabb.getCornerMin()));
	}

	@Override
	public boolean intersection(AABB aabb) {
		float dx = Math.max(aabb.getCornerMin().x, Math.min(this.center.x, aabb.getCornerMax().x));
		float dy = Math.max(aabb.getCornerMin().y, Math.min(this.center.y, aabb.getCornerMax().y));
		float dz = Math.max(aabb.getCornerMin().z, Math.min(this.center.z, aabb.getCornerMax().z));

		float distance = (float) Math.sqrt((dx - this.center.x) * (dx - this.center.x) +
				(dy - this.center.y) * (dy - this.center.y) +
				(dz - this.center.z) * (dz - this.center.z));

		return distance < this.radius;
	}

	@Override
	public boolean intersection(SphereCollider sphereCollider) {
		return this.center.distance(sphereCollider.getCenter()) < (this.radius + sphereCollider.radius);
	}

	@Override
	public void update() {
		this.center = new Vector3f(getModel().getPosition()).add(getTranslation());
	}

	public Vector3f getCenter() {
		return center;
	}

	//Not perfect
	@Override
	public boolean intersection(CylinderCollider cylinderCollider) {

		float dy = Math.max(cylinderCollider.getCenter().y - cylinderCollider.getHeight(),
				Math.min(this.center.y, cylinderCollider.getCenter().y + cylinderCollider.getHeight()));

		float distance = (float) Math.sqrt((this.center.x - cylinderCollider.getCenter().x) *
				(this.center.x - cylinderCollider.getCenter().x) +
				(dy - cylinderCollider.getCenter().y) * (dy - cylinderCollider.getCenter().y) +
				(this.center.z - cylinderCollider.getCenter().z) *
						(this.center.z - cylinderCollider.getCenter().z));

		return distance < cylinderCollider.getRadius() + this.radius;
	}

	@Override
	public boolean intersection(MeshCollider meshCollider) {
		return false;
	}

	public float getRadius() {
		return radius;
	}
}
