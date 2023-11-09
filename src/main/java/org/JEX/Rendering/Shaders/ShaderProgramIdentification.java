package org.JEX.Rendering.Shaders;

public class ShaderProgramIdentification {
    private int GL_PROGRAM;

    public ShaderProgramIdentification() {
        this(-1);
    }

    public ShaderProgramIdentification(int GL_PROGRAM) {
        this.GL_PROGRAM = GL_PROGRAM;
    }

    public int getGL_PROGRAM() {
        return GL_PROGRAM;
    }
}
