package pl.oblivion.utils;

public class CMaths {

    public static float randomNumber(float min, float max) {
        return (float) (min + (Math.random() * ((max - (min)) + 1)));
    }
}
