package org.JEX.Rendering.VertexUtil;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.IO.Resources.Model;
import org.JEX.Logs.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VertexObjectGLWrapper extends VertexObject{
    protected int vertexBufferID = -1;
    protected int vertexLocation = 0;

    protected int normalBufferID = -1;
    protected int normalLocation = 1;

    protected int uvBufferID = -1;
    protected int uvLocation = 2;

    public VertexObjectGLWrapper(Model model) {
        super(model);
    }

    @EngineThread
    public void buffer(){
        int[] vertResult = abstractBufferGeneration(getVerts(), 3);
        vertexBufferID = vertResult[0];

        int[] normResult = abstractBufferGeneration(getNormals(), 3);
        normalBufferID = normResult[0];

        int[] uvResult = abstractBufferGeneration(getUvs(), 2);
        uvBufferID = uvResult[0];

    }

    public void queueBuffer(){
        JEX.getInstance().queueEngineFunction(this::buffer);
    }

    protected int[] abstractBufferGeneration(float[] data, int dataSize){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length*dataSize);
        for (float v: data){
            buffer.put(v);
        }
        buffer.flip();

        int vbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo); // Bind buffer

        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, buffer, GL46.GL_STATIC_DRAW); // Buffer data

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0); // Unbind buffer
        return new int[]{vbo};
    }

    /**
     * Binds the buffers to the given locations
     * *** SHADER MUST BE ENABLED ***

     * @return true if successful
     */
    @EngineThread
    public boolean bindBuffers(){
        if(!ready())
            return false;

        return (abstractEnable(uvLocation,2, uvBufferID) &&
                abstractEnable(normalLocation,3, normalBufferID) &&
                abstractEnable(vertexLocation,3, vertexBufferID));
    }

    protected boolean abstractEnable(int location, int size, int vbo){
        GL46.glEnableVertexAttribArray(location);
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
        GL46.glVertexAttribPointer(location, size, GL_FLOAT, false, 0, 0);
        // Check for errors
        if(GL46.glGetError() != GL46.GL_NO_ERROR){
            System.err.println("Error when enabling vertex arrays! Is a shader bound?");
            return false;
        }
        return true;
    }

    @EngineThread
    public void unbindBuffers(){
        GL46.glDisableVertexAttribArray(vertexLocation);
        GL46.glDisableVertexAttribArray(normalLocation);
        GL46.glDisableVertexAttribArray(uvLocation);
    }

    @Override
    void destroy() {
        if(vertexBufferID >=0){
            GL46.glDeleteBuffers(vertexBufferID);
        }
        if(normalBufferID >=0){
            GL46.glDeleteBuffers(normalBufferID);
        }
        if(uvBufferID >=0){
            GL46.glDeleteBuffers(uvBufferID);
        }
    }

    public boolean ready(){
        return vertexBufferID != -1 && normalBufferID != -1 && uvBufferID != -1;
    }

    public void setVertexLocation(int vertexLocation) {
        this.vertexLocation = vertexLocation;
    }

    public void setNormalLocation(int normalLocation) {
        this.normalLocation = normalLocation;
    }

    public void setUvLocation(int uvLocation) {
        this.uvLocation = uvLocation;
    }
}
