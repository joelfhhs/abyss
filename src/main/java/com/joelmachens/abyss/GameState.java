package com.joelmachens.abyss;

/**
 * Class encapsulating entire state of the game.
 */
public class GameState {
    public static final int FLOAT_SIZE = 4;
    public static final float[] triangle = new float[] {
            // vertices         texture coordinates
            -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
            1.0f, -1.0f, 0.0f,  1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,   0.5f, 1.0f
    };
}
