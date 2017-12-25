package pl.oblivion.models.shapes.colliders;

import org.apache.log4j.Logger;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.TexturedMesh;

import java.util.Map;
import java.util.Properties;

public class OctreeNodeShape {

	Logger logger = Logger.getLogger(OctreeNodeShape.class);

	public OctreeNodeShape(Properties properties, Map<String, TexturedMesh> texturedMeshMap) {
		try {
			texturedMeshMap.put("shapes.colliders.OctreeNode",
					StaticMeshLoader.load(properties.getProperty("shapes.colliders.OctreeNode"), null)
							.getModelParts()[0].getTexturedMeshes()[0]);
			logger.info("Loading OctreeNodeShape was successful.");
		} catch (Exception e) {
			logger.fatal("Couldn't load OctreeNodeShape model: " + StaticMeshLoader.RESOURCE_PATH);
		}
	}
}
