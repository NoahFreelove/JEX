package org.JEX.Core.GameObjects;

import org.JEX.Core.GameObjects.Scripting.ILambdaScript;
import org.JEX.Core.GameObjects.Scripting.LambdaScript;
import org.JEX.Core.GameObjects.Scripting.Script;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Logs.Exceptions.ArgumentExceptions.JEXception_Argument;
import org.JEX.Logs.Exceptions.NullException.NullReturnException;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.ArrayList;
import java.util.HashMap;

public class GameObject {
    private boolean has_renderer;
    private Renderer renderer;

    private String name = "GameObject";
    private String name_tag = "untagged";
    private int tag;
    private final int mem_ID;

    private Script[] scripts;

    public GameObject() {
        mem_ID = this.hashCode();
        tag = name_tag.hashCode();
        scripts = new Script[0];
    }

    public boolean hasRenderer() {
        return has_renderer;
    }

    public Renderer getRenderer(){
        if(!has_renderer)
        {
            Log.error(new NullReturnException("GameObject doesn't have a renderer: " +
                    "Check if the GameObject has a renderer before calling this method", "Returned null."));
            return null;
        }

        return renderer;
    }

    public static Renderer[] getBulkRenderers(GameObject[] input){
        ArrayList<Renderer> renderers = new ArrayList<>();
        for(GameObject object : input){
            if(object.has_renderer){
                renderers.add(object.getRenderer());
            }
        }
        return renderers.toArray(new Renderer[0]);
    }


    public HashMap<String, HashMap<String,String>> saveGameObject(){
        return new HashMap<String, HashMap<String,String>>();
    }

    public void loadGameObject(HashMap<String,HashMap<String,String>> input){

    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return name_tag;
    }

    public int getTagID(){return tag;}

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String name_tag) {
        this.name_tag = name_tag;
        this.tag = this.name_tag.hashCode();
    }
    public boolean addScript(ILambdaScript lambdaScript){
        return addScript(new LambdaScript(lambdaScript));
    }

    public boolean addScript(Script script){
        if(script == null){
            Log.error(new NullReturnException("Cannot add null Script", "Aborted Script Add..."));
            return false;
        }
        // Increase the size of the array
        Script[] new_scripts = new Script[scripts.length + 1];
        // Copy the old array into the new array
        System.arraycopy(scripts, 0, new_scripts, 0, scripts.length);
        // Add the new script to the end of the new array
        new_scripts[scripts.length] = script;
        // Set the new array as the scripts array
        scripts = new_scripts;
        return true;
    }

    public boolean removeScript(int index){
        if(index>= scripts.length || index<0){
            Log.error(new JEXception_Argument(Integer.class, 0, index, "Index out of bound to remove script."));
            return false;
        }
        // Decrease the size of the array
        Script[] new_scripts = new Script[scripts.length - 1];
        // Copy the old array into the new array
        System.arraycopy(scripts, 0, new_scripts, 0, index);
        // Copy the rest of the old array into the new array
        System.arraycopy(scripts, index+1, new_scripts, index, scripts.length - index - 1);
        // Set the new array as the scripts array
        scripts = new_scripts;
        return true;
    }

    public boolean removeScript(Script script){
        for (int i = 0; i < scripts.length; i++) {
            if(script == scripts[i])
            {
                return  removeScript(i);
            }
        }
        return false;
    }

    public JEXIterator<Script> getScripts(){
        return new JEXIterator<>(scripts);
    }

    public void start(){
        getScripts().forEach(Script::start);
    }
    public void update(float delta_time){
        getScripts().forEach(script -> {
            script.update(delta_time);
        });
    }

    public void revokeRenderer(){
        this.renderer = null;
    }
}
