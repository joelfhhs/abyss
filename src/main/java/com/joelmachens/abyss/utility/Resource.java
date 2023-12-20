package com.joelmachens.abyss.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Manages resources.
 * TODO: add fallback to search working directory for files.
 */
public class Resource {
    /**
     * Gets a text resource from the jar.
     * @param resource path of the resource
     * @return string representing the entire text file
     */
    public static String loadText(String resource) {
        try(InputStream in = Resource.class.getResourceAsStream("/" + resource)) {
            if (in == null) {
                return "";
            }
            return new String(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a binary resource from the jar.
     * @param resource path of the resource
     * @return byte array of the file on the heap
     */
    public static byte[] loadBinary(String resource) {
        try(InputStream in = Resource.class.getResourceAsStream("/" + resource)) {
            if (in == null) {
                return new byte[]{};
            }
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
