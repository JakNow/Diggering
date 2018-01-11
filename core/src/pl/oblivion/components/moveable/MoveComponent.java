package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;
import pl.oblivion.main.Main;
import pl.oblivion.world.World;

public class MoveComponent extends BaseComponent {

	private static final ComponentType componentType = ComponentType.MOVE;


	private float velocity;
	private float velocityGoal;

	private float sideVelocity;
	private float sideVelocityGoal;

	private Vector3f direction;
	private Vector3f gravity;

	private float runSpeed;
	private float jumpPower;

	private boolean moveSide = false;
	public MoveComponent(Model model, World world, float runSpeed, float jumpPower) {
		super(model,componentType);
		this.direction = new Vector3f(0,0,0);
		this.runSpeed = runSpeed;
		this.jumpPower = jumpPower;
		this.gravity = world.getGravity();
	}

	/**
	 *Can be used later as a param of the floor to create slippery surrface (10 - ice, 100 - normal (?) floor)
	 */
	private float floorEffect = 80;
	public void update(float delta) {
		float dx = (float)(Math.sin(Math.toRadians(getModel().getRotation().y)));
		float dz = (float)(Math.cos(Math.toRadians(getModel().getRotation().y)));


		this.direction.x = dx;
		this.direction.z = dz;

		this.velocity = smoothMovement(velocityGoal,velocity,delta*floorEffect);
		this.sideVelocity = smoothMovement(sideVelocityGoal,sideVelocity,delta*floorEffect);

		updateModelsVectors(getModel().getPosition(),delta);
		if(getModel().getPosition().y<getModel().getHeight()){
			getModel().getPosition().y = getModel().getHeight();
		}
	}

	private float smoothMovement(float goalSpeed, float currentSpeed, float delta){
		float speedDifference = goalSpeed - currentSpeed;

		if(speedDifference > delta){
			return currentSpeed+delta;
		}
		if(speedDifference < -delta){
			return currentSpeed-delta;
		}
		return goalSpeed;
	}



	private void updateModelsVectors(Vector3f position, float delta){
		position.x += this.direction.x*this.velocity*delta + sideX()*this.sideVelocity*delta;
		position.y += this.direction.y*this.velocity*delta;
		position.z += this.direction.z*this.velocity*delta+ sideZ()*this.sideVelocity*delta;

	}

	private float sideX(){
			return (float)(Math.sin(Math.toRadians(getModel().getRotation().y+90)));
	}

	private float sideZ(){
		return (float)(Math.cos(Math.toRadians(getModel().getRotation().y+90)));
	}
	public float getRunSpeed() {
		return runSpeed;
	}

	public void setVelocityGoal(float velocityGoal) {
		this.velocityGoal = velocityGoal;
	}

	public void setSideVelocityGoal(float sideVelocityGoal) {
		this.sideVelocityGoal = sideVelocityGoal;
	}
}

