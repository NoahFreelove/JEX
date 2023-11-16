package org.JEX.Rendering.Shaders.Uniforms;

import org.JEX.Core.Engine.Transform;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Rendering.Camera.Camera;
import org.lwjgl.opengl.GL46;

public class MVPUniform extends ShaderUniform<Camera> {
    int modelLocation = -1;
    int viewLocation = -1;
    int projectionLocation = -1;
    int MVPLocation = -1;

    public MVPUniform(Camera value, GraphicsAPI api) {
        super("MVP", value, api);
    }

    @Override
    public void setUniform(int program) {
        if(value == null)
            return;

        if (api == GraphicsAPI.OpenGL) {
            if(!isLocationValid()){
                modelLocation = GL46.glGetUniformLocation(program, "JEXmodel");
                viewLocation = GL46.glGetUniformLocation(program, "JEXview");
                projectionLocation = GL46.glGetUniformLocation(program, "JEXprojection");
                MVPLocation = GL46.glGetUniformLocation(program, "JEXMVP");
            }
            if(modelLocation != -1)
                GL46.glUniformMatrix4fv(modelLocation, false, value.getModelMatrixBuffer());
            if(viewLocation != -1)
                GL46.glUniformMatrix4fv(viewLocation, false, value.getViewMatrixBuffer());
            if(projectionLocation != -1)
                GL46.glUniformMatrix4fv(projectionLocation, false, value.getProjectionMatrixBuffer());
            if(MVPLocation != -1)
                GL46.glUniformMatrix4fv(MVPLocation, false, value.getMVPMatrixBuffer());
        }
    }

    @Override
    protected boolean isLocationValid() {
        return (modelLocation != -1 && viewLocation != -1 && projectionLocation != -1 && MVPLocation != -1);
    }
}
