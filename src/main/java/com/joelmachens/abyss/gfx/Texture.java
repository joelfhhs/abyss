package com.joelmachens.abyss.gfx;

import com.joelmachens.abyss.utility.Logger;
import com.joelmachens.abyss.utility.Resource;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.jemalloc.JEmalloc.*;

/**
 * Class containing a single texture.
 */
public class Texture {
    private final int reference;
    private final int width;
    private final int height;

    /**
     * Create a texture from the specified resource.
     * @param resource name of the texture resource
     */
    public Texture(String resource) {
        byte[] image = Resource.loadBinary("gfx/textures/" + resource + ".png");

        if (image.length == 0) {
            Logger.error("Texture " + resource + " has an invalid asset!");
            throw new RuntimeException("No or null texture asset.");
        }

        Logger.log("Loading Texture: " + resource);

        this.reference = glGenTextures();

        ByteBuffer buffer = je_malloc(image.length);
        assert buffer != null;
        buffer.put(image);
        buffer.flip();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(true);
            ByteBuffer texture = stbi_load_from_memory(buffer, width, height, channels, 4);
            stbi_set_flip_vertically_on_load(false);
            if (texture == null) {
                Logger.error("Texture " + resource + " loading failed! " + stbi_failure_reason());
                throw new RuntimeException("Could not load textures.");
            }

            this.width = width.get(0);
            this.height = height.get(0);

            if (channels.get(0) != 4) {
                Logger.warn("Texture " + resource + " has " + channels.get(0) + " channels, expected 4!");
            }

            glBindTexture(GL_TEXTURE_2D, reference);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
            glGenerateMipmap(GL_TEXTURE_2D);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);

            stbi_image_free(texture);
        }
        je_free(buffer);
    }

    /**
     * Binds the texture.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, reference);
    }

    /**
     * Get the OpenGL reference to the texture.
     * @return OpenGL texture object
     */
    public int getReference() {
        return reference;
    }

    /**
     * Get the width of the texture.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the texture.
     * @return height
     */
    public int getHeight() {
        return height;
    }
}
