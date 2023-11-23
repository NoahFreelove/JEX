package org.JEX;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.GameObject;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Scripting.ILambdaScript;
import org.JEX.Core.IO.Filepath;
import org.JEX.Core.IO.FilepathType;
import org.JEX.Core.IO.Resources.Model;
import org.JEX.Core.IO.Resources.ModelLoader;
import org.JEX.Core.Input.ITC;
import org.JEX.Core.Input.InputCombo;
import org.JEX.Core.Input.InputHandler;
import org.JEX.Core.Levels.World;
import org.JEX.Core.Levels.LevelType;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Camera.Camera;
import org.JEX.Rendering.Camera.ProjectionCamera;
import org.JEX.Rendering.Renderers.GLRenderer;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.ShaderType;
import org.JEX.Rendering.Shaders.Uniforms.Vector4fUniform;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.joml.Vector4f;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // TODO: Resource Manager
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config, true);

        GameObject cameraMan = JEX.createGameObject().addScript(ProjectionCamera.class);
        Camera cam = (Camera) cameraMan.getScript(ProjectionCamera.class);
        cameraMan.getTransform().translate(0,0,10);

        World world = new World(new LevelConfig("World1", LevelType.THIRD_DIMENSIONAL, cam));
        instance.changeLevel(world);

        GameObject triangle_object = JEX.createGameObject();
        Model triangle = ModelLoader.loadModel(new Filepath("triangle.model", FilepathType.ClassLoader));
        GLRenderer renderer = getGlRenderer(triangle);
        triangle_object.setRenderer(renderer);
        world.add(triangle_object);
        world.add(cameraMan);

        renderer.getGLShaderProgram().addUniform(new Vector4fUniform("color", new Vector4f(0.5f, 0.5f, 0, 1)));

        InputCombo xAxis = new InputCombo(new int[]{ITC.keyCode("A"), ITC.keyCode("D")}, new float[]{-1,1});
        InputCombo zAxis = new InputCombo(new int[]{ITC.keyCode("W"), ITC.keyCode("S")}, new float[]{1,-1});
        InputCombo yAxis = new InputCombo(new int[]{ITC.keyCode("Q"), ITC.keyCode("E")}, new float[]{-1,1});

        InputHandler.addAction("Horizontal", xAxis);
        InputHandler.addAction("ZAxis", zAxis);
        InputHandler.addAction("Vertical", yAxis);
        
        JEX.addLambdaScript(new ILambdaScript() {
            @Override
            public void update(float delta_time) {
                if(xAxis.isAnyPressed())
                    cameraMan.getTransform().translate(xAxis.weight()*0.1f, 0, 0);
                if(zAxis.isAnyPressed())
                    cameraMan.getTransform().translate(0, 0, -zAxis.weight()*0.1f);
                if(yAxis.isAnyPressed())
                    cameraMan.getTransform().translate(0, yAxis.weight()*0.1f, 0);
            }
        }, triangle_object);
    }

    private static GLRenderer getGlRenderer(Model m) {
        VertexObjectGLWrapper wrapper = new VertexObjectGLWrapper(m);
        wrapper.queueBuffer();

        GLShader vertexShader = new GLShader(ShaderType.Vertex, new Filepath("vertex.glsl", FilepathType.ClassLoader));
        GLShader fragmentShader = new GLShader(ShaderType.Fragment, new Filepath("fragment.glsl", FilepathType.ClassLoader));
        GLRenderer renderer = new GLRenderer(vertexShader, fragmentShader);

        renderer.setVertexObject(wrapper);
        return renderer;
    }
}