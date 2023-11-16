package org.JEX.Core.Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Transform {
    Vector3f position = new Vector3f();
    Vector4f rotation = new Vector4f();
    Vector3f scale = new Vector3f();

    public Transform() {
    }

    public Transform(Vector3f position) {
        this.position = position;
    }

    public Transform(Vector3f position, Vector4f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Transform(Vector3f position, Vector4f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(float x, float y, float z, float rx, float ry, float rz, float rw, float sx, float sy, float sz){
        this.position = new Vector3f(x,y,z);
        this.rotation = new Vector4f(rx,ry,rz,rw);
        this.scale = new Vector3f(sx,sy,sz);
    }

    public Vector3f position(){
        return position;
    }
    public Vector3f positionC(){
        return new Vector3f(position);
    }

    public Vector4f rotation(){
        return rotation;
    }
    public Vector4f rotationC(){
        return new Vector4f(rotation);
    }

    public Vector3f scale(){
        return scale;
    }

    public Vector3f scaleC(){
        return new Vector3f(scale);
    }

    public void translate(Vector3f translation){
        position.add(translation);
    }

    public void translate(float x, float y, float z){
        position.add(x,y,z);
    }

    public void rotate(Vector4f rotation){
        this.rotation.add(rotation);
    }

    public void rotate(float x, float y, float z, float w){
        this.rotation.add(x,y,z,w);
    }

    public void scale(Vector3f scale){
        this.scale.add(scale);
    }

    public void scale(float x, float y, float z){
        this.scale.add(x,y,z);
    }

    public void add(Transform t){
        position.add(t.position);
        rotation.add(t.rotation);
        scale.add(t.scale);
    }

    public void add(float x, float y, float z, float rx, float ry, float rz, float rw, float sx, float sy, float sz){
        position.add(x,y,z);
        rotation.add(rx,ry,rz,rw);
        scale.add(sx,sy,sz);
    }
}
