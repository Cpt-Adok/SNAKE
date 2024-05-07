package graphics;

import org.lwjgl.opengl.*;

public class Shaders {
    private int vertexShader;
    private int fragmentShader;
    public int shaderProgram;

    public Shaders(String vertexShaderSource, String fragmentShaderSource) {
        vertexShader = addVertShader(vertexShaderSource);
        fragmentShader = addFragShader(fragmentShaderSource);
        shaderProgram = compileInProgram();
    }

    private int addVertShader(String vertexShaderSource) {
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSource);
        GL20.glCompileShader(vertexShader);
        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) != GL20.GL_TRUE) {
            throw new RuntimeException("Vertex shader compilation failed: " + GL20.glGetShaderInfoLog(vertexShader));
        }
        return vertexShader;
    }

    private int addFragShader(String fragmentShaderSource) {
        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);
        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) != GL20.GL_TRUE) {
            throw new RuntimeException("Fragment shader compilation failed: " + GL20.glGetShaderInfoLog(fragmentShader));
        }
        return fragmentShader;
    }

    private int compileInProgram() {
        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) != GL20.GL_TRUE) {
            throw new RuntimeException("Shader program linking failed: " + GL20.glGetProgramInfoLog(shaderProgram));
        }
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        return shaderProgram;
    }

    public void use() {
        GL20.glUseProgram(shaderProgram);
    }

    public void cleanUp() {
        GL20.glDeleteProgram(shaderProgram);
    }
}
