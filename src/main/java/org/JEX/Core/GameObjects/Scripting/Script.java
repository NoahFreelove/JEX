package org.JEX.Core.GameObjects.Scripting;

import java.util.HashMap;

public class Script implements Save,Load {

    public Script(){}

    public void start(){}
    public void update(float delta_time){}

    @Override
    public void load(HashMap<String, String> data) {

    }

    @Override
    public HashMap<String, String> save() {
        return null;
    }
}
