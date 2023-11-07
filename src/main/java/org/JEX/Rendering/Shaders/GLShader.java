package org.JEX.Rendering.Shaders;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Engine.JEX;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.Uniforms.ShaderUniform;
import org.lwjgl.opengl.*;

/**
 * Not called GLSL Shader because its specific to OpenGL. Vulkan can use compiled GLSL shaders.
 */
public non-sealed class GLShader extends ShaderBase{
    private final int shaderTypeInt;
    private int compileID = -1;


    public GLShader(ShaderType type) {
        super(type);
        switch (type){
            case Fragment -> shaderTypeInt = GL46.GL_FRAGMENT_SHADER;
            case Geometry -> shaderTypeInt = GL46.GL_GEOMETRY_SHADER;
            case Compute -> shaderTypeInt = GL46.GL_COMPUTE_SHADER;
            default -> shaderTypeInt = GL46.GL_VERTEX_SHADER;
        }
    }

    public GLShader(ShaderType type, String source) {
        super(type);
        switch (type){
            case Fragment -> shaderTypeInt = GL46.GL_FRAGMENT_SHADER;
            case Geometry -> shaderTypeInt = GL46.GL_GEOMETRY_SHADER;
            case Compute -> shaderTypeInt = GL46.GL_COMPUTE_SHADER;
            default -> shaderTypeInt = GL46.GL_VERTEX_SHADER;
        }
        shaderSourceString = source;
    }

    @Override
    @EngineThread
    void compile() {
        // Compile OpenGL shader with source
        int compiled = GL46.glCreateShader(shaderTypeInt);
        GL46.glShaderSource(compiled, shaderSourceString);
        GL46.glCompileShader(compiled);

        // Check for errors
        if(GL46.glGetShaderi(compiled, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE){
            System.err.println("Failed to compile shader!");
            System.err.println(GL46.glGetShaderInfoLog(compiled));
            return;
        }
        compileID = compiled;
    }


    public int getCompileID(){
        return compileID;
    }
}
