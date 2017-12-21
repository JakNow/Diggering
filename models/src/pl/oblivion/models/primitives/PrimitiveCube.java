package pl.oblivion.models.primitives;

import org.apache.log4j.Logger;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.models.ModelsManager;

import java.util.Properties;

public class PrimitiveCube {
	Logger logger = Logger.getLogger(PrimitiveCube.class);

	public PrimitiveCube(Properties properties){
		try {
			ModelsManager.texturedMeshMap.put("primitive_cube",
					StaticMeshLoader.load(properties.getProperty("primitive_cube"), null).getModelParts()[0]
							.getTexturedMeshes()[0]);
			logger.info("Loading Primitive Cube was successful.");
		} catch (Exception e) {
			logger.fatal("Couldn't load Primitive Cube model:\n" + StaticMeshLoader.RESOURCE_PATH);
		}
	}
}
