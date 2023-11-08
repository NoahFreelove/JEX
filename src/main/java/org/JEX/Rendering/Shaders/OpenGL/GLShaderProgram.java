package org.JEX.Rendering.Shaders.OpenGL;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.ShaderType;
import org.JEX.Rendering.Shaders.Uniforms.EmptyUniform;
import org.JEX.Rendering.Shaders.Uniforms.ShaderUniform;
import org.lwjgl.opengl.GL46;

import java.util.ArrayList;

public class GLShaderProgram {
    private int programID = -1;
    private ArrayList<ShaderUniform<?>> uniforms = new ArrayList<>();
    protected JEXIterator<ShaderUniform<?>> uniformIterator = new JEXIterator<ShaderUniform<?>>(new ShaderUniform[0]);
    protected boolean isValid = false;

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

    public boolean isValid(){
        return programID >= 0 && isValid;
    }

    public void enableShader(){
        if(isValid()){
            GL46.glUseProgram(programID);
            setUniforms();
        }
    }

    @EngineThread
    protected void setUniforms(){
        uniformIterator.reset();
        uniformIterator.forEach(ShaderUniform::setUniform);
    }

    public void addUniform(ShaderUniform<?> uni){
        if(uniforms == null || uniforms.contains(uni))
            return;
        this.uniforms.add(uni);
        uniformIterator = new JEXIterator<ShaderUniform<?>>(uniforms.toArray(new ShaderUniform[0]));
    }

    public void removeUniform(ShaderUniform<?> uni){
        if(uniforms == null || !uniforms.contains(uni))
            return;
        this.uniforms.remove(uni);
        uniformIterator = new JEXIterator<ShaderUniform<?>>(uniforms.toArray(new ShaderUniform[0]));
    }

    public ShaderUniform<?> getUniform(String name){
        for (ShaderUniform<?> uniform :
                uniforms) {
            if (uniform.getName().equals(name)) {
                return uniform;
            }
        }
        Log.warn("Returned Empty Uniform from getUniform(" + name + ") because no uniform with that name was found.");
        return new EmptyUniform();
    }

    public ShaderUniform<?> getUniform(int index){
        if(index>=0 && index < uniforms.size()){
            return uniforms.get(index);
        }
        Log.warn("Returned Empty Uniform from getUniform(" + index + ") because that index is out of range.");
        return new EmptyUniform();
    }
    protected void destroy() {
        GL46.glDeleteProgram(programID);
    }

    @EngineThread
    public void compile(){
        programID = GL46.glCreateProgram();

        if(vertexShader != null){
            vertexShader.compile();
            GL46.glAttachShader(programID, vertexShader.getCompileID());
        }
        if(fragmentShader != null){
            fragmentShader.compile();
            GL46.glAttachShader(programID, fragmentShader.getCompileID());
        }
        if(geometryShader != null){
            geometryShader.compile();
            GL46.glAttachShader(programID, geometryShader.getCompileID());
        }
        if(computerShader != null){
            computerShader.compile();
            GL46.glAttachShader(programID, computerShader.getCompileID());
        }

        GL46.glLinkProgram(programID);
        GL46.glValidateProgram(programID);

        if(GL46.glGetProgrami(programID, GL46.GL_LINK_STATUS) == GL46.GL_FALSE){
            System.err.println("Failed to link shader program!");
            System.err.println(GL46.glGetProgramInfoLog(programID));
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

}
