package org.JEX.Core.IO;

import org.JEX.Logs.Exceptions.ArgumentExceptions.NullArgumentException;
import org.JEX.Logs.Exceptions.IOExceptions.ResourceDoesntExistException;
import org.JEX.Logs.Log;

import java.io.File;
import java.net.URL;

public class Filepath {
    public static final Filepath EMPTY_FILEPATH = new Filepath("");
    private final String path;
    private final FilepathType type;
    private boolean fileExists;
    private File actualFile;

    public Filepath(String path) {
        if(path == null){
            Log.error(new NullArgumentException(String.class, 0));
            this.path = "";
            this.type = FilepathType.Undefined;
            fileExists = false;
            return;
        }
        this.path = path;
        type = FilepathType.Absolute;
        fileExists = checkExistence();
    }

    public Filepath(String path, FilepathType type) {
        if(path == null){
            Log.error(new NullArgumentException(String.class, 0));
            this.path = "";
            this.type = FilepathType.Undefined;
            fileExists = false;
            return;
        }

        this.type = type;
        if(type == FilepathType.ClassLoader && path.charAt(0) != '/'){
            this.path = "/" + path;
        }
        else{
            this.path = path;
        }
        fileExists = checkExistence();
    }

    /**
     * Checks the existence of a file according to its filepath
     * Used as validation before loading files.
     * <ul>
     *     <li>For <strong>Absolute Paths</strong> its simple to just use the Java built-in File class to check</li>
     *     <li>For <strong>Class Loader or Relative Paths</strong>, it's a bit more complicated. But class loader is more sustainable
     *     than absolute paths as they follow your project on every instance.
     *     </li>
     * </ol>
     *
     * @return true if the file exists, false if not.
     */
    public boolean checkExistence(){
        if(actualFile != null)
            return actualFile.exists();

        if(type == FilepathType.Absolute){
            actualFile = new File(path);
            fileExists = actualFile.exists();
            return fileExists;
        }
        else if (type == FilepathType.ClassLoader){
            URL resourceURL = Filepath.class.getResource(path);
            if (resourceURL == null){
                Log.error(new ResourceDoesntExistException("Resource: " + path + " doesn't exist.","Filepath File reference set to null."));
                actualFile = null;
                fileExists = false;
                return false;
            }
            else{
                actualFile = new File(resourceURL.getPath());
                fileExists = actualFile.exists();
                return fileExists;
            }
        }

        return false;
    }

    public File getFile(){
        if(checkExistence()){
            return actualFile;
        }
        else{
            Log.error(new ResourceDoesntExistException("File: \"" + path + "\" doesn't exist.","Filepath getFile() returning null"));
            return null;
        }
    }

    public String getPath() {
        return path;
    }

    public FilepathType getType() {
        return type;
    }
}
