package org.JEX.Rendering.Shaders.Uniforms;

import org.JEX.Core.Engine.Transform;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Camera.Camera;
import org.lwjgl.opengl.GL46;

public class MVPUniform extends ShaderUniform<Transform> {
    int modelLocation = -1;
    int viewLocation = -1;
    int projectionLocation = -1;
    int MVPLocation = -1;
    private Camera projection;

    public MVPUniform(Transform value, GraphicsAPI api) {
        super("MVP", value, api);
    }

    @Override
    public void setUniform(int program) {
        if(projection == null || value == null)
            return;

        if (api == GraphicsAPI.OpenGL) {
            if(!isLocationValid()){
                modelLocation = GL46.glGetUniformLocation(program, "JEXmodel");
                viewLocation = GL46.glGetUniformLocation(program, "JEXview");
                projectionLocation = GL46.glGetUniformLocation(program, "JEXprojection");
                MVPLocation = GL46.glGetUniformLocation(program, "JEXMVP");
            }
            if(modelLocation != -1)
                GL46.glUniformMatrix4fv(modelLocation, false, projection.getModelMatrixBuffer(value));
            if(viewLocation != -1)
                GL46.glUniformMatrix4fv(viewLocation, false, projection.getViewMatrixBuffer());
            if(projectionLocation != -1)
                GL46.glUniformMatrix4fv(projectionLocation, false, projection.getProjectionMatrixBuffer());
            if(MVPLocation != -1)
            {
                GL46.glUniformMatrix4fv(MVPLocation, false, projection.getMVPMatrixBuffer(value));
            }
        }
    }

    public void setCamera(Camera cam){
        this.projection = cam;
    }

    @Override
    protected boolean isLocationValid() {
        return (modelLocation != -1 && viewLocation != -1 && projectionLocation != -1 && MVPLocation != -1);
    }
}
