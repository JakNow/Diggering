package pl.oblivion.main;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Config;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;

import java.io.File;

public class Main extends SimpleApp {

Player player;
    public Main() {

        StaticRenderer staticRenderer = new StaticRenderer(window);
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
        staticRenderer.getRendererHandler().setStaticModel(player);
        this.camera = new Camera(player,mouseInput);

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
    player.update(window,delta);
    }
}
