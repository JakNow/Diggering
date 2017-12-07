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

    public Color(int rgb, int a) {
        this.r = rgb;
        this.g = rgb;
        this.b = rgb;
        this.a = a;
    }

    public Vector4f getFloats() {
        return new Vector4f(this.r / 255f, this.g / 255f, this.b / 255f, this.a / 255f);
    }
}
