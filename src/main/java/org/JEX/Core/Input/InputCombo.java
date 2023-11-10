package org.JEX.Core.Input;

public class InputCombo {

    private final int[] keys;
    private final int[] mouseButtons;
    private final float[] weights;
    private int lastKeyPressed;
    private boolean allPressed = false;
    private boolean isAnyPressed = false;

    public InputCombo(int[] keys, int[] mouseButtons){
        this(keys,mouseButtons, new float[0]);
    }

    public InputCombo(int[] keys, int[] mouseButtons, float[] weights){
        this.keys = keys;
        this.mouseButtons = mouseButtons;
        this.weights = weights;
    }

    public InputCombo(int[] keys,float[] weights){
        this(keys, new int[0], weights);
    }

    public InputCombo(int[] keys){
        this(keys, new int[0]);
    }

    public void checkPressed(){
        isAnyPressed = isAnyPressed();
        if(!isAnyPressed){
            return;
        }

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
        this.allPressed = allKeysPressed;
    }

    public boolean isAllPressed(){
        return allPressed;
    }

    public boolean isAnyPressed(){
        for(int key : keys){
            if(KeyboardHandler.isKeyDown(key)){
                return true;
            }
        }

        for (int mouse: mouseButtons){
            if(MouseHandler.isMouseDown(mouse)){
                return true;
            }
        }
        return false;
    }

    public boolean isORPressed() {
        return isAnyPressed;
    }

    public float weight(){
        if(!isAnyPressed){
            return 0;
        }
        if(weights.length != keys.length)
            return 0;

        float total = 0;
        for (int i = 0; i < weights.length; i++) {
            if(KeyboardHandler.isKeyDown(keys[i])){
                total += weights[i];
            }
        }
        return total;
    }
}
