package pl.oblivion.core;

import org.apache.log4j.Logger;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.game.Renderer;
import pl.oblivion.models.ModelsManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class SimpleApp {

	protected static final Logger logger = Logger.getLogger(SimpleApp.class);
	protected final Window window;
	protected final Renderer rendererHandler;
	protected final MouseInput mouseInput;
	public static Properties properties = loadProperties();
	private final Timer timer;
	protected Camera camera;

	private final int ups = Integer.parseInt(properties.getProperty("window.ups"));
	private final int fps = Integer.parseInt(properties.getProperty("window.fps"));

	protected SimpleApp() {

		this.window = new Window(properties);
		this.mouseInput = new MouseInput(window);
		this.timer = new Timer();
		this.rendererHandler = new Renderer(window);
		new ModelsManager();
	}

	public void run() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / ups;

		while (! window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			while (accumulator >= interval) {
				mouseInput.input();
				logicUpdate(interval, mouseInput);
				accumulator -= interval;
			}

			renderUpdate();
			if (! window.isvSync()) {
				sync();
			}
			window.updateAfter();
		}

		window.destroy();
		cleanUp();
	}

	public abstract void logicUpdate(float delta, MouseInput mouseInput);

	private void renderUpdate() {
		rendererHandler.prepare();
		rendererHandler.render(window, camera);
	}

	private void sync() {
		float loopSlot = 1f / fps;
		double endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				logger.error("Problem with syncing app.",ie);
			}
		}
	}

	private void cleanUp() {
		rendererHandler.cleanUp();
	}


	private static Properties loadProperties(){
		try{
			InputStream stream = Files.newInputStream(Paths.get("engine\\resources\\engine.properties"));
			Properties properties = new Properties();
			properties.load(stream);
			logger.info("Loaded engine.properties file.");
			return properties;
		} catch (IOException e){
			logger.fatal("Couldn't load engine.properties for core!",e);
		}
		return null;
	}
}
