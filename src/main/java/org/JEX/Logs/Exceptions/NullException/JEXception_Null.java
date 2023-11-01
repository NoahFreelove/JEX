package org.JEX.Logs.Exceptions.NullException;

import org.JEX.Logs.Exceptions.JEXception;

public class JEXception_Null extends JEXception {

    public JEXception_Null() {
        super("Unknown Null Exception");
    }

    public JEXception_Null(String msg) {
        super("Null Exception:\n-> " + msg);
    }

    public JEXception_Null(String msg, String resolution) {
        super("Null Exception:\n-> " + msg, resolution);
    }
}
