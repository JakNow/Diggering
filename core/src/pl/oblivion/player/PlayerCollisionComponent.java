package pl.oblivion.player;

import pl.oblivion.base.Model;
import pl.oblivion.components.collision.CollisionComponent;
import pl.oblivion.core.broadPhase.Octree;

public class PlayerCollisionComponent extends CollisionComponent {

	PlayerCollisionComponent(Model model, Octree octree) {
		super(model, octree);
	}

	@Override
	public void update(float delta) {
		getOctree().updateModelsPosition(getModel());
	}
}
