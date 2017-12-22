package pl.oblivion.models;

import org.apache.log4j.Logger;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.models.shapes.colliders.AABBCollider;
import pl.oblivion.models.shapes.colliders.CollidersShapesInterface;
import pl.oblivion.models.shapes.colliders.CylinderCollider;
import pl.oblivion.models.shapes.colliders.SphereCollider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModelsManager implements CollidersShapesInterface {

	private final static Logger logger = Logger.getLogger(ModelsManager.class);
	private static final Map<String, TexturedMesh> texturedMeshMap = new HashMap<>();
	public static final Properties properties = loadProperties();

	public ModelsManager() {
		this.loadAABBCollider();
		this.loadCylinderCollider();
		this.loadSphereCollider();
	}

	private static Properties loadProperties() {
		Properties properties = new Properties();
		InputStream stream = null;
		try {
			stream = Files.newInputStream(Paths.get("models\\resources\\models.properties"));
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
	public void loadAABBCollider() {
		new AABBCollider(properties, texturedMeshMap);
	}

	@Override
	public void loadSphereCollider() {
		new SphereCollider(properties, texturedMeshMap);
	}

	@Override
	public void loadCylinderCollider() {
		new CylinderCollider(properties, texturedMeshMap);
	}

	public static TexturedMesh getAABBCollider() {
		return new TexturedMesh(texturedMeshMap.get("shapes.colliders.AABBCollider"));
	}

	public static TexturedMesh getCylinderCollider() {
		return new TexturedMesh(texturedMeshMap.get("shapes.colliders.CylinderCollider"));
	}

	public static TexturedMesh getSphereCollider() {
		return new TexturedMesh(texturedMeshMap.get("shapes.colliders.SphereCollider"));
	}
}
