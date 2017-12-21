package pl.oblivion.models;

import org.apache.log4j.Logger;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.models.primitives.PrimitivesInterface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModelsManager implements PrimitivesInterface {

	private final static Logger logger = Logger.getLogger(ModelsManager.class);
	private static final Map<String, TexturedMesh> texturedMeshMap = new HashMap<>();
	private final Properties properties;

	public ModelsManager() {
		this.properties = loadProperties();
		this.loadPrimitiveCube();
	}

	private Properties loadProperties() {
		Properties properties = new Properties();
		InputStream stream = null;
		try {
			stream = Files.newInputStream(Paths.get("core\\resources\\models.properties"));
		} catch (IOException e) {
			logger.fatal("Couldn't load models.properties file!", e);
		}
		try {
			properties.load(stream);
		} catch (IOException e) {
			logger.fatal("Couldn't load properties from file!");
		}

		return properties;
	}

	@Override
	public void loadPrimitiveCube() {
		try {
			texturedMeshMap.put("primitive_cube",
					StaticMeshLoader.load(properties.getProperty("primitive_cube"), null).getModelParts()[0]
							.getTexturedMeshes()[0]);
			logger.info("Loading Primitive Cube was successful.");
		} catch (Exception e) {
			logger.fatal("Couldn't load Primitive Cube model:\n" + StaticMeshLoader.RESOURCE_PATH);
		}
	}

	public static void printAvaiableModels() {
		for (String key : texturedMeshMap.keySet()) {
			System.out.println(key);
		}
	}

	public static TexturedMesh getAABB() {
		return texturedMeshMap.get("primitive_cube");
	}
}
