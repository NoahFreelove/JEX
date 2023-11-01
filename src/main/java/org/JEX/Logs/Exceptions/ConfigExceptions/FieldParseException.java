package org.JEX.Logs.Exceptions.ConfigExceptions;

public class FieldParseException extends JEXception_Config{

    public FieldParseException(String message) {
        super("Field parse error: " + message);
    }
    public FieldParseException(String message, String resolution){
        super("Field parse error: " + message, resolution);
    }
}
