package org.JEX.Logs.Exceptions.ConfigExceptions;

public class UnknownConfigTypeException extends JEXception_Config{

    public UnknownConfigTypeException(){
        super("Unknown Config Type Exception");
    }
    public UnknownConfigTypeException(String message) {
        super("Config Type Exception:\n-> " + message);
    }

    public UnknownConfigTypeException(String message, String solution) {
        super("Config Type Exception:\n-> " +message, solution);
    }
}
