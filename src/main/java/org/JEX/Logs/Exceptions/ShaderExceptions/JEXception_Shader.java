package org.JEX.Logs.Exceptions.ShaderExceptions;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_Shader extends JEXception {
    public JEXception_Shader(){
        super("Unknown Shader Exception");
    }
    public JEXception_Shader(String message) {
        super("Shader Exception:\n-> " + message);
    }

    public JEXception_Shader(String message, String resolution) {
        super("Shader Exception:\n-> " + message, resolution);
    }
}
