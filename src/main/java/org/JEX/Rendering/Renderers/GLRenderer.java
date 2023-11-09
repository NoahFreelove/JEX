package org.JEX.Rendering.Renderers;

import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.OpenGL.GLShaderProgram;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.lwjgl.opengl.GL46;

import static org.JEX.Logs.Log.print;

public class GLRenderer extends Renderer {
    protected VertexObjectGLWrapper vertexObject;

    protected int draw_mode = GL46.GL_TRIANGLE_FAN;

    private GLShaderProgram castedShaderProgram;

    public GLRenderer() {
        super(GraphicsAPI.OpenGL);
        shaderProgram = new GLShaderProgram();
        castedShaderProgram = (GLShaderProgram) shaderProgram;
    }

    public GLRenderer(GLShader... shaders) {
        super(GraphicsAPI.OpenGL);
        shaderProgram = new GLShaderProgram(shaders);
        castedShaderProgram = (GLShaderProgram) shaderProgram;
        JEX.getInstance().queueEngineFunction(() -> shaderProgram.compile());
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

        drawArrays();
        if(GL46.glGetError() != 0)
            print("error:" + GL46.glGetError());

        vertexObject.unbindBuffers();

        //System.out.println("Drawing " + vertexObject.vertLen() + " vertices");
    }

    private void drawArrays(){
        GL46.glDrawArrays(draw_mode, 0, vertexObject.vertLen());

    }

    public GLShaderProgram getGLShaderProgram(){
        return castedShaderProgram;
    }

}
