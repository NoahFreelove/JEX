package org.JEX.Core.GameObjects.Scripting;

import org.JEX.Core.GameObjects.GameObject;

import java.util.HashMap;

public class Script implements Save,Load {

    private boolean valid = false;
    private GameObject reference = null;

    protected Script(){}

    public void start(){}
    public void update(float delta_time){}

    @Override
    public void load(HashMap<String, String> data) {

    }

    @Override
    public HashMap<String, String> save() {
        return null;
    }

    public boolean isValid(){
        return valid && reference != null;
    }

    public void addToObject(GameObject newParent){
        this.reference = newParent;
        valid = true;
    }

    public void detach(){
        this.reference = null;
        valid = false;
    }

}
