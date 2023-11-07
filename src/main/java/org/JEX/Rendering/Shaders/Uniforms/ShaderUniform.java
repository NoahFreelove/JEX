package org.JEX.Rendering.Shaders.Uniforms;

import org.lwjgl.opengl.GL46;

public abstract class ShaderUniform<T> {
    protected T value;
    protected String name;

    public ShaderUniform(String name){
        this.name = name;
    }

    public ShaderUniform(String name, T value){
        this.name = name;
        this.value = value;
    }

    public void setName(String name){
        this.name = name;
    }

    void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return value;
    }

    public String getName(){
        return name;
    }

    public abstract void setUniform();

}
