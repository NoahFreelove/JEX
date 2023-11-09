package org.JEX.Core.Input;

public class InputCombo {

    private final int[] keys;
    private final int[] mouseButtons;
    public final String name;
    private boolean pressed = false;

    public InputCombo(int[] keys, int[] mouseButtons, String name){
        this.keys = keys;
        this.mouseButtons = mouseButtons;
        this.name = name;
    }

    public InputCombo(int[] keys, String name){
        this(keys, new int[0], name);
    }

    public void checkPressed(){
        boolean allKeysPressed = true;
        for(int key : keys){
            if(!KeyboardHandler.isKeyDown(key)){
                allKeysPressed = false;
                break;
            }
        }

        for (int mouse: mouseButtons){
            if(!MouseHandler.isMouseDown(mouse)){
                allKeysPressed = false;
                break;
            }
        }
        this.pressed = allKeysPressed;
    }

    public boolean isPressed(){
        return pressed;
    }
}
