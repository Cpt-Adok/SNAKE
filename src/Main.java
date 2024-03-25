import java.util.random.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    public static void main(String[] args) {
        if(!GLFW.glfwInit()) {
            System.err.println("initialization error");
            System.exit(1);
        }

        long window = GLFW.glfwCreateWindow(800, 600, "test fenetre", MemoryUtil.NULL, MemoryUtil.NULL);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }
}