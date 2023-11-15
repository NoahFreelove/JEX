package org.JEX.Core.Engine;

import org.JEX.Core.Scripting.Script;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.HashMap;

public class GameObject {

    JEX.GameObjectInterface object_reference;

    GameObject(JEX.GameObjectInterface object_reference){
        this.object_reference = object_reference;
    }

    public boolean hasRenderer() {
        return object_reference.hasRenderer();
    }

    public Renderer getRenderer(){
        return object_reference.getRenderer();
    }

    private static JEX.GameObjectInterface[] extractFromInterface(GameObject[] interfaces){
        JEX.GameObjectInterface[] output = new JEX.GameObjectInterface[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            output[i] = interfaces[i].object_reference;
        }
        return output;
    }

    public static Renderer[] getBulkRenderers(GameObject[] input){
        return JEX.GameObjectInterface.getBulkRenderers(extractFromInterface(input));
    }


    public HashMap<String, HashMap<String,String>> saveGameObject(){
        return new HashMap<String, HashMap<String,String>>();
    }

    public void loadGameObject(HashMap<String,HashMap<String,String>> input){

    }

    public String getName() {
        return object_reference.getName();
    }

    public String getTag() {
        return object_reference.getTag();
    }

    public int getTagID(){return object_reference.getTagID();}

    public void setName(String name) {
        object_reference.setName(name);
    }

    public void setTag(String name_tag) {
        object_reference.setTag(name_tag);
    }

    public void revokeRenderer(){
        object_reference.revokeRenderer();
    }

    public void start(){
        object_reference.getScripts().forEach(obj -> {
            if(obj.isValid()){
                obj.start();
            }
        });
    }
    public void update(float delta_time){
        object_reference.getScripts().forEach(script -> {
            if(script.isValid())
                script.update(delta_time);
        });
    }

    public void setRenderer(Renderer r){
        object_reference.setRenderer(r);
        r.setAttachedGameObject(this);
    }

    public JEXIterator<Script> getScripts(){
        return object_reference.getScripts();
    }

    public boolean removeScript(int index){
        return object_reference.removeScript(index);
    }

    public boolean removeScript(Script script){
        return object_reference.removeScript(script);
    }

    public void addScript(Class<? extends Script> scriptClass) {
        JEX.instanceScript(scriptClass,this);
    }

    public Transform getTransform(){return object_reference.getTransform();}

    public void setTransform(Transform t){object_reference.setTransform(t);}
}
