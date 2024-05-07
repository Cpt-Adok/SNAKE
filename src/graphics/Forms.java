package graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import org.lwjgl.opengl.GL11;

public class Forms {
    private VAO vao;
    private VBO vbo;
    private EBO ebo;

    private Shaders shaders;

    public Forms(float[] vertices, int[] indices) {
        this.vbo = new VBO(vertices);
        this.ebo = new EBO(indices);

        this.shaders = new Shaders("/Users/loic/Documents/Program/java/snake/res/shaders/cube.vert", "/Users/loic/Documents/Program/java/snake/res/shaders/cube.frag");
        
        int stride = 3*Float.BYTES;
        vao.setAttributePointer(0, 3, GL11.GL_FLOAT, stride, 0);
    }

    public void loop() {
        shaders.use();
        vao.drawElementIndices(GL_TRIANGLES, 0, 3);
    }
}