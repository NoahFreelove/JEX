package org.JEX.Logs.Exceptions.ConfigExceptions;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_Config extends JEXception {
    public JEXception_Config(){
        super("Unknown Config Exception...");
    }
    public JEXception_Config(String msg){
        super("Config Exception:\n-> " + msg);
    }
    public JEXception_Config(String msg, String resolution){
        super("Config Exception:\n-> " + msg,resolution);
    }
}
