package org.JEX.Logs.Exceptions.ConfigExceptions;

public class NoSuchConfigAttributeException extends JEXception_Config{
    public NoSuchConfigAttributeException(String message) {
        super("No Such Config Attribute Exception:\n-> " + message);
    }

}
