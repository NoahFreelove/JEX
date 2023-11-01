package org.JEX.Logs.Exceptions.ConfigExceptions;

public class InaccessibleAttributeException extends JEXception_Config{
    public InaccessibleAttributeException(String message) {
        super("Inaccessible Attribute Exception:\n-> " + message);
    }
}
