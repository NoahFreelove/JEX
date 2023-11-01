package org.JEX.Logs.Exceptions.IOExceptions;

public class FileReadException extends JEXception_IO{
    public FileReadException(){
        super("Unknown File Read Exception");
    }
    public FileReadException(String message){
        super("File Read Exception:\n-> " + message);
    }
    public FileReadException(String message, String solution){
        super("File Read Exception:\n-> " +message, solution);
    }

}
