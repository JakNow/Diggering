package pl.oblivion.shaders;

import org.lwjgl.opengl.GL11;
import pl.oblivion.utils.MyFile;

import java.io.BufferedReader;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

    private int programID;

    public ShaderProgram(MyFile vertexFile, MyFile fragmentFile, String... inVariables) {
        int vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
        int fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        bindAttributes(inVariables);
        glLinkProgram(programID);
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
    }

    protected void storeAllUniformLocations(Uniform... uniforms) {
        for (Uniform uniform : uniforms) {
            uniform.storeUniformLocation(programID);
        }
        glValidateProgram(programID);
    }

    public void start() {
        glUseProgram(programID);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        glDeleteProgram(programID);
    }

    private void bindAttributes(String[] inVariables) {
        for (int i = 0; i < inVariables.length; i++) {
            glBindAttribLocation(programID, i, inVariables[i]);
        }
    }

    private int loadShader(MyFile file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = file.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader " + file);
            System.exit(-1);
        }
        return shaderID;
    }


}
