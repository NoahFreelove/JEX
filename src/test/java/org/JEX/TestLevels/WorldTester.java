package org.JEX.TestLevels;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.GameObject;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.GameObjects.Scripting.ILambdaScript;
import org.JEX.Core.IO.Resources.Model;
import org.JEX.Core.Input.ITC;
import org.JEX.Core.Input.KeyboardHandler;
import org.JEX.Core.Levels.LevelType;
import org.JEX.Core.Levels.World;
import org.JEX.Rendering.Renderers.GLRenderer;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.ShaderType;
import org.JEX.Rendering.Shaders.Uniforms.Vector4fUniform;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.junit.jupiter.api.Test;

public class WorldTester {

    @Test
    public void sampleWorld(){
        TestWorld(createSampleWorld(), ITC.keyCode("SPACE"));
    }

    volatile static boolean[] stop = {false};

    public static void TestWorld(World world, int escapeKey){

        GameObject escapeObject = JEX.createGameObject();

        JEX.addLambdaScript(new ILambdaScript() {
            @Override
            public void update(float delta_time) {
                if(KeyboardHandler.isKeyDown(escapeKey))
                    stop[0] = true;
            }
        }, escapeObject);
        world.add(escapeObject);
        JEX instance = JEX.startEngine(JEXConfig.createDefaultConfig(), true);

        instance.changeLevel(world);


        while (!stop[0]){}
    }
    private static World createSampleWorld(){
        World world = new World(new LevelConfig("World1", LevelType.THIRD_DIMENSIONAL));

        GameObject triangle_object = JEX.createGameObject();

        Model triangle = Model.create2DModel(new Vector2f[]{
                new Vector2f(-0.5f, -0.5f),
                new Vector2f(0.5f, -0.5f),
                new Vector2f(0.5f, 0.5f),
        }, new Vector2f[]{
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(1, 1),
        });
        GLRenderer renderer = getGlRenderer(triangle);
        triangle_object.setRenderer(renderer);
        world.add(triangle_object);
        renderer.getGLShaderProgram().addUniform(new Vector4fUniform("color", new Vector4f(1,1,0,1)));
        return world;
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
                    gl_Position = vec4(vertexPos, 1.0);
                }
                """);

        GLShader fragmentShader = new GLShader(ShaderType.Fragment, """
                #version 330 core
                out vec4 FragColor;
                uniform vec4 color;
                void main(){
                    FragColor = color;
                }
                """);


        GLRenderer renderer = new GLRenderer(vertexShader, fragmentShader);

        renderer.setVertexObject(wrapper);
        return renderer;
    }
}
