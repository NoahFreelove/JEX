package org.JEX.Logs.Exceptions.NullException;

public class NullReturnException extends JEXception_Null {

        public NullReturnException() {
            super("Unknown Null Return Exception");
        }

        public NullReturnException(String msg) {
            super("Null Return Exception:\n-> " + msg);
        }

        public NullReturnException(String msg, String resolution) {
            super("Null Return Exception:\n-> " + msg, resolution);
        }
}
