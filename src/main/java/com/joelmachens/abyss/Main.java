package com.joelmachens.abyss;

import com.joelmachens.abyss.gfx.Window;
import com.joelmachens.abyss.utility.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Main {
    public static void main(String[] args) {
        Logger.reset();
        Logger.log("Starting Abyss...");

        if (!GLFW.glfwInit()) {
            Logger.error("GLFW failed to initialise!");
            throw new IllegalStateException("GLFW failed to initialise!");
        }
        GLFW.glfwSetErrorCallback(GLFWErrorCallback.createThrow());

        Window mainWindow = new Window();

        // main application loop
        while (!mainWindow.isClosing()) {
            GLFW.glfwPollEvents();
            mainWindow.render();
        }

        Logger.log("Shutting down...");
        GLFW.glfwTerminate();
    }
}
