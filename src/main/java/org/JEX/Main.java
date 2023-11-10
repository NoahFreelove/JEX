package org.JEX;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Core.GameObjects.Scripting.ILambdaScript;
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
import org.JEX.Rendering.Renderers.GLRenderer;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.ShaderType;
import org.JEX.Rendering.Shaders.Uniforms.Vector4fUniform;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.joml.Vector4f;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // TODO: Resource Manager
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config, true);

        World world = new World(new LevelConfig("World1", LevelType.THIRD_DIMENSIONAL));
        instance.changeLevel(world);

        GameObject triangle_object = new GameObject();
        Model triangle = ModelLoader.loadModel(new Filepath("triangle.model", FilepathType.ClassLoader));
        GLRenderer renderer = getGlRenderer(triangle);
        triangle_object.setRenderer(renderer);
        world.add(triangle_object);

        GameObject square_object = new GameObject();
        Model square = ModelLoader.loadModel(new Filepath("square.model", FilepathType.ClassLoader));
        GLRenderer renderer_square = getGlRenderer(square);
        square_object.setRenderer(renderer_square);
        world.add(square_object);

        renderer.getGLShaderProgram().addUniform(new Vector4fUniform("color", new Vector4f(1, 0, 0, 1)));
        renderer_square.getGLShaderProgram().addUniform(new Vector4fUniform("color", new Vector4f(0, 0.5f, 0.5f, 1)));

        InputCombo horiz = new InputCombo(new int[]{ITC.keyCode("A"), ITC.keyCode("D")}, new float[]{-1,1});
        InputCombo vert = new InputCombo(new int[]{ITC.keyCode("W"), ITC.keyCode("S")}, new float[]{1,-1});
        InputHandler.addAction("Horizontal", horiz);
        InputHandler.addAction("Vertical", vert);
        
        square_object.addScript(new ILambdaScript() {
            @Override
            public void update(float delta_time) {
                if(horiz.isAnyPressed())
                    Log.print(horiz.weight());
                if(vert.isAnyPressed())
                    Log.print(vert.weight());
            }
        });
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