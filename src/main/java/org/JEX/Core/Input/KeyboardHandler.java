package org.JEX.Core.Input;

import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Levels.Level;
import org.JEX.Logs.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyboardHandler extends InputHandler {

    private static boolean[] keys = new boolean[1024];
    private static boolean[] disabled_keys = new boolean[1024];
    private static boolean[] sticky_keys = new boolean[1024];
    private static boolean input_enabled = true;


    public static void onKeyPress(int key){
        if(disabled_keys[key] || !input_enabled){
            return;
        }
        keys[key] = true;
        anyKeyEvent();
    }
    public static void keyRepeat(int key){
        // we don't really care about this because if its down, its down already.
        return;
    }

    public static void onKeyRelease(int key){
        if(sticky_keys[key] || !input_enabled){
            return;
        }
        keys[key] = false;
        anyKeyEvent();
    }

    public static boolean isKeyDown(int key){
        return keys[key];
    }

    public static void disableKey(int key){
        disabled_keys[key] = true;
    }

    public static void enableKey(int key){
        disabled_keys[key] = false;
    }

    public static void enableInput(){
        input_enabled = true;
    }

    public static void disableInput(){
        input_enabled = false;
    }

    public static void stickKey(int key){
        sticky_keys[key] = true;
    }

    public static void unstickKey(int key){
        sticky_keys[key] = false;
    }
}
