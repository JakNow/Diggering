package pl.oblivion.core;

/**
 * Rendering engine config
 */
public class Config {

	public static final String TITLE = "Diggering";
	public static final int TARGET_FPS = 75;
	public static final int TARGET_UPS = 30;
	public static final String STATIC_VERT = "shaders/static.vert";
	public static final String STATIC_FRAG = "shaders/static.frag";
	public static final String WORLD_VERT = "shaders/world.vert";
	public static final String WORLD_FRAG = "shaders/world.frag";
	public static final String COLLISION_VERT = "shaders/collision.vert";
	public static final String COLLISION_FRAG = "shaders/collision.frag";
	public static final float FOV = 70;
	public static final float NEAR = 0.1f;
	public static final float FAR = 1000f;
	public static int WIDTH = 1000;
	public static int HEIGHT = WIDTH * 9 / 16;
	public static float RED = 0.0f;
	public static float GREEN = 0.0f;
	public static float BLUE = 0.0f;
	public static float ALPHA = 1.0f;
}
