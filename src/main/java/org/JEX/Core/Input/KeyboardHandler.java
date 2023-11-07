package org.JEX.Core.Input;

import org.JEX.Logs.Log;

public class KeyboardHandler {

    private static boolean[] keys = new boolean[1024];
    private static boolean[] disabled_keys = new boolean[1024];
    private static boolean[] sticky_keys = new boolean[1024];
    private static boolean input_enabled = true;

    public static void onKeyPress(int key){
        if(disabled_keys[key] || !input_enabled){
            return;
        }
        keys[key] = true;
    }

    public static void onKeyRelease(int key){
        if(sticky_keys[key] || !input_enabled){
            return;
        }
        keys[key] = false;
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
