package pl.oblivion.utils;

import org.joml.Vector4f;

public class Color {

	private int r, g, b, a;

	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(int rgba) {
		this.r = rgba;
		this.g = rgba;
		this.b = rgba;
		this.a = rgba;
	}

	public Color(Vector4f vector4f) {
		this.r = clamp((int) (vector4f.x * 255), 0, 255);
		this.g = clamp((int) (vector4f.y * 255), 0, 255);
		this.b = clamp((int) (vector4f.z * 255), 0, 255);
		this.a = clamp((int) (vector4f.w * 255), 0, 255);
	}

	private int clamp(int value, int min, int max) {
		if (value > max) {
			return max;
		} else if (value < min) {
			return min;
		} else {
			return value;
		}
	}

	public Color(int rgb, int a) {
		this.r = rgb;
		this.g = rgb;
		this.b = rgb;
		this.a = a;
	}

	public Vector4f getFloats() {
		return new Vector4f(this.r / 255f, this.g / 255f, this.b / 255f, this.a / 255f);
	}

	public void setColour(Vector4f vector4f) {
		this.r = clamp((int) (vector4f.x * 255), 0, 255);
		this.g = clamp((int) (vector4f.y * 255), 0, 255);
		this.b = clamp((int) (vector4f.z * 255), 0, 255);
		this.a = clamp((int) (vector4f.w * 255), 0, 255);
	}

	@Override
	public String toString() {
		return "Color[" + r +
				", " + g +
				", " + b +
				", " + a +
				']';
	}
}
