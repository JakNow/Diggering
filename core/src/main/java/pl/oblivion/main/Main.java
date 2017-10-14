package pl.oblivion.main;

import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.staticModels.StaticRenderer;

import java.io.File;

public class Main extends SimpleApp {

    Player player;
    StaticRenderer staticRenderer;

    public Main() {

        staticRenderer = new StaticRenderer(window);
        rendererHandler.addRendererProgram(staticRenderer);


        String fileName = "core/resources/assets/models/sample_body.obj";
        File file = new File(fileName);
        ModelView test = null;
        try {
            test = StaticMeshLoader.load(file.getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        player = new Player(test);
        this.camera = new Camera(player, mouseInput);
        staticRenderer.getRendererHandler().processModel(player);

    }

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void input(MouseInput mouseInput) {

    }

    @Override
    public void logicUpdate(float delta, MouseInput mouseInput) {
        camera.update();
        player.update(window, delta);
    }
}
