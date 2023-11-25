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
import org.JEX.Rendering.Shaders.OpenGL.GLLayout;
import org.JEX.Rendering.Shaders.OpenGL.GLShader;
import org.JEX.Rendering.Shaders.ShaderType;
import org.JEX.Rendering.Shaders.Uniforms.Vector4fUniform;
import org.JEX.Rendering.VertexUtil.VertexObjectGLWrapper;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // TODO: Resource Manager
        // TODO: Custom data additions to model loader
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config, true);

        GameObject cameraMan = JEX.createGameObject().addScript(ProjectionCamera.class);
        ProjectionCamera cam = (ProjectionCamera) cameraMan.getScript(ProjectionCamera.class);
        cameraMan.getTransform().translate(0,0,10);

        World world = new World(new LevelConfig("World1", LevelType.THIRD_DIMENSIONAL, cam));
        instance.changeLevel(world);

        GameObject triangle_object = JEX.createGameObject();
        Model triangle = ModelLoader.loadModel(new Filepath("cube.model", FilepathType.ClassLoader));
        GLRenderer renderer = getGlRenderer(triangle);
        triangle_object.setRenderer(renderer);
        world.add(triangle_object);
        world.add(cameraMan);

        //renderer.getGLShaderProgram().addUniform(new Vector4fUniform("color", new Vector4f(0.5f, 0.5f, 0, 1)));

        // Create Vector4 colors for a cube
        Vector4f[] colors = new Vector4f[triangle.getVerts().length];
        // Randomize colors
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Vector4f((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
        }
        GLLayout colorLayout = new GLLayout(colors);
        colorLayout.queueBuffer();
        colorLayout.setLocation(3);
        renderer.getGLShaderProgram().addLayout(colorLayout);

        InputCombo xAxis = new InputCombo(new int[]{ITC.keyCode("A"), ITC.keyCode("D")}, new float[]{-1,1});
        InputCombo zAxis = new InputCombo(new int[]{ITC.keyCode("W"), ITC.keyCode("S")}, new float[]{1,-1});
        InputCombo yAxis = new InputCombo(new int[]{ITC.keyCode("Q"), ITC.keyCode("E")}, new float[]{-1,1});

        InputCombo mouseX = new InputCombo(new int[]{ITC.keyCode("LEFT"), ITC.keyCode("RIGHT")}, new float[]{-1,1});
        InputCombo mouseY = new InputCombo(new int[]{ITC.keyCode("UP"), ITC.keyCode("DOWN")}, new float[]{-1,1});

        InputHandler.addAction("Horizontal", xAxis);
        InputHandler.addAction("ZAxis", zAxis);
        InputHandler.addAction("Vertical", yAxis);
        InputHandler.addAction("MouseX", mouseX);
        InputHandler.addAction("MouseY", mouseY);

        Vector3f right = new Vector3f(1,0,0);
        Vector3f forward = new Vector3f(0,0,1);

        JEX.addLambdaScript(new ILambdaScript() {
            @Override
            public void update(float delta_time) {
                if(xAxis.isAnyPressed()) {
                    right.set(cam.getRight()).mul(xAxis.weight()*0.1f);
                    cameraMan.getTransform().translate(right);
                }
                if(zAxis.isAnyPressed()) {
                    forward.set(cam.getForward()).mul(zAxis.weight()*0.1f);
                    cameraMan.getTransform().translate(forward);
                }
                if(yAxis.isAnyPressed()) {
                    cameraMan.getTransform().translate(0, yAxis.weight()*0.1f, 0);
                }
                if(mouseX.isAnyPressed()) {
                    cam.rotHorizAngle(-mouseX.weight() * JEX.getInstance().getWindow().getDeltaTime());
                }
                if(mouseY.isAnyPressed()) {
                    cam.rotVertAngle(-mouseY.weight() * JEX.getInstance().getWindow().getDeltaTime());
                }
                //triangle_object.getTransform().rotate(1,0,0,0);
            }
        }, triangle_object);
    }

    private static GLRenderer getGlRenderer(Model m) {
        VertexObjectGLWrapper wrapper = new VertexObjectGLWrapper(m);
        wrapper.buffer();

        GLShader vertexShader = new GLShader(ShaderType.Vertex, new Filepath("vertex.glsl", FilepathType.ClassLoader));
        GLShader fragmentShader = new GLShader(ShaderType.Fragment, new Filepath("fragment.glsl", FilepathType.ClassLoader));
        GLRenderer renderer = new GLRenderer(vertexShader, fragmentShader);

        renderer.setVertexObject(wrapper);
        return renderer;
    }
}