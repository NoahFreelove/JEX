package org.JEX.Core.IO.Resources;

import org.JEX.Logs.Log;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Arrays;

public class Model {
    private Vector3f[] verts;
    private Vector3f[] normals;
    private Vector2f[] uvs;

    public Model(Vector3f[] verts, Vector3f[] normals, Vector2f[] uvs) {
        this.verts = verts;
        this.normals = normals;
        this.uvs = uvs;
    }
    public static Model create2DModel(Vector2f[] points, Vector2f[] normals){
        return new Model(resizeVec2Array(points), resizeVec2Array(normals),emptyVec2Array(normals.length));
    }

    private static Vector3f[] resizeVec2Array(Vector2f[] input){
        Vector3f[] output = new Vector3f[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = new Vector3f(input[i],0);
        }
        return output;
    }
    private static Vector2f[] emptyVec2Array(int size){
        Vector2f[] arr = new Vector2f[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new Vector2f();
        }
        return arr;
    }

    public Vector3f[] getVerts() {
        return verts;
    }

    public void setVerts(Vector3f[] verts) {
        this.verts = verts;
    }

    public Vector3f[] getNormals() {
        return normals;
    }

    public void setNormals(Vector3f[] normals) {
        this.normals = normals;
    }

    public Vector2f[] getUvs() {
        return uvs;
    }

    public void setUvs(Vector2f[] uvs) {
        this.uvs = uvs;
    }
}
