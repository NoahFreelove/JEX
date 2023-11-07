package org.JEX.Rendering.Renderers;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Rendering.Shaders.ShaderBase;
import org.JEX.Rendering.VertexUtil.VertexObject;

import static org.JEX.Logs.Log.print;

public abstract class Renderer {
    private final GraphicsAPI api;
    private GameObject objectPointer;

    protected LevelConfig currentLevelConfig;
    protected RenderConfig renderConfig;

    public Renderer(GraphicsAPI api){
        this.api = api;
    }

    public void render(LevelConfig config){
        this.currentLevelConfig = config;
        render();
    }

    protected abstract void render();

    protected GameObject getPointer(){
        return objectPointer;
    }

    public void setAttachedGameObject(GameObject object){
        if(this.objectPointer != null){
            this.objectPointer.revokeRenderer();
        }
        this.objectPointer = object;
    }

}
