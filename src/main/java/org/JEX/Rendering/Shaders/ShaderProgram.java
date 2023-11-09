package org.JEX.Rendering.Shaders;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.Uniforms.EmptyUniform;
import org.JEX.Rendering.Shaders.Uniforms.ShaderUniform;

import java.util.ArrayList;

public abstract class ShaderProgram {
    protected ShaderProgramIdentification identificationModule;
    protected boolean isValid = false;

    protected ArrayList<ShaderUniform<?>> uniforms = new ArrayList<>();
    protected JEXIterator<ShaderUniform<?>> uniformIterator = new JEXIterator<ShaderUniform<?>>(new ShaderUniform[0]);

    @EngineThread
    protected abstract void setUniforms();

    public void addUniform(ShaderUniform<?> uni){
        if(uniforms == null || uniforms.contains(uni))
            return;
        this.uniforms.add(uni);
        uniformIterator = new JEXIterator<ShaderUniform<?>>(uniforms.toArray(new ShaderUniform<?>[0]));
    }

    public void removeUniform(ShaderUniform<?> uni){
        if(uniforms == null || !uniforms.contains(uni))
            return;
        this.uniforms.remove(uni);
        uniformIterator = new JEXIterator<ShaderUniform<?>>(uniforms.toArray(new ShaderUniform<?>[0]));
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

    public abstract boolean isValid();

    @EngineThread
    public abstract void compile();
}
