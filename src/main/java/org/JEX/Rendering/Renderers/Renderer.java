package org.JEX.Rendering.Renderers;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Rendering.Shaders.ShaderBase;

public abstract class Renderer {
    private final GraphicsAPI api;
    private GameObject objectPointer;

    protected LevelConfig currentLevelConfig;
    protected RenderConfig renderConfig;

    protected ShaderBase vertexShader;
    protected ShaderBase fragmentShader;
    protected ShaderBase geometryShader;
    protected ShaderBase computerShader;

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
