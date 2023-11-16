package org.JEX.Rendering.Shaders.OpenGL;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Rendering.Shaders.ShaderProgramIdentification;
import org.JEX.Rendering.Shaders.ShaderProgram;
import org.JEX.Rendering.Shaders.ShaderType;
import org.lwjgl.opengl.GL46;

public class GLShaderProgram extends ShaderProgram {

    protected GLShader vertexShader;
    protected GLShader fragmentShader;
    protected GLShader geometryShader;
    protected GLShader computerShader;

    private int vertexLocation = 0;
    private int normalLocation = 1;
    private int uvLocation = 2;

    public GLShaderProgram(){
        vertexShader = new GLShader(ShaderType.Vertex);
        fragmentShader = new GLShader(ShaderType.Fragment);
        geometryShader = new GLShader(ShaderType.Geometry);
        computerShader = new GLShader(ShaderType.Compute);
    }

    public GLShaderProgram(GLShader... shaders) {

        for (GLShader shader :
                shaders) {
            if (shader.getType() == ShaderType.Vertex && vertexShader == null) {
                vertexShader = shader;
            }
            else if (shader.getType() == ShaderType.Fragment && fragmentShader == null) {
                fragmentShader = shader;
            }
            else if (shader.getType() == ShaderType.Geometry && geometryShader == null) {
                geometryShader = shader;
            }
            else if (shader.getType() == ShaderType.Compute && computerShader == null) {
                computerShader = shader;
            }
        }
    }

    @Override
    public boolean isValid(){
        return programID() >= 0 && isValid;
    }

    public void enableShader(){
        if(isValid()){
            GL46.glUseProgram(programID());
            setUniforms();
        }
    }

    protected void destroy() {
        GL46.glDeleteProgram(programID());
    }

    @EngineThread
    @Override
    public void compile(){
        identificationModule = new ShaderProgramIdentification(GL46.glCreateProgram());


        if(vertexShader != null){
            vertexShader.compile();
            GL46.glAttachShader(programID(), vertexShader.getCompileID());
        }
        if(fragmentShader != null){
            fragmentShader.compile();
            GL46.glAttachShader(programID(), fragmentShader.getCompileID());
        }
        if(geometryShader != null){
            geometryShader.compile();
            GL46.glAttachShader(programID(), geometryShader.getCompileID());
        }
        if(computerShader != null){
            computerShader.compile();
            GL46.glAttachShader(programID(), computerShader.getCompileID());
        }

        GL46.glLinkProgram(programID());
        GL46.glValidateProgram(programID());

        if(GL46.glGetProgrami(programID(), GL46.GL_LINK_STATUS) == GL46.GL_FALSE){
            System.err.println("Failed to link shader program!");
            System.err.println(GL46.glGetProgramInfoLog(programID()));
            return;
        }
        isValid = true;
    }



    public int getVertexLocation() {
        return vertexLocation;
    }

    public void setDefaultVertexLocation(int vertexLocation) {
        this.vertexLocation = vertexLocation;
    }

    public int getNormalLocation() {
        return normalLocation;
    }

    public void setDefaultNormalLocation(int normalLocation) {
        this.normalLocation = normalLocation;
    }

    public int getUvLocation() {
        return uvLocation;
    }

    public void setDefaultUvLocation(int uvLocation) {
        this.uvLocation = uvLocation;
    }

    @Override
    protected void setUniforms() {
        uniformIterator.reset();
        uniformIterator.forEach((uni) -> uni.setUniform(programID()));
    }

    private int programID(){
        return (identificationModule == null)? -1 : identificationModule.getGL_PROGRAM();
    }
}
