package pl.oblivion.player;

import org.joml.Vector3f;
import pl.oblivion.base.ModelView;
import pl.oblivion.colliders.CylinderCollider;
import pl.oblivion.components.collision.CollisionComponent;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.Window;
import pl.oblivion.core.broadPhase.Octree;
import pl.oblivion.game.MouseInput;
import pl.oblivion.main.Main;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.world.World;

public class Player extends StaticModel {

	private static final float SCALE = 1.0f;
	private static Vector3f POSITION = new Vector3f(0, 3, 0);
	private static Vector3f ROTATION = new Vector3f(0, 0, 0);

	private float runSpeed = Float.parseFloat(Main.properties.getProperty("player.run_speed"));
	private float rotationSpeed = Float.parseFloat(Main.properties.getProperty("player.rotation_speed"));
	private float jumpPower = Float.parseFloat(Main.properties.getProperty("player.jump_power"));

	private MoveComponent moveComponent;
	private RotateComponent rotateComponent;
	private CollisionComponent collisionComponent;
	private World world;

	public Player(ModelView modelView, Octree octree, World world,MouseInput mouseInput) {
		super(POSITION, ROTATION, SCALE, modelView);
		this.world = world;
		moveComponent = new MoveComponent(this,world,runSpeed,jumpPower);
		rotateComponent = new PlayerRotationComponent(this, rotationSpeed,mouseInput);
		collisionComponent = new PlayerCollisionComponent(this, octree, CylinderCollider.create(this,false),null);
	}

	public void update(float delta) {
		moveComponent.update(delta);
		collisionComponent.update(delta);
		rotateComponent.update(delta);
	}

	public float getRunSpeed() {
		return runSpeed;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public float getJumpPower() {
		return jumpPower;
	}

	public World getWorld() {
		return world;
	}
}
