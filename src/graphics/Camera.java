package graphics;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector3f;
import org.joml.Matrix4f;
import java.lang.Math;

public class Camera {
    private double mousePosX; 
    private double mousePosY; 

    private float cameraYaw; 
    private float cameraPitch; 

    private Vector3f cameraFront;

    private Vector3f cameraUp;
    private Vector3f cameraRight;
    private Vector3f worldUp;

    private long window;

    private boolean is_pressed(int key) {
        return glfwGetKey(this.window, key) == GLFW_PRESS;
    }

    public Camera(int width, int height, long window, float sensitivity) {
        this.window = window;

        this.cameraYaw = -90.0f;
        this.cameraPitch = 10.0f;

        this.speed = 1.0f;
        this.cameraSensitivity = sensitivity;

        this.cameraPosition = new Vector3f(0.0f, 0.0f, 0.0f);
        this.cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        this.cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

        this.worldUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.cameraRight = new Vector3f(0.0f, 0.0f, 0.0f);

        mousePosX = (width / 2);
        mousePosY = (height / 2);
    
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    
    public void inputProcess(float deltaTime) {
        float velocity = speed * (deltaTime/2);
        this.fov = 45.0f;

        if(is_pressed(GLFW_KEY_W)) { cameraPosition.add(cameraFront.mul(velocity));}
        if(is_pressed(GLFW_KEY_S)) { cameraPosition.sub(cameraFront.mul(velocity));}
        if(is_pressed(GLFW_KEY_A)) { cameraPosition.add(cameraRight.mul(velocity));}
        if(is_pressed(GLFW_KEY_D)) { cameraPosition.sub(cameraRight.mul(velocity));}
        if(is_pressed(GLFW_KEY_LEFT_CONTROL)) { cameraPosition.add(worldUp.mul(velocity));}
        if(is_pressed(GLFW_KEY_SPACE)) { cameraPosition.sub(worldUp.mul(velocity));}
    }

    public void mouseMouvementProcess() {
        double[] mouseX = new double[]{};
        double[] mouseY = new double[]{};

        glfwGetCursorPos(window, mouseX, mouseY);
        
        float deltaX = (float)(mouseX[0] - this.mousePosX);
        float deltaY = (float)(mouseY[0] - this.mousePosY);

        this.mousePosX = mouseX[0];
        this.mousePosY = mouseY[0]; 

        deltaX *= (this.cameraSensitivity/2);
        deltaY *= (this.cameraSensitivity/2);

        this.cameraYaw += deltaX;
        this.cameraPitch += deltaY;

        if (this.cameraPitch > 89.0f)
            this.cameraPitch = 89.0f;
        if (this.cameraPitch < -89.0f)
            this.cameraPitch = -89.0f;
    }

    public void updateCameraVectors() {
        Vector3f front = new Vector3f(new float[] {
            (float)(Math.cos(Math.toRadians(cameraYaw)) * Math.cos(Math.toRadians(cameraPitch))),
            (float)(Math.sin(Math.toRadians(cameraPitch))),
            (float)(Math.sin(Math.toRadians(cameraYaw)) * Math.cos(Math.toRadians(cameraPitch)))
        });

        cameraFront = front.normalize();

        cameraRight = cameraRight.cross(cameraFront, worldUp).normalize();
        cameraUp = cameraUp.cross(cameraRight, cameraFront).normalize();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(cameraPosition, cameraPosition.add(cameraFront), cameraUp);
    }

    public float speed; 
    public float cameraSensitivity;

    public float fov;

    public Vector3f cameraPosition;
}
