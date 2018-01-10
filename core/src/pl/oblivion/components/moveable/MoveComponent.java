package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;

public class MoveComponent extends BaseComponent {

    private static final ComponentType componentType = ComponentType.MOVE;

    private Vector3f velocity = new Vector3f();
    private Vector3f gravity = new Vector3f();
    private Vector3f velocityGoal = new Vector3f();
    /**
     * Can be used later as a param of the floor to create slippery surrface (10 - ice, 100 - normal (?) floor)
     */
    private float floorEffect = 80;

    public MoveComponent(Model model, float gravity, float runSpeed, float jumpPower) {
        super(model, componentType);

    }

    public void update(float delta) {
        this.velocity.x = smoothMovement(velocityGoal.x, velocity.x, delta * floorEffect);
        this.velocity.z = smoothMovement(velocityGoal.z, velocity.z, delta * floorEffect);

        updateModelsVectors(getModel().getPosition(), delta);
    }

    private float smoothMovement(float goalSpeed, float currentSpeed, float delta) {
        float speedDifference = goalSpeed - currentSpeed;

        if (speedDifference > delta) {
            return currentSpeed + delta;
        }
        if (speedDifference < -delta) {
            return currentSpeed - delta;
        }
        return goalSpeed;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public Vector3f getVelocityGoal() {
        return velocityGoal;
    }

    private void updateModelsVectors(Vector3f position, float delta) {
        position.x += this.velocity.x * delta;
        position.y += this.velocity.y * delta;
        position.z += this.velocity.z * delta;

        this.velocity.x += this.gravity.x * delta;
        this.velocity.y += this.gravity.y * delta;
        this.velocity.z += this.gravity.z * delta;
    }
}

