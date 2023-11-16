package org.JEX.Logs.Exceptions.ShaderExceptions;

public class CantFindUniformException extends JEXception_Shader {
    public CantFindUniformException(String name) {
        super("Could not find uniform: " + name);
    }
}
