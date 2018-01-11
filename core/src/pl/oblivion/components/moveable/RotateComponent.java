package pl.oblivion.components.moveable;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.components.BaseComponent;
import pl.oblivion.components.ComponentType;

public class RotateComponent extends BaseComponent {

    private static final ComponentType componentType = ComponentType.ROTATE;
    private Vector3f rotation;
    private float currentTurnSpeed;
    private float rotationSpeed;

    public RotateComponent(Model model, float rotationSpeed) {
        super(model, componentType);
        this.rotation = new Vector3f(model.getRotation());
        this.rotationSpeed = rotationSpeed;
    }

    public void update(float delta) {
        rotate(0, currentTurnSpeed, 0, delta);
    }

	private void rotate(float dx, float dy, float dz, float delta) {
		this.getModel().getRotation().x += dx * delta;
		this.getModel().getRotation().y += dy * delta;
		this.getModel().getRotation().z += dz * delta;


        this.rotation.x = clampRotation(this.getModel().getRotation().x);
        this.rotation.y = clampRotation(this.getModel().getRotation().y);
        this.rotation.z = clampRotation(this.getModel().getRotation().z);

        this.getModel().setRotation(this.rotation);
    }

	private float clampRotation(float rotation) {
		rotation %= 360;
		if (rotation < 0) {
			rotation += 360;
		}
		return rotation;
	}
}
