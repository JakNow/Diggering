package pl.oblivion.core.broadPhase;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Octree implements BroadPhase {

	private final OctreeNode node;

	private Map<Model, OctreeNode> modelOctreeNodeMap = new HashMap<>();

	public Octree(final float worldExtends, int worldDepth) {
		node = new OctreeNode(new Vector3f(0, 0, 0), worldExtends, 0, worldDepth, null, this, 0);
	}

	@Override
	public void insertObject(final Model model) {
		node.insertModel(model);
	}

	@Override
	public void clean() {
		node.clean();
	}

	@Override
	public List<Model> getCollidableModelsList(final Model model) {
		return modelOctreeNodeMap.get(model).getCollidableObjectsList();
	}

	@Override
	public void updateModelsPosition(Model model) {
		modelOctreeNodeMap.get(model).updateModelsPosition(model);
	}

	public Map<Model, OctreeNode> getModelOctreeNodeMap() {
		return modelOctreeNodeMap;
	}
}
