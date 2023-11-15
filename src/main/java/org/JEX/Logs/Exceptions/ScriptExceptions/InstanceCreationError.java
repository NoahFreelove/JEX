package org.JEX.Logs.Exceptions.ScriptExceptions;

import org.JEX.Core.Scripting.Script;

public class InstanceCreationError extends JEXception_Script {

    public InstanceCreationError(String message, Class<? extends Script> clazz, String resolution){
        super(message,clazz,resolution);
    }
}
