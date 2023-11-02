package org.JEX.Core.Input;

import org.JEX.Core.Annotations.New;
import org.joml.Vector2f;

public class MouseHandler {
    private static boolean[] mouse_buttons = new boolean[8];
    private static boolean[] disabled_mouse_buttons = new boolean[8];
    private static boolean input_enabled = true;
    private static float mouse_x = 0;
    private static float mouse_y = 0;

    public static void onMousePress(int button){
        if(disabled_mouse_buttons[button] || !input_enabled){
            return;
        }
        mouse_buttons[button] = true;
    }

    public static void onMouseRelease(int button){
        if(!input_enabled){
            return;
        }
        mouse_buttons[button] = false;
    }

    public static boolean isMouseDown(int button){
        return mouse_buttons[button];
    }

    public static void disableMouseButton(int button){
        disabled_mouse_buttons[button] = true;
    }

    public static void enableMouseButton(int button){
        disabled_mouse_buttons[button] = false;
    }

    public static void enableInput(){
        input_enabled = true;
    }

    public static void disableInput(){
        input_enabled = false;
    }

    public static void updatePosition(float x, float y){
        mouse_x = x;
        mouse_y = y;
    }

    public static float getMouseX(){
        return mouse_x;
    }

    public static float getMouseY(){
        return mouse_y;
    }

    @New
    public static Vector2f getMousePosition(){
        return new Vector2f(mouse_x, mouse_y);
    }

}
