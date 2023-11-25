package org.JEX.Core.IO.Resources;

import org.JEX.Core.IO.BufferedFile;
import org.JEX.Core.IO.Filepath;
import org.JEX.Logs.Exceptions.IOExceptions.FileReadException;
import org.JEX.Logs.Log;

import java.util.Arrays;

public abstract class ModelLoader {

    private static boolean loaded = false;

    private static ModelLoader10 loader10;
    private static ModelLoader11 loader11;

    public static Model loadModel(Filepath filepath){
        if(!loaded) initLoaders();

        BufferedFile bf = new BufferedFile(filepath);
        String[] lines = bf.getDataAsLines();
        if(lines.length <1) {
            Log.error(new FileReadException("Could not read model file because it has no lines...: ",
                    "returned empty model"));
            return new Model();
        }

        String version_str = lines[0];
        int VERSION = 0;
        if(version_str.startsWith("version:")){
           try {
               VERSION = Integer.parseInt(version_str.split(":")[1]);
           }catch (NumberFormatException e){
               Log.error(new FileReadException("Could not read model file because version is invalid: ",
                       "returned empty model"));
               return new Model();
           }
        }

        System.arraycopy(lines, 1, lines, 0, lines.length-1);
        return switch (VERSION){
            case 11 -> loader11.onLoad(lines);
            default -> loader10.onLoad(lines);
        };
    }

    protected abstract Model loadModelAbstract(String[] data);

    private static void initLoaders(){
        loader10 = new ModelLoader10();
        loader11 = new ModelLoader11();
        loaded = true;
    }

    protected Model onLoad(String[] lines){
        return loadModelAbstract(lines);
    }
}
