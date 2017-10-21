package pl.oblivion.main;

import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelPart;
import pl.oblivion.base.ModelView;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.world.GenerateCollisionShapes;
import pl.oblivion.world.Scene;
import pl.oblivion.world.World;

import java.io.File;

public class Main extends SimpleApp {

    private Player player;
    private StaticRenderer staticRenderer;

    private Main() {

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

        World world = new World();
        Scene testScene = new Scene();
        world.add(testScene);
        testScene.add(player);

        GenerateCollisionShapes.getSpehereCollider(player);

       staticRenderer.getRendererHandler().processWorld(world);
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
