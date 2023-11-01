package org.JEX.Logs.Exceptions.GLExceptions;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_GL extends JEXception {
    public JEXception_GL(){
        super("Unknown GL Exception");
    }
    public JEXception_GL(String message) {
        super("GL Exception:\n-> " + message);
    }

    public JEXception_GL(String message, String resolution) {
        super("GL Exception:\n-> " + message, resolution);
    }
}
