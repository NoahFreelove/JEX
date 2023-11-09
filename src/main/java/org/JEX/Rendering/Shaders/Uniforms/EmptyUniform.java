package org.JEX.Rendering.Shaders.Uniforms;

import org.JEX.Core.Engine.Window.GraphicsAPI;

public class EmptyUniform extends ShaderUniform<Object>{
    public EmptyUniform() {
        super("Empty Uniform", new Object(), GraphicsAPI.Unknown);
    }

    @Override
    public void setUniform(int program) {

    }
}
