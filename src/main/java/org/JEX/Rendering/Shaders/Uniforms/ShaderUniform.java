package org.JEX.Rendering.Shaders.Uniforms;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.lwjgl.system.APIUtil;

public abstract class ShaderUniform<T> {
    protected T value;
    protected String name;
    protected final GraphicsAPI api;

    public ShaderUniform(String name, T value, GraphicsAPI api){
        this.name = name;
        this.value = value;
        this.api = api;
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
    @EngineThread
    // Need to change this from int program to some object so it works for
    // OpenGL and Vulkan
    public abstract void setUniform(int program);
}
