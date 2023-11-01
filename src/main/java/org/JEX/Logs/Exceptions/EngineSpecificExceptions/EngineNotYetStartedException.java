package org.JEX.Logs.Exceptions.EngineSpecificExceptions;

public class EngineNotYetStartedException extends JEXception_EngineException {
    public EngineNotYetStartedException(){
        super("Unknown Engine Not Yet Started Exception");
    }
    public EngineNotYetStartedException(String message) {
        super("Engine Not Yet Started Exception:\n-> " + message);
    }

    public EngineNotYetStartedException(String message, String solution) {
        super("Engine Not Yet Started Exception:\n-> " +message, solution);
    }
}
