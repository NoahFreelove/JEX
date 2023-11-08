package org.JEX.Rendering.Shaders;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.IO.BufferedFile;
import org.JEX.Core.IO.Filepath;
import org.JEX.Logs.Exceptions.IOExceptions.DataNotYetLoadedException;
import org.JEX.Logs.Exceptions.IOExceptions.ResourceDoesntExistException;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;

public abstract class ShaderBase {
    protected String shaderSourceString;
    protected final ShaderType type;

    public ShaderBase(ShaderType type){
        this.type = type;
    }

    @EngineThread
    protected abstract void compile();

    public void loadSourceFromFile(Filepath file){
        if(!file.checkExistence())
        {
            Log.error(new ResourceDoesntExistException("Can't load shader source from file which doesn't exist!", "Aborted loading source."));
            return;
        }
        BufferedFile bf = new BufferedFile(file, true);
        if(!bf.hasLoaded()) {
            Log.error(new DataNotYetLoadedException("Buffered File was unable to buffer file data when access was attempted.", "Aborted loading source."));
            return;
        }
        shaderSourceString = bf.getDataAsString();
    }

    public ShaderType getType(){
        return type;
    }



}
