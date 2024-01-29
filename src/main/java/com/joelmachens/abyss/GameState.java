package com.joelmachens.abyss;

/**
 * Class encapsulating entire state of the game.
 */
public class GameState {
    public static final int FLOAT_SIZE = 4;
    public static final float[] quad = new float[] {
            // vertices         texture coordinates     texture index
            -1.0f, -1.0f,       0.0f, 0.0f,             (float) Math.floor(Math.random() * 4),
            -1.0f, 1.0f,        0.0f, 1.0f,             (float) Math.floor(Math.random() * 4),
            1.0f, 1.0f,         1.0f, 1.0f,             (float) Math.floor(Math.random() * 4),

            -1.0f, -1.0f,       0.0f, 0.0f,             (float) Math.floor(Math.random() * 4),
            1.0f, -1.0f,        1.0f, 0.0f,             (float) Math.floor(Math.random() * 4),
            1.0f, 1.0f,         1.0f, 1.0f,             (float) Math.floor(Math.random() * 4),
    };
}
