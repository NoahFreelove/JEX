package org.JEX.Rendering.Shaders.Uniforms;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL46;

public class Vector4fUniform extends ShaderUniform<Vector4f> {
    public Vector4fUniform(String name) {
        super(name, new Vector4f(), GraphicsAPI.OpenGL);
    }
    public Vector4fUniform(String name, Vector4f value) {
        super(name, value, JEX.active_config().graphicsAPI());
    }

    public Vector4fUniform(String name, Vector4f value, GraphicsAPI api) {
        super(name, value, api);
    }

    @Override
    @EngineThread
    public void setUniform(int program) {
        if(api == GraphicsAPI.OpenGL){
            int location = GL46.glGetUniformLocation(program, name);
            GL46.glUniform4f(location, value.x, value.y, value.z, value.w);
        }
    }
}
