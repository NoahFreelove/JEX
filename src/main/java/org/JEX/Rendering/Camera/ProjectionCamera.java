package org.JEX.Rendering.Camera;

import org.joml.Matrix4f;

public class ProjectionCamera extends Camera{
    @Override
    public Matrix4f getViewMatrix() {
        return new Matrix4f().identity();
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().identity();
    }

    @Override
    public Matrix4f getModelMatrix() {
        return new Matrix4f().identity();
    }
}
