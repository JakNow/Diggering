package pl.oblivion.main;

import components.CollisionComponent;
import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.collisionMesh.CollisionMeshRenderer;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.world.WorldRenderer;
import shapes.AABB;
import shapes.CylinderCollider;
import shapes.SphereCollider;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;

public class Main extends SimpleApp {

    private Player player;
    private StaticModel aabb;
    private StaticModel cylinder;
    private StaticModel sphere;

    private Main() {


        StaticRenderer staticRenderer = new StaticRenderer(window);
        rendererHandler.addRendererProgram(staticRenderer);

        ModelView test = null;
        try {
            test = StaticMeshLoader.load("sample_body.obj", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        player = new Player(test);

        this.camera = new Camera(player, mouseInput);


        aabb = new StaticModel(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1f, test);
        aabb.addComponent(new CollisionComponent(AABB.create(aabb), null));

        cylinder = new StaticModel(new Vector3f(-5, 0, 0), new Vector3f(0, 0, 0), 1f, test);
        cylinder.addComponent(new CollisionComponent(CylinderCollider.create(cylinder), null));
        sphere = new StaticModel(new Vector3f(-10, 0, 0), new Vector3f(0, 0, 0), 1f, test);
        sphere.addComponent(new CollisionComponent(SphereCollider.create(sphere), null));


        WorldRenderer worldRenderer = new WorldRenderer(window);
        rendererHandler.addRendererProgram(worldRenderer);

        staticRenderer.getRendererHandler().processModel(player);
        staticRenderer.getRendererHandler().processModel(aabb);
        staticRenderer.getRendererHandler().processModel(cylinder);
        staticRenderer.getRendererHandler().processModel(sphere);


        player.addComponent(new CollisionComponent(SphereCollider.create(player), null));


        CollisionMeshRenderer collisionMeshRenderer = new CollisionMeshRenderer(window);
        rendererHandler.addRendererProgram(collisionMeshRenderer);

        collisionMeshRenderer.getRendererHandler().processModel(player);

        collisionMeshRenderer.getRendererHandler().processModel(aabb);
        collisionMeshRenderer.getRendererHandler().processModel(cylinder);
        collisionMeshRenderer.getRendererHandler().processModel(sphere);

    }

    public static void main(String[] args) {
        new Main().run();
    }


    @Override
    public void logicUpdate(float delta, MouseInput mouseInput) {
        player.update(window, delta);
        camera.update();

        player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().update();

        player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().intersection((AABB) aabb.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());
        player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().intersection((SphereCollider) sphere.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());
        player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().intersection((CylinderCollider) cylinder.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());

        if (window.isKeyPressed(GLFW_KEY_F1))
            CollisionMeshRenderer.ENABLE_RENDER = true;
        if (window.isKeyPressed(GLFW_KEY_F2))
            CollisionMeshRenderer.ENABLE_RENDER = false;
    }

}
