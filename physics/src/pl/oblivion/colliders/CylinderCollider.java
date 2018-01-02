package pl.oblivion.colliders;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

public class CylinderCollider extends BasicCollider {

	private static final ColliderType colliderType = ColliderType.CYLINDER;
	private Vector3f center;
	private float height, radius;

	private CylinderCollider(Model model, Vector3f center, float height, float radius) {
		super(model,colliderType);
		this.center = center;
		this.height = height;
		this.radius = radius;
		this.setTranslation(new Vector3f(center).sub(model.getPosition()));
	}

	public static CylinderCollider create(Model model, boolean isInscribed) {
		AABB aabb = AABB.create(model);
		float radius = getRadius(aabb.getDepth(), aabb.getWidth(), isInscribed);

		return new CylinderCollider(model, aabb.getCenter(), aabb.getHeight(), radius);
	}

	private static float getRadius(float comp1, float comp2, boolean isInscribed) {
		if (isInscribed) { return comp1 > comp2 ? comp1 : comp2; } else {
			return (float) (Math.sqrt(Math.pow(comp1, 2) + Math.pow(comp2, 2)));
		}

	}

	@Override
	public boolean intersection(AABB aabb) {
		float dx = Math.max(aabb.getCornerMin().x, Math.min(this.center.x, aabb.getCornerMax().x));
		float dz = Math.max(aabb.getCornerMin().z, Math.min(this.center.z, aabb.getCornerMax().z));

		float distance = (float) Math.sqrt((dx - this.center.x) * (dx - this.center.x) +
				(dz - this.center.z) * (dz - this.center.z));

		return distance < this.radius &&
				(Math.abs(this.center.y - aabb.getCenter().y) < (this.height + aabb.getHeight()));

	}

	//Not perfect
	@Override
	public boolean intersection(SphereCollider sphereCollider) {

		float dy = Math.max(this.center.y - height,
				Math.min(sphereCollider.getCenter().y, this.center.y + height));

		float distance = (float) Math.sqrt((this.center.x - sphereCollider.getCenter().x) *
				(this.center.x - sphereCollider.getCenter().x) +
				(dy - sphereCollider.getCenter().y) * (dy - sphereCollider.getCenter().y) +
				(this.center.z - sphereCollider.getCenter().z) *
						(this.center.z - sphereCollider.getCenter().z));

		return (distance < sphereCollider.getRadius() + this.radius);
	}

	@Override
	public boolean intersection(CylinderCollider cylinderCollider) {
		return Math.abs(this.center.x - cylinderCollider.getCenter().x) <
				(this.radius + cylinderCollider.getRadius()) &&
				(Math.abs(this.center.y - cylinderCollider.getCenter().y) <
						(this.height + cylinderCollider.getHeight()) &&
						(Math.abs(this.center.z - cylinderCollider.getCenter().z) <
								(this.radius + cylinderCollider.getRadius())));
	}

	@Override
	public boolean intersection(MeshCollider meshCollider) {
		return false;
	}

	public float getRadius() {
		return radius;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public void update() {
		this.center = new Vector3f(getModel().getPosition()).add(getTranslation());
	}
}
