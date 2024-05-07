import item.*;
import personnages.*;
import graphics.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

public class Graphics {
    // La fenêtre
    private long window;

    private GLFWVidMode window_VidMode;

    // Gestion des FPS.
    private double deltaTime;
    private double currentTime;
    private double lastFrame;

    private float vertices[] = {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
    }; 

    private int indices[] = {
        0, 1, 2
    };

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            throw new IllegalStateException("Impossible d'initialiser GLFW");
        }

        this.window = glfwCreateWindow(800, 600, "snake", MemoryUtil.NULL, MemoryUtil.NULL);
        this.window_VidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        try (MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); 
			IntBuffer pHeight = stack.mallocInt(1);
        
			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
		glfwShowWindow(window);
    }

    private void loop() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_MULT);

        Forms form = new Forms(vertices, indices);

        this.lastFrame = 0.0f;

        while (!GLFW.glfwWindowShouldClose(this.window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            this.currentTime = glfwGetTime();
            this.deltaTime = this.currentTime - this.lastFrame;
       
            form.loop();

            GLFW.glfwSwapBuffers(this.window);
            GLFW.glfwPollEvents();

            this.lastFrame = currentTime;
        }
    }

    public Graphics() {
        System.out.println("LWJGL Version : " + Version.getVersion() +".");
        init();
        loop();

        glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
    }
}
