package pl.oblivion.models.shapes.colliders;

import org.apache.log4j.Logger;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.TexturedMesh;

import java.util.Map;
import java.util.Properties;

public class AABBCollider {

	Logger logger = Logger.getLogger(AABBCollider.class);

	public AABBCollider(Properties properties, Map<String, TexturedMesh> texturedMeshMap) {
		try {
			TexturedMesh texturedMesh =
					StaticMeshLoader.load(properties.getProperty("shapes.colliders.AABBCollider"), null)
							.getModelParts()[0].getTexturedMeshes()[0];
			texturedMeshMap.put("shapes.colliders.AABBCollider", texturedMesh);
			logger.info("Loading AABBCollider was successful.");
		} catch (Exception e) {
			logger.fatal("Couldn't load AABBCollider model:" + StaticMeshLoader.RESOURCE_PATH);
		}
	}
}
