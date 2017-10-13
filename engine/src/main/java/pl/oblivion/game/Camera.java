package pl.oblivion.game;

import org.joml.Vector3f;
import pl.oblivion.base.Model;
import pl.oblivion.core.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private float distanceFromPlayer = 10;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0,0,0);
    private Vector3f rotation = new Vector3f(0,0,0);
    private final Model model;
    private final MouseInput mouseInput;

    public Camera(Model model, MouseInput mouseInput) {
        this.model = model;
        this.mouseInput = mouseInput;
    }

    public void update(){
        cameraMove();
        System.out.println(angleAroundPlayer);
    }

    private void cameraMove(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance,verticalDistance);
        this.rotation.y = 180 - (model.getRotation().y + angleAroundPlayer);

    }
    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = model.getRotation().y + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = model.getPosition().x - offsetX;
        position.z = model.getPosition().z - offsetZ;
        position.y = model.getPosition().y + verticalDistance;
    }
    private float calculateHorizontalDistance(){
        return (float)(distanceFromPlayer * Math.cos(Math.toRadians(rotation.x)));
    }

    private float calculateVerticalDistance(){
        return (float)(distanceFromPlayer * Math.sin(Math.toRadians(rotation.x)));
    }

    private void calculateZoom(){
        float zoomLevel = mouseInput.getWheelY();
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch(){
        if(mouseInput.isRightButtonPressed()){
            float pitchChange = mouseInput.getDisplVec().x * 0.1f;
            rotation.x +=pitchChange;
        }
    }

    private void calculateAngleAroundPlayer(){
        if(mouseInput.isRightButtonPressed()){
            float angleChange = mouseInput.getDisplVec().y * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
