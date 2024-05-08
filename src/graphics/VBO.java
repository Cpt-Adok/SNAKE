package graphics;

import org.lwjgl.opengl.GL15;

public class VBO {
    private int id;

    public VBO(float[] vertices) {
        this.id = GL15.glGenBuffers();
        bind();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
    }

    public void clean() {
        unbind();
        GL15.glDeleteBuffers(this.id);
    }

    public void bind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.id);
    }

    public void unbind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}
