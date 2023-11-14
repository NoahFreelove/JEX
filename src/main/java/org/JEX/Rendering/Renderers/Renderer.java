package org.JEX.Rendering.Renderers;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.GameObject;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Rendering.Shaders.ShaderProgram;

public abstract class Renderer {
    private final GraphicsAPI api;
    private GameObject objectPointer;

    protected ShaderProgram shaderProgram;

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

    public ShaderProgram getShaderProgram(){
        return shaderProgram;
    }
}
