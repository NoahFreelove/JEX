package org.JEX.Logs.Exceptions.ArgumentExceptions;

public class NullArgumentException extends JEXception_Argument{
    public NullArgumentException(){
        super("Null Argument Exception");
    }
    public NullArgumentException(String msg){
        super("Null Argument Exception:\n-> " + msg);
    }
    public NullArgumentException(String msg, String resolution){
        super("Null Argument Exception:\n-> " + msg,resolution);
    }

    public NullArgumentException(Class<?> type, int index){
        super("Argument Exception with argument:\n-> class: " + type.getSimpleName() + "\n-> argument index:" + index +
                "\n-> value: null");
    }
}
