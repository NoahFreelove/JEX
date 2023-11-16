package org.JEX.Core.Scripting;

import org.JEX.Core.Engine.GameObject;
import org.JEX.Core.Engine.Transform;
import org.JEX.Logs.Exceptions.NullException.NullReturnException;
import org.JEX.Logs.Log;

import java.util.HashMap;

/**
 * Idea behind scripts
 * 1. You can only add scripts through instancing classes, never through adding script objects. This is to ensure any
 * created scripts always have a parent. 
 * 2. You can never remove a script, you can however enable or disable it. This is also to make sure the attached
 * value is never null
 * 3. To be modular and not rely on any constructor inputs or other information to function
 * Other than that the idea is that they let you attach any behavior to GameObjects.
 */
public class Script implements Save,Load {

    private boolean enabled = false;
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

    public boolean isEnabled(){
        return enabled && reference != null;
    }

    public void setEnabled(boolean val){
        this.enabled = val;
    }

    public void addToObject(GameObject newParent){
        if(reference != null)
            return;

        this.reference = newParent;
        enabled = true;
    }

    public Transform getTransform(){
        if(enabled)
            return reference.getTransform();
        Log.error(new NullReturnException("Script attached object is null, cannot getTransform()", "returning new transform"));
        return new Transform();
    }

}
