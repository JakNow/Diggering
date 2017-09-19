package pl.oblivion;

import pl.oblivion.core.SimpleApp;
import pl.oblivion.staticModels.StaticRenderer;

public class Main extends SimpleApp {


    public Main() {
        new StaticRenderer();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void input() {

    }

    @Override
    public void logicUpdate(float delta) {

    }
}
