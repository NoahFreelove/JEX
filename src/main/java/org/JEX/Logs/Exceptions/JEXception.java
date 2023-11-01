package org.JEX.Logs.Exceptions;

import org.JEX.Core.Engine.JEX;
import org.JEX.Logs.Exceptions.IOExceptions.JEXception_IO;

// such a clever name
public class JEXception extends Exception{
    public JEXception(){
        super();
    }
    public JEXception(String msg){
        super(msg);
    }
    public JEXception(String msg, String resolution){
        super(msg + "\n-> Resolution:\n    -> " + resolution);
    }
    public JEXception(Exception e){
        super(e);
    }
}
