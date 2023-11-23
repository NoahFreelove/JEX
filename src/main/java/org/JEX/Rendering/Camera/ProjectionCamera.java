package org.JEX.Rendering.Camera;

import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Engine.Transform;
import org.JEX.Logs.Log;
import org.JEX.Main;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ProjectionCamera extends Camera{

    private float fov = 90;
    private float near = 0.1f;
    private float far = 100.0f;

    private Vector3f direction = new Vector3f();
    private Vector3f right = new Vector3f();
    private Vector3f up = new Vector3f();
    private Vector3f forward = new Vector3f();

    private float halfWidth;
    private float halfHeight;
    private float horizAngle = 3.14f;
    private float vertAngle = 0f;

    private Quaternionf vec4ToQuat(Vector4f v){
        // We need to convert into radians

        return new Quaternionf(Math.toRadians(v.x), Math.toRadians(v.y), Math.toRadians(v.z),v.w());
    }

    private final Matrix4f viewMatrix = new Matrix4f();
    private final Vector3f dest = new Vector3f();
    @Override
    public Matrix4f getViewMatrix() {
        Transform t = getTransform();
        viewMatrix.identity();
        halfWidth = JEX.getInstance().getWindow().getWindowWidth()/2f;
        halfHeight = JEX.getInstance().getWindow().getWindowHeight()/2f;
        direction = new Vector3f((float) (Math.cos(vertAngle)* Math.sin(horizAngle)),
                (float) Math.sin(vertAngle),
                (float) (Math.cos(vertAngle) * Math.cos(horizAngle)));

        forward = new Vector3f((float) Math.sin(horizAngle),0f, (float) Math.cos(horizAngle));

        right = new Vector3f((float) Math.sin(horizAngle - Math.PI/2),0f, (float) Math.cos(horizAngle- Math.PI/2));

        up = new Vector3f();
        right.cross(direction,up);

        t.position().add(direction, dest);

        viewMatrix.lookAt(new Vector3f().add(t.position()),dest,up);
        return viewMatrix;
    }

    private final Matrix4f projection = new Matrix4f().identity();

    @Override
    public Matrix4f getProjectionMatrix() {
        projection.identity();
        projection.perspective((float) Math.toRadians(fov), JEX.getInstance().getWindow().getAspectRatio(), near, far);
        return projection;
    }

    private final Matrix4f modelMatrix = new Matrix4f();
    @Override
    public Matrix4f getModelMatrix(Transform t) {
        modelMatrix.identity()
                .rotate(vec4ToQuat(t.rotation()))
                .translate(-t.position().x,-t.position().y,-t.position().z).scale(t.scale());
        return modelMatrix;
    }

}
