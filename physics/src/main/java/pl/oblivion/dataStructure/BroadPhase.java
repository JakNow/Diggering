package pl.oblivion.dataStructure;

import pl.oblivion.base.Model;

public interface BroadPhase {

    void insertObject(final Model model);

    void clean();

    void update();

}
