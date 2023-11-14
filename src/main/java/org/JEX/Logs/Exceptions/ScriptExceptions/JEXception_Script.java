package org.JEX.Logs.Exceptions.ScriptExceptions;

import org.JEX.Core.GameObjects.Scripting.Script;
import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_Script extends JEXception {

    public JEXception_Script() {
        super("Unknown Script Exception");
    }

    public JEXception_Script(String msg, Class<? extends Script> clazz, String res) {
        this("Script Exception for class '" + clazz.getSimpleName() +  "':\n-> " + msg, res);
    }

    public JEXception_Script(String msg, String resolution) {
        super("Script Exception:\n-> " + msg, resolution);
    }
}
