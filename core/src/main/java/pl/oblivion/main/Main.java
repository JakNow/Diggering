package pl.oblivion.main;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.world.Scene;
import pl.oblivion.world.World;

import java.io.File;

public class Main extends SimpleApp {

    StaticModel staticModel;
    private Player player;

    private Main() {

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
        this.camera = new Camera(player, mouseInput);

        staticModel = new StaticModel(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1f, test);
        World world = new World();
        Scene testScene = new Scene();
        world.add(testScene);
        testScene.add(staticModel);
        testScene.add(player);
        staticModel.addComponent(new MoveComponent(staticModel, 0.05f));

        staticRenderer.getRendererHandler().processWorld(world);
    }

    public static void main(String[] args) {
        new Main().run();
    }


    @Override
    public void logicUpdate(float delta, MouseInput mouseInput) {
        player.update(window, delta);
        camera.update();
    }
}
