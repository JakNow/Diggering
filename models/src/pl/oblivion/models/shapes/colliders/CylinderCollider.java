package pl.oblivion.models.shapes.colliders;

import org.apache.log4j.Logger;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.TexturedMesh;

import java.util.Map;
import java.util.Properties;

public class CylinderCollider {

	Logger logger = Logger.getLogger(CylinderCollider.class);

	public CylinderCollider(Properties properties, Map<String, TexturedMesh> texturedMeshMap) {
		try {
			texturedMeshMap.put("shapes.colliders.CylinderCollider",
					StaticMeshLoader.load(properties.getProperty("shapes.colliders.CylinderCollider"), null)
							.getModelParts()[0]
							.getTexturedMeshes()[0]);
			logger.info("Loading CylinderCollider was successful.");
		} catch (Exception e) {
			logger.fatal("Couldn't load CylinderCollider model: " + StaticMeshLoader.RESOURCE_PATH);
		}
	}
}
