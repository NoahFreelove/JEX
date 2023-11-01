package org.JEX.Logs.Exceptions.IOExceptions;

public class DataNotYetLoadedException extends JEXception_IO{

    public DataNotYetLoadedException(){
        super("Data Not Yet Loaded Exception");
    }
    public DataNotYetLoadedException(String message){
        super("Data Not Yet Loaded Exception:\n-> " + message);
    }

    public DataNotYetLoadedException(String message, String resolution){
        super("Data Not Yet Loaded Exception:\n-> " + message, resolution);
    }
}
