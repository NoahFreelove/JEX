package org.JEX.Logs.Exceptions.IOExceptions;

public class ParseException extends JEXception_IO{

    public ParseException(String message){
        super("Unknown Parse Error: " + message);
    }

    public ParseException(String message, String resolution){
        super("Parse Error: " + message, resolution);
    }
}
