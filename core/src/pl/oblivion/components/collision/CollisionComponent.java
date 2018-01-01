package pl.oblivion.components.collision;

import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.core.broadPhase.Octree;

import java.util.List;

public abstract class CollisionComponent extends BaseComponent {

	private final Model model;
	private final Octree octree;

	public CollisionComponent(Model model, Octree octree) {
		this.model = model;
		this.octree = octree;
		this.octree.insertObject(this.model);

	}

	public abstract void update(float delta);

	public List<Model> getCollidableModelsList() {
		return octree.getCollidableModelsList(getModel());
	}

	public Model getModel() {
		return model;
	}

	public Octree getOctree() {
		return octree;
	}

}
