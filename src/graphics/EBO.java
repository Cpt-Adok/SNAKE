package graphics;

import org.lwjgl.opengl.GL15;

public class EBO {
    private int id;

    public EBO(int[] indices) {
        this.id = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.id);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        bind();
    }

    public void cleanup() {
        unbind();
        GL15.glDeleteBuffers(this.id);
    }

    public void bind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, (int)this.id);
    }

    public void unbind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
