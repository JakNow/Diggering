package pl.oblivion.dataStructure;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

public class Octree implements BroadPhase {

	private final OctreeNode node;

	public Octree(final float worldExtends, int worldDepth) {
		node = new OctreeNode(new Vector3f(0, 0, 0), worldExtends, worldDepth, null);
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
	public void update() {
		node.dynamicUpdate();
	}
}
