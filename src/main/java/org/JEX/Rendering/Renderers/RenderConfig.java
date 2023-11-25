package org.JEX.Rendering.Renderers;

import org.lwjgl.opengl.GL46;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class RenderConfig {
    int GL_DRAW_MODE = GL46.GL_TRIANGLE_FAN;
    int[] GL_ENABLES = {GL_DEPTH_TEST, GL_CULL_FACE};
}
