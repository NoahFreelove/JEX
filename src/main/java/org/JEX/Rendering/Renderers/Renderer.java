package org.JEX.Rendering.Renderers;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.GameObject;
import org.JEX.Core.Engine.Transform;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Shaders.ShaderProgram;
import org.JEX.Rendering.Shaders.Uniforms.MVPUniform;
import org.JEX.Rendering.Shaders.Uniforms.TransformUniform;

public abstract class Renderer {
    private final GraphicsAPI api;
    private GameObject objectPointer;

    protected ShaderProgram shaderProgram;

    protected LevelConfig currentLevelConfig;
    protected RenderConfig renderConfig = new RenderConfig();

    private TransformUniform transformUniform;
    private MVPUniform mvpUniform;
    private boolean addedDefaultUniforms = false;
    public Renderer(GraphicsAPI api){
        this.api = api;
    }

    public void render(LevelConfig config){
        this.currentLevelConfig = config;
        if(config.getActiveCamera() == null)
        {
            Log.warn("Aborted rendering because there is no active camera!");
            return;
        }
        if(addedDefaultUniforms){
            transformUniform.setValue(config.getActiveCamera().getTransform());
            mvpUniform.setValue(objectPointer.getTransform());
            mvpUniform.setCamera(config.getActiveCamera());
        }
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
        addDefaultUniforms();
    }

    public ShaderProgram getShaderProgram(){
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram){
        this.shaderProgram = shaderProgram;
        addDefaultUniforms();
    }

    private void addDefaultUniforms(){
        if(shaderProgram != null){
            if(transformUniform != null)
                shaderProgram.removeUniform(transformUniform);

            transformUniform = new TransformUniform(new Transform(), api);
            shaderProgram.addUniform(transformUniform);


            if(mvpUniform != null)
                shaderProgram.removeUniform(mvpUniform);

            mvpUniform = new MVPUniform(null, api);
            shaderProgram.addUniform(mvpUniform);

            addedDefaultUniforms = true;
        }
        else{
            addedDefaultUniforms = false;
        }
    }

    public void setRenderConfig(RenderConfig config){
        this.renderConfig = config;
    }

    public RenderConfig getRenderConfig(){
        return renderConfig;
    }
}
