package org.JEX.Logs.Exceptions.IOExceptions;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_IO extends JEXception {
    public JEXception_IO(){
        super("Unknown IO Exception...");
    }
    public JEXception_IO(String msg){
        super("IO Exception:\n-> " + msg);
    }
    public JEXception_IO(String msg, String resolution){
        super("IO Exception:\n-> " + msg,resolution);
    }
}
