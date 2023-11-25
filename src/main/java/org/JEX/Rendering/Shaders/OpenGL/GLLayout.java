package org.JEX.Rendering.Shaders.OpenGL;

import org.JEX.Logs.Exceptions.GLExceptions.JEXception_GL;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.Layout;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class GLLayout extends Layout {
    protected int bufferID = -1;
    protected int location = -1;
    protected int targetLocation = -1;
    protected boolean isBinded = false;
    public GLLayout(float[] data, int size) {
        super(data, size);
    }

    public GLLayout(Vector3f[] data) {
        super(data);
    }

    public GLLayout(Vector4f[] data) {
        super(data);
    }


    public GLLayout(Vector2f[] data) {
        super(data);
    }
    /**
     * Binds the buffers to the given locations
     * *** SHADER MUST BE ENABLED ***

     * @return true if successful
     */
    @Override
    protected void buffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length*size);
        for (float v: data){
            buffer.put(v);
        }
        buffer.flip();

        int vbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo); // Bind buffer

        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, buffer, GL46.GL_STATIC_DRAW); // Buffer data

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0); // Unbind buffer
        bufferID = vbo;
    }

    @Override
    protected boolean bind() {
        if(isBinded){
            Log.error(new JEXception_GL("Layout is already binded!"));
            return false;
        }
        location = targetLocation;

        if(!isBuffered())
            return false;
        if(location == -1){
            Log.error(new JEXception_GL("Location is not set!"));
        }
        GL46.glEnableVertexAttribArray(location);
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, bufferID);
        GL46.glVertexAttribPointer(location, size, GL_FLOAT, false, 0, 0);
        // Check for errors
        if(GL46.glGetError() != GL46.GL_NO_ERROR){
            System.err.println("Error when enabling vertex arrays! Is a shader bound?");
            return false;
        }
        //System.out.println("Bound layout to location " + location);
        isBinded = true;
        return true;
    }

    @Override
    protected void unbind() {
        if(!isBinded)
            return;
        GL46.glDisableVertexAttribArray(location);
        isBinded = false;
    }

    @Override
    protected void destroy() {
        if(bufferID != -1){
            GL46.glDeleteBuffers(bufferID);
            data = null;
            bufferID = -1;
        }
    }
    public boolean isBuffered(){
        return bufferID != -1;
    }

    public static void bufferAll(GLLayout... layouts){
        for (GLLayout l :
                layouts) {
            l.queueBuffer();
        }
    }

    public static boolean bindAll(int[] locations, GLLayout... layouts){
        boolean success = true;
        for (int i = 0; i < layouts.length; i++) {
            layouts[i].setLocation(locations[i]);
            if(!layouts[i].bind())
                success = false;
        }
        return success;
    }

    public static void unbindAll(GLLayout... layouts){
        for (GLLayout l :
                layouts) {
            l.unbind();
        }
    }

    public static void destroyAll(GLLayout... layouts){
        for (GLLayout l :
                layouts) {
            l.destroy();
        }
    }

    public void setLocation(int loc){
        this.targetLocation = loc;
    }

}
