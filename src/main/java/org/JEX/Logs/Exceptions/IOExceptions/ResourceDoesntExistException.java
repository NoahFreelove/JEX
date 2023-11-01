package org.JEX.Logs.Exceptions.IOExceptions;

public class ResourceDoesntExistException extends JEXception_IO{

    public ResourceDoesntExistException(){
        super("Unknown Resource Doesn't Exist Exception");
    }
    public ResourceDoesntExistException(String message) {
        super("Resource Doesn't Exist Exception:\n-> " + message);
    }

    public ResourceDoesntExistException(String message, String solution) {
        super("Resource Doesn't Exist Exception:\n-> " +message, solution);
    }
}
