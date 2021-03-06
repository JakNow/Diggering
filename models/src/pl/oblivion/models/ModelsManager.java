package pl.oblivion.models;

import org.apache.log4j.Logger;
import pl.oblivion.base.TexturedMesh;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModelsManager {

	private final static Logger logger = Logger.getLogger(ModelsManager.class);
	public static final Properties properties = loadProperties();
	private static final Map<String, TexturedMesh> texturedMeshMap = new HashMap<>();

	public ModelsManager() {
	}

	private static Properties loadProperties() {
		try {
			InputStream stream = Files.newInputStream(Paths.get("models/resources/models.properties"));
			Properties properties = new Properties();
			properties.load(stream);
			logger.info("Loaded models.properties file.");
			return properties;
		} catch (IOException e) {
			logger.fatal("Couldn't load models.properties file!", e);
		}

		return properties;
	}

}
