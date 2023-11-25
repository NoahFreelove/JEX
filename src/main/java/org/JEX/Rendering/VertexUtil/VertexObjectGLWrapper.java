package org.JEX.Rendering.VertexUtil;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.IO.Resources.Model;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.OpenGL.GLLayout;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VertexObjectGLWrapper extends VertexObject{
    protected GLLayout vertexLayout;
    protected GLLayout normalLayout;
    protected GLLayout uvLayout;
    protected final int[] layouts = {0,1,2};

    public VertexObjectGLWrapper(Model model) {
        super(model);
        vertexLayout = new GLLayout(model.getVerts());
        normalLayout = new GLLayout(model.getNormals());
        uvLayout = new GLLayout(model.getUvs());
    }

    public void buffer(){
        GLLayout.bufferAll(vertexLayout, normalLayout, uvLayout);
    }

    @EngineThread
    public boolean bindBuffers(){
        return GLLayout.bindAll(layouts, vertexLayout, normalLayout, uvLayout);
    }

    @EngineThread
    public void unbindBuffers(){
        GLLayout.unbindAll(vertexLayout, normalLayout, uvLayout);
    }

    @Override
    void destroy() {
        GLLayout.destroyAll(vertexLayout, normalLayout, uvLayout);
    }

    public boolean ready(){
        return (vertexLayout.isBuffered() && normalLayout.isBuffered() && uvLayout.isBuffered());
    }


}
