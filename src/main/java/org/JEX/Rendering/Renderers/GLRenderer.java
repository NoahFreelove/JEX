package org.JEX.Rendering.Renderers;

import org.JEX.Core.Engine.GameObject;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Logs.Exceptions.ShaderExceptions.JEXception_Shader;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.OpenGL.GLShaderProgram;
import org.JEX.Rendering.Shaders.ShaderProgram;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.lwjgl.opengl.GL46;

import static org.JEX.Logs.Log.print;

public class GLRenderer extends Renderer {
    protected VertexObjectGLWrapper vertexObject;

    private GLShaderProgram castedShaderProgram;

    public GLRenderer() {
        super(GraphicsAPI.OpenGL);
        setShaderProgram(new GLShaderProgram());
    }

    public GLRenderer(GLShader... shaders) {
        super(GraphicsAPI.OpenGL);
        setShaderProgram(new GLShaderProgram(shaders));
        JEX.queueEngineFunction(() -> shaderProgram.compile());
    }

    public void setVertexObject(VertexObjectGLWrapper vertexObject){
        this.vertexObject = vertexObject;
    }

    @Override
    protected void render() {
        if(vertexObject == null)
            return;
        if(!vertexObject.ready() || !shaderProgram.isValid())
            return;

        castedShaderProgram.enableShader();

        vertexObject.bindBuffers();

        setEnables();
        drawArrays();
        if(GL46.glGetError() != 0)
            print("error:" + GL46.glGetError());

        vertexObject.unbindBuffers();

        castedShaderProgram.disableShader();

        //System.out.println("Drawing " + vertexObject.vertLen() + " vertices");
    }

    protected void setEnables(){
        for (int i :
                renderConfig.GL_ENABLES) {
            GL46.glEnable(i);
        }
    }

    private void drawArrays(){
        GL46.glDrawArrays(renderConfig.GL_DRAW_MODE, 0, vertexObject.vertLen());
    }

    public GLShaderProgram getGLShaderProgram(){
        return castedShaderProgram;
    }

    @Override
    public void setShaderProgram(ShaderProgram shaderProgram) {
        if(shaderProgram instanceof GLShaderProgram program) {
            castedShaderProgram = program;
            super.setShaderProgram(shaderProgram);
        }
        else {
            Log.error(new JEXception_Shader("Can't set shader: Shader program is not a GLShaderProgram"));
        }
    }
}
