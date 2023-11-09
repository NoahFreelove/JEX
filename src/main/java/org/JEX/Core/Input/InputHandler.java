package org.JEX.Core.Input;

import java.util.HashMap;

public class InputHandler {

    /**
     * Input Events are much better solution than the JE2 input system of every level adding its own handler to the
     * input system. This is because the JE2 input system would try to trigger events for un-loaded levels.
     */
    protected static HashMap<String,InputCombo> inputEvents = new HashMap<>();

    protected static void anyKeyEvent(){
        inputEvents.forEach((name, combo) -> combo.checkPressed());
    }

    public static boolean isActionPressed(String name){
        return inputEvents.get(name).isPressed();
    }
}
