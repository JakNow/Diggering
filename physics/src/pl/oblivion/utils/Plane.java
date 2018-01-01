package pl.oblivion.utils;

import org.joml.Vector3f;

public class Plane {

	private float a;
	private float b;
	private float c;
	private float d;

	public Plane(float a, float b, float c, float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Plane(Vector3f abc, float d) {
		this.a = abc.x;
		this.b = abc.y;
		this.c = abc.z;
		this.d = d;
	}

	@Override
	public String toString() {
		return "Plane[" + a +
				", " + b +
				", " + c +
				", " + d +
				']';
	}
}
