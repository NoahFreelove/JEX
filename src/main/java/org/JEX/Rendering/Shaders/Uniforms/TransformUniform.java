package org.JEX.Rendering.Shaders.Uniforms;

import org.JEX.Core.Engine.Transform;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Logs.Log;
import org.lwjgl.opengl.GL46;

public class TransformUniform extends ShaderUniform<Transform> {
    int posLocation = -1;
    int rotLocation = -1;
    int scaleLocation = -1;
    public TransformUniform(Transform value, GraphicsAPI api) {
        super("Transform", value, api);
    }

    @Override
    public void setUniform(int program) {
        if (api == GraphicsAPI.OpenGL) {
            if(!isLocationValid()){
                posLocation = GL46.glGetUniformLocation(program, "JEXpos");
                rotLocation = GL46.glGetUniformLocation(program, "JEXrot");
                scaleLocation = GL46.glGetUniformLocation(program, "JEXscale");
            }
            if(posLocation != -1)
                GL46.glUniform3f(posLocation, value.position().x(), value.position().y(), value.position().z());
            if(rotLocation != -1)
                GL46.glUniform4f(rotLocation, value.rotation().x(), value.rotation().y(), value.rotation().z(), value.rotation().w());
            if(scaleLocation != -1)
                GL46.glUniform3f(scaleLocation, value.scale().x(), value.scale().y(), value.scale().z());
        }
    }

    @Override
    protected boolean isLocationValid() {
        return (posLocation != -1 && rotLocation != -1 && scaleLocation != -1);
    }
}
