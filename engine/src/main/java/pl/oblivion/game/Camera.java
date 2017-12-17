package pl.oblivion.game;

import org.joml.Vector3f;
import pl.oblivion.base.Model;

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
		calculateAngleAroundPlayer();

		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();

		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.rotation.y = 180 - (model.getRotation().y + angleAroundPlayer);

	}

	private void calculateZoom() {
		float zoomLevel = mouseInput.getWheelY();
		distanceFromPlayer -= zoomLevel;
	}

	private void calculatePitch() {
		if (mouseInput.isRightButtonPressed()) {
			float pitchChange = mouseInput.getDisplVec().x * 0.1f;
			rotation.x += pitchChange;
		}
	}

	private void calculateAngleAroundPlayer() {
		if (mouseInput.isRightButtonPressed()) {
			float angleChange = mouseInput.getDisplVec().y * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(rotation.x)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(rotation.x)));
	}

	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float theta = model.getRotation().y + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = model.getPosition().x - offsetX;
		position.z = model.getPosition().z - offsetZ;
		position.y = model.getPosition().y + verticalDistance + overHead;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
}
