package pl.oblivion.utils;

import org.joml.Vector3f;

public class Face {

	private final Vector3f point1;
	private final Vector3f point2;
	private final Vector3f point3;

	private final Vector3f normal;
	private final Plane plane;

	public Face(Vector3f point1, Vector3f point2, Vector3f point3, Vector3f normal) {
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		this.normal = normal;
		this.plane = createPlane();
	}

	private Plane createPlane() {
		float d = - (normal.x * point1.x + normal.y * point1.y + normal.z * point1.z);

		return new Plane(normal, d);
	}

	public Vector3f getPoint1() {
		return point1;
	}

	public Vector3f getPoint2() {
		return point2;
	}

	public Vector3f getPoint3() {
		return point3;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public Plane getPlane() {
		return plane;
	}

	@Override
	public String toString() {
		return "Face{" +
				"point1=" + point1 +
				", point2=" + point2 +
				", point3=" + point3 +
				", normal=" + normal +
				", plane=" + plane.toString() +
				'}';
	}
}
