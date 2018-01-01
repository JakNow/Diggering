package pl.oblivion.utils;

import org.joml.Vector3f;

public class Ray {

	private Vector3f point;
	private Vector3f diraction;

	public Ray(Vector3f point, Vector3f direction) {
		this.point = point;
		this.diraction = direction;
	}

	public Vector3f getPoint() {
		return point;
	}

	public void setPoint(Vector3f point) {
		this.point = point;
	}

	public Vector3f getDiraction() {
		return diraction;
	}

	public void setDiraction(Vector3f diraction) {
		this.diraction = diraction;
	}
}
