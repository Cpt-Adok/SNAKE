package graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;


public class VAO {
    private int id;

    public VAO() {
        this.id = GL30.glGenVertexArrays();
        bind();
    }

    public void clean() {
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
        GL30.glVertexAttribPointer(index, size, type, false, stride, offset);
        enableVertexAttribArray(index);
    }

    public void enableVertexAttribArray(int index) {
        GL30.glEnableVertexAttribArray(index);
    }

    public void disableVertexAttribArray(int index) {
        GL30.glDisableVertexAttribArray(index);
    }

    public void drawElements(int mode, int count, int type, long indices) {
        bind();
        GL11.glDrawElements(mode, count, type, indices);
        unbind();
    }

    public void drawElements(int mode, int first, int count) {
        bind();
        GL11.glDrawArrays(mode, first, count);
        unbind();
    }
}
