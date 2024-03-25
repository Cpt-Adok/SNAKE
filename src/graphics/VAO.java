package graphics;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.lwjgl.opengl.GL30;

public class VAO {
    private int id;

    public VAO() {
        this.id = GL30.glGenVertexArrays();
        bind();
    }

    public void cleanup() {
        unbind();
        GL30.glDeleteVertexArrays(id);
    }

    public void bind() {
        GL30.glBindVertexArray(this.id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void setAttributePointer(int index, int size, int type, int stride, long offset) {
        GL30.glEnableVertexAttribArray(index);
        GL30.glVertexAttribPointer(index, size, type, false, stride, offset);
    }

    public void drawElement(int mode, int count, int type, long indices) {
        bind();

        glDrawElements(mode, count, type, indices);
        unbind();
    }

    public void drawElementIndices(int mode, int first, int count) {
        bind();
        glDrawArrays(mode, first, count);
        unbind();
    }
}
