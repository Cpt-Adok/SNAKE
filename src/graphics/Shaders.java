package graphics;

import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Shaders {
    private int vertexShader;
    private int fragmentShader;
    
    public int shaderProgram;

    private int addVertShader(String vertexShaderSource) {
        int vertexShaderLocal = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        GL20.glShaderSource(vertexShaderLocal, vertexShaderSource);
        GL20.glCompileShader(vertexShaderLocal);

        return vertexShaderLocal;
    }

    private int addFragShader(String fragShaderSource) {
        int fragShaderLocal = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        
        GL20.glShaderSource(fragShaderLocal, fragShaderSource);
        GL20.glCompileShader(fragShaderLocal);

        return fragShaderLocal;
    }

    private void compileInProgram() {
        GL20.glAttachShader(this.shaderProgram, this.vertexShader);
        GL20.glAttachShader(this.shaderProgram, this.fragmentShader);

        GL20.glLinkProgram(this.shaderProgram);

        GL20.glDetachShader(this.shaderProgram, this.vertexShader);
        GL20.glDetachShader(this.shaderProgram, this.fragmentShader);

        GL20.glDeleteProgram(vertexShader);
        GL20.glDeleteProgram(fragmentShader);
    }

    public void clean() {
        GL20.glDeleteShader(this.shaderProgram);
    }

    public Shaders(String vertexShaderName, String fragShaderName) {
        this.vertexShader = addVertShader(getShaderFile(vertexShaderName));
        this.fragmentShader = addFragShader(getShaderFile(fragShaderName));

        this.compileInProgram();
    }

    public void use() {
        GL20.glUseProgram(this.shaderProgram);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    private String getShaderFile(String filename) {
        Path path = Paths.get("res/shaders/"+filename);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de trouver " + filename);
        }
    }
}
