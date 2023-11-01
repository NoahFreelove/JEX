package org.JEX.Logs.Exceptions.ArgumentExceptions;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_Argument extends JEXception {
    public JEXception_Argument(){
        super("Unknown Argument Exception");
    }
    public JEXception_Argument(String msg){
        super("Unknown Argument Exception:\n-> " + msg);
    }
    public JEXception_Argument(Class<?> type, int index, Object val){
        super("Argument Exception with argument:\n-> class: " + type.getSimpleName() + "\n-> argument index:" + index +
                "\n-> value: " + val.toString());
    }
}
