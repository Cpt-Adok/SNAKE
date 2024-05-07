import item.*;
import personnages.*;
import graphics.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Graphics {
    private long window;

    private double deltaTime;
    private double currentTime;
    private double lastFrame;

    private GLFWVidMode window_VidMode;

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("unable to initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(800, 600, "snake", MemoryUtil.NULL, MemoryUtil.NULL);
        window_VidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        GLFW.glfwWindowHint(GLFW_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW_VERSION_MINOR, 6);

        GLFW.glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        GLFW.glfwWindowHint(GLFW_DEPTH_BITS, 24);
        GLFW.glfwWindowHint(GLFW_STENCIL_BITS, 8);
    
        GLFW.glfwWindowHint(GLFW_SAMPLES, 4);

        GLFW.glfwMakeContextCurrent(window);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_MULT);
    }

    private void loop() {
        GL.createCapabilities();

        lastFrame = 0.0f;

        while(!glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            currentTime = glfwGetTime();
            deltaTime = currentTime - lastFrame;


            lastFrame = currentTime;
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
