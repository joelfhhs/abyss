package com.joelmachens.abyss.gfx;

import com.joelmachens.abyss.GameState;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL41.*;

/**
 * The single OS window utilised to display the game UI.
 * TODO (far future): big rework to allow multiple game windows
 */
public class Window {
    private final long window;

    /**
     * Creates the application window.
     */
    public Window() {
        final long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode mode = glfwGetVideoMode(monitor);
        assert mode != null;

        // window hints
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        // borderless full screen stuff
        glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
        if (glfwGetPlatform() != GLFW_PLATFORM_COCOA) {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
            glfwWindowHint(GLFW_AUTO_ICONIFY, GLFW_FALSE);
        }

        // TODO: make multisampling a setting
        glfwWindowHint(GLFW_SAMPLES, 4);

        this.window = glfwCreateWindow(mode.width(), mode.height(), "Abyss", 0, 0);
        glfwMakeContextCurrent(this.window);
        GL.createCapabilities();
        glfwSwapInterval(1);
        glViewport(0, 0, mode.width(), mode.height());
        glEnable(GL_MULTISAMPLE);

        Shader main = new Shader("main");
        Texture texture = new Texture("null");

        int VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        main.bind();
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        int VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, GameState.triangle, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * GameState.FLOAT_SIZE, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * GameState.FLOAT_SIZE, 3 * GameState.FLOAT_SIZE);
        glEnableVertexAttribArray(1);
    }

    /**
     * Renders the current state of the game.
     */
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glfwSwapBuffers(window);
    }

    /**
     * Gets if the user or system has requested the application window close.
     * @return Should window close
     */
    public boolean isClosing() {
        return glfwWindowShouldClose(window);
    }
}
