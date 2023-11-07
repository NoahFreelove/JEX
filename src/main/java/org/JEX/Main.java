package org.JEX;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Core.GameObjects.Scripting.ILambdaScript;
import org.JEX.Core.IO.Filepath;
import org.JEX.Core.IO.FilepathType;
import org.JEX.Core.IO.Resources.Model;
import org.JEX.Core.Levels.World;
import org.JEX.Core.Levels.LevelType;
import org.JEX.Rendering.Renderers.GLRenderer;
import org.JEX.Rendering.Shaders.GLShader;
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
                //Log.debug("HI!!!: " + delta_time);
            }
        });

        Model m = Model.create2DModel(new Vector2f[]{
                // Create square
                new Vector2f(-0.5f,-0.5f),
                new Vector2f(0.5f,-0.5f),
                new Vector2f(0.5f,0.5f),
                new Vector2f(-0.5f,0.5f),
        },new Vector2f[]{
                new Vector2f(0,0),
                new Vector2f(1,0),
                new Vector2f(1,1),
                new Vector2f(0,1),
        });
        GLRenderer renderer = getGlRenderer(m);

        object.setRenderer(renderer);

        world.add(object);
    }

    private static GLRenderer getGlRenderer(Model m) {
        VertexObjectGLWrapper wrapper = new VertexObjectGLWrapper(m);
        wrapper.queueBuffer();

        GLShader vertexShader = new GLShader(ShaderType.Vertex, """
                #version 330 core
                layout(location = 0) in vec3 vertexPos;
                layout(location = 1) in vec3 normal;
                layout(location = 2) in vec2 uv;
                                
                void main() {
                    gl_Position.xyz = vertexPos;
                    gl_Position.w = 1.0;
                }
                """);

        GLShader fragmentShader = new GLShader(ShaderType.Fragment, """
                #version 330 core
                out vec4 FragColor;
                void main(){
                  FragColor = vec4(0.5, 1, 0, 0);
                }
                """);


        GLRenderer renderer = new GLRenderer(vertexShader, fragmentShader);

        renderer.setVertexObject(wrapper);
        return renderer;
    }
}