package graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Graphics {
    private VAO vao;
    private VBO vbo;
    private EBO ebo;
    private Shaders shaders;

    private float vertices[] = {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
    };  

    private int indices[] = {
        0, 1, 2
    };

    private double lastFrame;
    private double currentTime;
    private double deltaTime;

    private long window;

    public Graphics() {
        System.out.println("LWJGL Version : " + Version.getVersion());
        init(); // Initialiser OpenGL et créer les objets VAO, VBO, EBO
        loop(); // Entrer dans la boucle principale de rendu
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
    
        if(!glfwInit()) {
            throw new IllegalStateException("Impossible de charger GLFW");
        } 
    
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1);
    
        window = glfwCreateWindow(800, 600, "snake", NULL, NULL);
    
        if (window == NULL) {
            throw new RuntimeException("Impossible de créer la fenêtre GLFW");
        }

        // Créer les objets VAO, VBO, EBO ici après avoir initialisé OpenGL
        this.vbo = new VBO(this.vertices);
        this.ebo = new EBO(this.indices);
        this.vao = new VAO();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_MULT);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    private void loop() {
        this.shaders = new Shaders("cube.vert", "cube.frag");

        this.lastFrame = 0.0f;

        while(!glfwWindowShouldClose(window)) {
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

            this.currentTime = glfwGetTime();
            this.deltaTime = currentTime - lastFrame;  

            this.vao.drawElements(GL_TRIANGLES, 0, 3);

            lastFrame = currentTime;
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        
        // Nettoyage des ressources OpenGL après la fin de la boucle de rendu
        this.vao.clean();
        this.vbo.clean();
        this.ebo.clean();

        // Fermeture de GLFW et nettoyage des callbacks
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).set();
    }
}