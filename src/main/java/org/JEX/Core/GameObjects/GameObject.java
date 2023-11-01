package org.JEX.Core.GameObjects;

import org.JEX.Logs.Exceptions.NullException.JEXception_Null;
import org.JEX.Logs.Exceptions.NullException.NullReturnException;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class GameObject {
    private boolean has_renderer;
    private Renderer renderer;


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
}
