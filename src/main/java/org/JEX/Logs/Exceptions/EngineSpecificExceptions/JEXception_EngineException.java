package org.JEX.Logs.Exceptions.EngineSpecificExceptions;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_EngineException extends JEXception {
    public JEXception_EngineException(){
        super("Unknown Engine Exception...");
    }
    public JEXception_EngineException(String msg){
        super("Engine Exception:\n-> " + msg);
    }
    public JEXception_EngineException(String msg, String resolution){
        super("Engine Exception:\n-> " + msg,resolution);
    }
}
