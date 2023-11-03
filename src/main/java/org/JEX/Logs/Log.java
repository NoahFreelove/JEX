package org.JEX.Logs;

import org.JEX.Logs.Exceptions.JEXception;

import static org.JEX.Core.Engine.JEX.active_config;

public class Log {
    public static void error(JEXception exception){
        if(!active_config().log_errors() || !active_config().log_errors())
            return;

        System.err.println(exception.getMessage());
        StackTraceElement[] elms = exception.getStackTrace();
        if(elms.length >=1){
            System.err.println("(@" + elms[0] + ")");
        }
        System.err.println();
    }

    public static void warn(String warning){
        if(active_config().log_warnings() && active_config().enable_logs())
            return;

        System.out.println("WARN: " + warning);
    }

    public static void print(String message){
        if(active_config().enable_logs())
            System.out.println(message);
    }
    public static void print(int message){
        print(String.valueOf(message));
    }
    public static void print(double message){
        print(String.valueOf(message));
    }
    public static void print(boolean message){
        print(String.valueOf(message));
    }
    public static void print(char message){
        print(String.valueOf(message));
    }
    public static void print(Object message){
        print(message.toString());
    }

    public static void debug(String message){
        if(active_config().log_debug() && active_config().enable_logs())
            System.out.println("DEBUG: " + message);
    }
    public static void debug(int message){
        debug(String.valueOf(message));
    }
    public static void debug(double message){
        debug(String.valueOf(message));
    }
    public static void debug(boolean message){
        debug(String.valueOf(message));
    }
    public static void debug(char message){
        debug(String.valueOf(message));
    }
    public static void debug(Object message){
        debug(message.toString());
    }

}
