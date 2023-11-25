package org.JEX.Rendering.VertexUtil;

import org.JEX.Core.IO.Resources.Model;
import org.JEX.Logs.Log;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;

/**
 * A collection of vertices which form a 2D or 3D Object. (To make a 2D one just ignore your Z values)
 */
public abstract class VertexObject {
    private float[] verts;
    private float[] normals;
    private float[] uvs;
    private Model modelRef;

    public VertexObject(Model model) {
        setModel(model);
    }

    public static float[] vec4ToFloatArray(Vector4f[] data) {
        float[] output = new float[data.length*4];
        for (int i = 0; i < data.length; i++) {
            output[i*4] = data[i].x();
            output[i*4+1] = data[i].y();
            output[i*4+2] = data[i].z();
            output[i*4+3] = data[i].w();
        }
        return output;
    }

    public void setModel(Model model){
        this.modelRef = model;
        verts = vec3ToFloatArray(model.getVerts());
        normals = vec3ToFloatArray(model.getNormals());
        uvs = vec2ToFloatArray(model.getUvs());
    }
    public Model getModel(){
        return modelRef;
    }

    public static float[] vec3ToFloatArray(Vector3f[] floats){
        float[] output = new float[floats.length*3];
        for (int i = 0; i < floats.length; i++) {
            output[i*3] = floats[i].x();
            output[i*3+1] = floats[i].y();
            output[i*3+2] = floats[i].z();
        }
        return output;
    }

    public static float[] vec2ToFloatArray(Vector2f[] floats){
        float[] output = new float[floats.length*2];
        for (int i = 0; i < floats.length; i++) {
            output[i*2] = floats[i].x();
            output[i*2+1] = floats[i].y();
        }
        return output;
    }

    public float[] getVerts() {
        return verts;
    }

    public float[] getNormals() {
        return normals;
    }

    public float[] getUvs() {
        return uvs;
    }

    public int vertLen(){
        return verts.length / 3; // we have 3 floats per vertex. This should always be divisible by 3 so division is fine.
    }

    public int normLen(){
        return normals.length / 3;
    }

    public int uvLen(){
        return uvs.length; // we have 2 floats per vertex. This should always be divisible by 2 so division is fine.
    }

    abstract void destroy();
}
