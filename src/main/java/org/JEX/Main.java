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
import org.JEX.Core.Levels.World;
import org.JEX.Core.Levels.LevelType;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Renderers.GLRenderer;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.ShaderType;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.joml.Vector2f;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // TODO: Resource Manager
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config, true);

        World world = new World(new LevelConfig("World1", LevelType.THIRD_DIMENSIONAL));
        instance.changeLevel(world);

        GameObject object = new GameObject();
        object.addScript(new ILambdaScript() {
            @Override
            public void update(float delta_time) {
                Log.debug("HI!!!: " + delta_time);
            }
        });

        Model m = ModelLoader.loadModel(new Filepath("triangle.model", FilepathType.ClassLoader));
        GLRenderer renderer = getGlRenderer(m);

        object.setRenderer(renderer);

        world.add(object);


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