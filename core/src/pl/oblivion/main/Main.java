package pl.oblivion.main;

import org.apache.log4j.Logger;
import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.colliders.AABB;
import pl.oblivion.components.ComponentType;
import pl.oblivion.components.collision.CollisionComponent;
import pl.oblivion.components.collision.StaticCollisionComponent;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.utils.CMaths;
import pl.oblivion.world.WorldRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Main extends SimpleApp {

	private static Logger logger = Logger.getLogger(Main.class);
	public static Properties properties = loadProperties();
	private SimpleInputs inputs;
	private Player player;
	private StaticModel plane;

	private Main() {
		StaticRenderer staticRenderer = new StaticRenderer(window);
		rendererHandler.addRendererProgram(staticRenderer);

		ModelView test = null;
		try {
			test = StaticMeshLoader.load("sample_body.obj", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		player = new Player(test, octree);
		inputs = new SimpleInputs(window, player);
		this.camera = new Camera(player, mouseInput);

		WorldRenderer worldRenderer = new WorldRenderer(window);
		rendererHandler.addRendererProgram(worldRenderer);

		staticRenderer.getRendererHandler().processModel(player);

		try {
			plane = new StaticModel(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 3f,
					StaticMeshLoader.load("test_floor.obj", null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		staticRenderer.getRendererHandler().processModel(plane);

		int numbOfTestModels = 1000;
		int size = 64;
		StaticModel[] testPlanes = new StaticModel[numbOfTestModels];
		for (int i = 0; i < numbOfTestModels; i++) {
			testPlanes[i] = new StaticModel(
					new Vector3f(CMaths.randomNumber(- size, size), CMaths.randomNumber(- size, size),
							CMaths.randomNumber(- size, size)),
					new Vector3f(0, 0, 0), 1f, test);
			testPlanes[i].addComponent(new StaticCollisionComponent(testPlanes[i],octree,AABB.create(testPlanes[i])));
			staticRenderer.getRendererHandler().processModel(testPlanes[i]);
		}
		
	}

	public static void main(String[] args) {
		new Main().run();
	}

	private static Properties loadProperties() {
		try {
			InputStream stream = Files.newInputStream(Paths.get("core/resources/core.properties"));
			Properties properties = new Properties();
			properties.load(stream);
			logger.info("Loaded core.properties file.");
			return properties;
		} catch (IOException e) {
			logger.fatal("Couldn't load core.properties for core!", e);
		}
		return null;
	}

	@Override
	public void logicUpdate(float delta, MouseInput mouseInput) {
		inputs.checkPlayerInputs();
		player.update(delta);
		camera.update();
		//octree.logCollision();

		if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
			glfwSetWindowShouldClose(window.getWindowHandle(), true);
		}
	}

}
