package pl.oblivion.main;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.collision.broadPhase.BroadPhase;
import pl.oblivion.components.collidable.CollidableComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.core.ColliderGenerator;
import pl.oblivion.core.PhysicsState;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.TypeOfShape;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.world.Scene;
import pl.oblivion.world.World;
import pl.oblivion.world.WorldRenderer;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Random;

public class Main extends SimpleApp {

    StaticModel staticModel;
    private Player player;
    private StaticModel testModel;

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


        try {
            testModel = new StaticModel(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 3f, "core/resources/assets/models/test_floor.obj", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        World world1 = new World();
        Scene worldScene = new Scene();
        world1.add(worldScene);
        worldScene.add(testModel);

        Random random = new Random();
        int count = 0;
        for(int i = 0; i < 500; i++){
            StaticModel staticModel = new StaticModel(new Vector3f((float) (Math.random()*5),(float) (Math.random()*5),(float) (Math.random()*5)), new Vector3f (0,0,0),1f,test);
             boolean isMoveable = random.nextBoolean();
            CollidableComponent collidableComponent = new CollidableComponent(staticModel, TypeOfShape.AABB, isMoveable);
            if(isMoveable)
                count++;

            staticModel.addComponent(collidableComponent);
            worldScene.add(staticModel);

        }
        System.out.println("No of movable: "+count);

        CollidableComponent testModdelCollidableComponent = new CollidableComponent(testModel, TypeOfShape.AABB,false);
        testModel.addComponent(testModdelCollidableComponent);

        WorldRenderer worldRenderer = new WorldRenderer(window);
        rendererHandler.addRendererProgram(worldRenderer);

        worldRenderer.getRendererHandler().processWorld(world1);
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
