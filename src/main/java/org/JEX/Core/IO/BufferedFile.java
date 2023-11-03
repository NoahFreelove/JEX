package org.JEX.Core.IO;

import org.JEX.Logs.Exceptions.ArgumentExceptions.NullArgumentException;
import org.JEX.Logs.Exceptions.IOExceptions.DataNotYetLoadedException;
import org.JEX.Logs.Exceptions.IOExceptions.FileReadException;
import org.JEX.Logs.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BufferedFile {
    protected final Filepath filepath;
    private boolean loaded;
    protected byte[] bufferedData;

    public BufferedFile(Filepath filepath) {
        if(filepath == null) {
            Log.error(new NullArgumentException(Filepath.class, 0));
            this.filepath = Filepath.EMPTY_FILEPATH;
            return;
        }
        this.filepath = filepath;
    }

    public BufferedFile(Filepath filepath, boolean autoload) {
        if(filepath == null) {
            Log.error(new NullArgumentException(Filepath.class, 0));
            this.filepath = Filepath.EMPTY_FILEPATH;
            return;
        }

        this.filepath = filepath;

        if(autoload)
            readDataFromFile();
    }

    public void readDataFromFile() {
        File file = filepath.getFile();
        if(file == null)
        {
            Log.error(new FileReadException("Could not read file: File is null.",
                    "Aborted File Read Attempt."));
            return;
        }
        // Load file into byte array
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(fis.readAllBytes())) {
                bufferedData = bis.readAllBytes();
                loaded = true;
            }
        } catch (IOException e) {
            Log.error(new FileReadException("Could not read file: \"" + filepath.getPath() + "\"" + e.getMessage()));
            bufferedData = new byte[0];
            loaded = false;
        }
    }

    public byte[] getData() {
        if(loaded)
            return bufferedData;

        Log.error(new DataNotYetLoadedException("Tried to get data from buffered file before it was loaded.",
                "Returned empty byte array."));
        return new byte[0];
    }

    public String getDataAsString() {
        return new String(getData());
    }
    public String[] getDataAsLines(){
        return getDataAsString().split("\n");
    }

    public boolean hasLoaded(){
        return loaded;
    }

}
