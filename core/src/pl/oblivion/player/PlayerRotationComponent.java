package pl.oblivion.player;

import pl.oblivion.base.Model;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.game.MouseInput;

public class PlayerRotationComponent extends RotateComponent {

	private MouseInput mouseInput;
	PlayerRotationComponent(Model model, float rotationSpeed, MouseInput mouseInput) {
		super(model, rotationSpeed);
		this.mouseInput = mouseInput;
	}

	@Override
	public void update(float delta){
		rotateWithCamera();
	}

	private void rotateWithCamera(){
		if (mouseInput.isRightButtonPressed()) {
			this.getModel().getRotation().y += mouseInput.getSwampAxis()* mouseInput.getDisplVec().y * mouseInput.getMouseSensitivity();
		}
	}
}
