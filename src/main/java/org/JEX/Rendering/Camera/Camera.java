package org.JEX.Rendering.Camera;

import org.JEX.Core.Scripting.Script;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public abstract class Camera extends Script {

    public abstract Matrix4f getViewMatrix();
    public abstract Matrix4f getProjectionMatrix();
    public abstract Matrix4f getModelMatrix();

    public FloatBuffer getModelMatrixBuffer() {
        return BufferUtils.createFloatBuffer(16).put(getModelMatrix().get(new float[16]));
    }

    public FloatBuffer getViewMatrixBuffer() {
        return BufferUtils.createFloatBuffer(16).put(getViewMatrix().get(new float[16]));
    }

    public FloatBuffer getProjectionMatrixBuffer() {
        return BufferUtils.createFloatBuffer(16).put(getProjectionMatrix().get(new float[16]));
    }

    public FloatBuffer getMVPMatrixBuffer() {
        return BufferUtils.createFloatBuffer(16).put(getMVPMatrix().get(new float[16]));
    }

    public Matrix4f getMVPMatrix() {
        return getProjectionMatrix().mul(getViewMatrix()).mul(getModelMatrix());
    }


}
