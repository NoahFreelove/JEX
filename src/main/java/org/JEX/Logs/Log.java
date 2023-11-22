package org.JEX.Logs;

import org.JEX.Logs.Exceptions.JEXception;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.util.Arrays;

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
        if(!active_config().log_warnings() || !active_config().enable_logs())
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
    public static void print(Object[] message){
        print(Arrays.toString(message));
    }
    public static void print(Vector3f message){
        print("(" + message.x() + ", " + message.y() + ", " + message.z() + ")");
    }
    public static void print(Matrix4f mat){
        print("Matrix4f(");
        print("    " + mat.m00() + ", " + mat.m01() + ", " + mat.m02() + ", " + mat.m03());
        print("    " + mat.m10() + ", " + mat.m11() + ", " + mat.m12() + ", " + mat.m13());
        print("    " + mat.m20() + ", " + mat.m21() + ", " + mat.m22() + ", " + mat.m23());
        print("    " + mat.m30() + ", " + mat.m31() + ", " + mat.m32() + ", " + mat.m33());
        print(")");
    }

    public static void print(FloatBuffer fb){
        print("FloatBuffer(");
        for (int i = 0; i < fb.capacity(); i++) {
            print("    " + fb.get(i));
        }
        print(")");
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
