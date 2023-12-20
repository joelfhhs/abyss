package com.joelmachens.abyss.gfx;

import com.joelmachens.abyss.utility.Logger;
import com.joelmachens.abyss.utility.Resource;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL41.*;

/**
 * A single, complete program for the GPU.
 */
public class Shader {
    private final int program;

    /**
     * Instantiate a shader from assets.
     * @param resource name of the shader program
     */
    public Shader(String resource) {
        String vertexSource = Resource.loadText("gfx/shaders/" + resource + ".vs");
        String fragmentSource = Resource.loadText("gfx/shaders/" + resource + ".fs");

        if (vertexSource.isBlank() || fragmentSource.isBlank()) {
            Logger.error("No source found for shader " + resource + "!");
            throw new RuntimeException("No or null shader source.");
        }

        Logger.log("Compiling Shader: " + resource);

        int vertex = glCreateShader(GL_VERTEX_SHADER);
        int fragment = glCreateShader(GL_FRAGMENT_SHADER);
        this.program = glCreateProgram();

        glShaderSource(vertex, vertexSource);
        glCompileShader(vertex);

        glShaderSource(fragment, fragmentSource);
        glCompileShader(fragment);

        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer i = stack.mallocInt(1);

            glGetShaderiv(vertex, GL_COMPILE_STATUS, i);
            if (i.get(0) == GL_FALSE) {
                Logger.error("Shader compilation failed for resource " + resource + ": " + glGetShaderInfoLog(vertex));
            }

            glGetShaderiv(fragment, GL_COMPILE_STATUS, i);
            if (i.get(0) == GL_FALSE) {
                Logger.error("Shader compilation failed for resource " + resource + ": " + glGetShaderInfoLog(fragment));
            }

            glGetProgramiv(program, GL_LINK_STATUS, i);
            if (i.get(0) == GL_FALSE) {
                Logger.error("Shader linking failed for resource " + resource + ": " + glGetProgramInfoLog(program));
            }
        }

        glDeleteShader(vertex);
        glDeleteShader(fragment);
    }

    /**
     * Binds the shader.
     */
    public void bind() {
        glUseProgram(program);
    }

    /**
     * Gets the shaders program identifier.
     * @return shader program
     */
    public int getProgram() {
        return program;
    }

}
