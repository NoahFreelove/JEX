package org.JEX.Rendering.Camera;

import org.JEX.Core.Engine.Transform;
import org.JEX.Core.Scripting.Script;
import org.JEX.Logs.Log;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public abstract class Camera extends Script {

    public abstract Matrix4f getViewMatrix();
    public abstract Matrix4f getProjectionMatrix();
    public abstract Matrix4f getModelMatrix(Transform t);

    public FloatBuffer getModelMatrixBuffer(Transform t) {
        return getModelMatrix(t).get(BufferUtils.createFloatBuffer(16));
    }

    public FloatBuffer getViewMatrixBuffer() {
        return getViewMatrix().get(BufferUtils.createFloatBuffer(16));
    }

    public FloatBuffer getProjectionMatrixBuffer() {
        return getProjectionMatrix().get(BufferUtils.createFloatBuffer(16));
    }

    public FloatBuffer getMVPMatrixBuffer(Transform t) {
        return getMVPMatrix(t).get(BufferUtils.createFloatBuffer(16));
    }

    private final Matrix4f mvpMatrix = new Matrix4f();
    public Matrix4f getMVPMatrix(Transform t) {
        mvpMatrix.identity().mul(getProjectionMatrix()).mul(getViewMatrix()).mul(getModelMatrix(t));
        return mvpMatrix;
    }
}
