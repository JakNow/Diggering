package pl.oblivion.core.broadPhase;

import pl.oblivion.base.Model;

import java.util.List;

public interface BroadPhase {

	void insertObject(final Model model);

	void clean();

	List<Model> getCollidableModelsList(final Model model);

	void updateModelsPosition(final Model model);
}
