package pl.oblivion.game;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.utils.Maths;

public class Camera {

	private final Model model;
	private final MouseInput mouseInput;
	private float distanceFromPlayer = 10;
	private float angleAroundPlayer = 0;
	private Vector3f position = new Vector3f(0, 0, 0);
	private Vector3f rotation = new Vector3f(0, 0, 0);
	private float overHead = 1.5f;

	public Camera(Model model, MouseInput mouseInput) {
		this.model = model;
		this.mouseInput = mouseInput;
	}

	public void update() {
		cameraMove();
	}

	private void cameraMove() {
		calculateZoom();
		calculatePitch();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistnace();

		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.rotation.y = 180 - (model.getRotation().y + angleAroundPlayer);

	}
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float px = model.getPosition().x;
		float py = model.getPosition().y;
		float pz = model.getPosition().z;


		float theta = model.getRotation().y + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));

		position.x = px - offsetX;
		position.z = pz - offsetZ;
		position.y = py + overHead + verticalDistance;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(this.rotation.x)));
	}

	private float calculateVerticalDistnace() {
		 return (float) (distanceFromPlayer * Math.sin(Math.toRadians(this.rotation.x)));

	}

	private void calculateZoom() {
		float zoomLevel = mouseInput.getWheelY() * mouseInput.getMouseSensitivity()*3;
		distanceFromPlayer += mouseInput.getSwampAxis()*zoomLevel;
		distanceFromPlayer = Maths.clamp(distanceFromPlayer, 2, 10);

	}

	private void calculatePitch() {
		if (mouseInput.isRightButtonPressed()) {
			this.rotation.x += mouseInput.getSwampAxis()* mouseInput.getDisplVec().x * mouseInput.getMouseSensitivity();
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
}
