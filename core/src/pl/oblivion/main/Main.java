package pl.oblivion.main;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.collisionMesh.CollisionMeshRenderer;
import pl.oblivion.components.CollisionComponent;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.dataStructure.Octree;
import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.player.Player;
import pl.oblivion.shapes.AABB;
import pl.oblivion.shapes.CylinderCollider;
import pl.oblivion.shapes.SphereCollider;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.world.WorldRenderer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;

public class Main extends SimpleApp {

	private Player player;
	private StaticModel aabb;
	private StaticModel cylinder;
	private StaticModel sphere;

	private StaticModel plane;
	private Octree octree;

	private StaticModel[] testModel;

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

		aabb = new StaticModel(new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, 0, 0), 1f, test);
		aabb.addComponent(new CollisionComponent(AABB.create(aabb), null));

		cylinder = new StaticModel(new Vector3f(- 16, - 0.5f, - 16f), new Vector3f(0, 0, 0), 1f, test);
		cylinder.addComponent(new CollisionComponent(CylinderCollider.create(cylinder), null));
		sphere = new StaticModel(new Vector3f(16, - 0.5f, 16f), new Vector3f(0, 0, 0), 1f, test);
		sphere.addComponent(new CollisionComponent(SphereCollider.create(sphere), null));

		WorldRenderer worldRenderer = new WorldRenderer(window);
		rendererHandler.addRendererProgram(worldRenderer);

		staticRenderer.getRendererHandler().processModel(player);
		staticRenderer.getRendererHandler().processModel(aabb);
		staticRenderer.getRendererHandler().processModel(cylinder);
		staticRenderer.getRendererHandler().processModel(sphere);

		player.addComponent(new CollisionComponent(AABB.create(player), null));

		CollisionMeshRenderer collisionMeshRenderer = new CollisionMeshRenderer(window);
		rendererHandler.addRendererProgram(collisionMeshRenderer);

		collisionMeshRenderer.getRendererHandler().processModel(player);

		collisionMeshRenderer.getRendererHandler().processModel(aabb);
		collisionMeshRenderer.getRendererHandler().processModel(cylinder);
		collisionMeshRenderer.getRendererHandler().processModel(sphere);

		try {
			plane = new StaticModel(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 3f,
					StaticMeshLoader.load("test_floor.obj", null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//  staticRenderer.getRendererHandler().processModel(plane);

		//  plane.addComponent(new CollisionComponent(AABB.create(plane), MeshCollider.create(plane)));

		octree = new Octree(128, 3);
		octree.insertObject(player);
		octree.insertObject(aabb);
		octree.insertObject(cylinder);
		octree.insertObject(sphere);
		testModel = new StaticModel[150];
		for (int i = 0; i < 150; i++) {
			testModel[i] = new StaticModel(
					new Vector3f((float) (Math.random() * 100 - 50), (float) (Math.random() * 100 - 50),
							(float) (Math.random() * 100 - 50)), new Vector3f(0, 0, 0), 1f, test);
			testModel[i].addComponent(new CollisionComponent(AABB.create(testModel[i]), null));
			octree.insertObject(testModel[i]);

			collisionMeshRenderer.getRendererHandler().processModel(testModel[i]);
			staticRenderer.getRendererHandler().processModel(testModel[i]);
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

	@Override
	public void logicUpdate(float delta, MouseInput mouseInput) {
		player.update(window, delta);
		camera.update();
		octree.update();
		player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().update();

		player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape()
				.intersection((AABB) aabb.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());
		player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().intersection(
				(SphereCollider) sphere.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());
		player.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape().intersection(
				(CylinderCollider) cylinder.getComponent(CollisionComponent.class).getBroadPhaseCollisionShape());

		if (window.isKeyPressed(GLFW_KEY_F1)) { CollisionMeshRenderer.ENABLE_RENDER = true; }
		if (window.isKeyPressed(GLFW_KEY_F2)) { CollisionMeshRenderer.ENABLE_RENDER = false; }
	}
}
