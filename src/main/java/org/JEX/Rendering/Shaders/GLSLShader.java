package org.JEX.Rendering.Shaders;

import org.lwjgl.opengl.*;

public non-sealed class GLSLShader extends ShaderBase{
    private int programID = -1;
    private final int shaderTypeInt;

    public GLSLShader(ShaderType type) {
        super(type);
        switch (type){
            case Fragment -> shaderTypeInt = GL46.GL_FRAGMENT_SHADER;
            case Geometry -> shaderTypeInt = GL46.GL_GEOMETRY_SHADER;
            case Compute -> shaderTypeInt = GL46.GL_COMPUTE_SHADER;
            default -> shaderTypeInt = GL46.GL_VERTEX_SHADER;
        }
    }

    @Override
    void compile() {
        // Compile OpenGL shader with source
        programID = GL46.glCreateProgram();
        int compiled = GL46.glCreateShader(shaderTypeInt);
        GL46.glShaderSource(compiled, shaderSourceString);
        GL46.glCompileShader(compiled);

        // Check for errors
        if(GL46.glGetShaderi(compiled, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE){
            System.err.println("Failed to compile shader!");
            System.err.println(GL46.glGetShaderInfoLog(compiled));
            return;
        }

        // Attach shader to program
        GL46.glAttachShader(programID, compiled);
        GL46.glLinkProgram(programID);
        GL46.glValidateProgram(programID);

        // Check for errors
        if(GL46.glGetProgrami(programID, GL46.GL_LINK_STATUS) == GL46.GL_FALSE){
            System.err.println("Failed to link shader!");
            System.err.println(GL46.glGetProgramInfoLog(programID));
            return;
        }
        isValid = true;
    }

    @Override
    protected void destroy() {
        GL46.glDeleteProgram(programID);
    }

    private boolean isValid(){
        return programID >= 0 && isValid;
    }

    @Override
    public void enableShader(){
        if(isValid()){
            GL46.glUseProgram(programID);
        }
    }
}
