package org.JEX.Rendering.Shaders;

import org.JEX.Core.Engine.JEX;
import org.JEX.Rendering.VertexUtil.VertexObject;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class Layout {
    protected int size;
    protected float[] data;

    public Layout(float[] data, int size) {
        this.size = size;
        this.data = data;
    }

    public Layout(Vector3f[] data){
        this.data = VertexObject.vec3ToFloatArray(data);
        this.size = 3;
    }

    public Layout(Vector2f[] data){
        this.data = VertexObject.vec2ToFloatArray(data);
        this.size = 2;
    }

    public Layout(Vector4f[] data) {
        this.data = VertexObject.vec4ToFloatArray(data);
        this.size = 4;
    }

    public void queueBuffer(){
        JEX.queueEngineFunction(this::buffer);
    }

    protected abstract void buffer();
    protected abstract boolean bind();
    protected abstract void unbind();

    public float[] getData(){
        return data;
    }
    protected abstract void destroy();

    public abstract boolean isBuffered();
}
