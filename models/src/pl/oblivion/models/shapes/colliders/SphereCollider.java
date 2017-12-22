package pl.oblivion.models.shapes.colliders;

import org.apache.log4j.Logger;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.TexturedMesh;

import java.util.Map;
import java.util.Properties;

public class SphereCollider {

	Logger logger = Logger.getLogger(SphereCollider.class);

	public SphereCollider(Properties properties, Map<String, TexturedMesh> texturedMeshMap) {
		try {
			texturedMeshMap.put("shapes.colliders.SphereCollider",
					StaticMeshLoader.load(properties.getProperty("shapes.colliders.SphereCollider"), null)
							.getModelParts()[0]
							.getTexturedMeshes()[0]);
			logger.info("Loading SphereCollider was successful.");
		} catch (Exception e) {
			logger.fatal("Couldn't load SphereCollider model: " + StaticMeshLoader.RESOURCE_PATH);
		}
	}
}
