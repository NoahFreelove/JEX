package org.JEX.Core.Engine.Window;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Engine.JEX;
import org.JEX.Logs.Exceptions.GLExceptions.JEXception_GL;
import org.JEX.Logs.Log;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.opengl.GL11.*;

public class GLFWWindow {
    protected long handle = -1L;
    private int window_width = 0;
    private int window_height = 0;
    private String window_title = "";
    private long window_monitor = 0L;
    private long window_share = 0L;
    private long update_millis = 1000L / 60L; // 60 FPS
    private long delta_nanos = 0L;
    private float delta = 0.0f;
    private float aspect_ratio = 0.0f;
    public GLFWWindow(){}

    @EngineThread
    public WindowCreationResult createWindow(int width, int height, String title, long monitor, long share){
        boolean result = glfwInit();

        if(!result){
            return WindowCreationResult.GLFW_NOT_INITIALIZED;
        }

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        handle = glfwCreateWindow(width, height, title, monitor, share);

        aspect_ratio = (float)width / (float)height;

        if(handle == -1L){
            return WindowCreationResult.WINDOW_CREATION_FAILED;
        }

        return WindowCreationResult.GOOD;
    }

    public void destroyWindow(){
        glfwDestroyWindow(handle);
    }

    public long getHandle(){
        return handle;
    }

    public int getWindowWidth() {
        return window_width;
    }

    public int getWindowHeight() {
        return window_height;
    }

    public String getWindowTitle() {
        return window_title;
    }

    public long getWindowMonitor() {
        return window_monitor;
    }

    public long getWindowShare() {
        return window_share;
    }

    public void setWindowWidth(int window_width) {
        this.window_width = window_width;
    }

    public void setWindowHeight(int window_height) {
        this.window_height = window_height;
    }

    public void setWindowTitle(String window_title) {
        this.window_title = window_title;
    }

    public void setWindowMonitor(long window_monitor) {
        this.window_monitor = window_monitor;
    }

    public void setWindowShare(long window_share) {
        this.window_share = window_share;
    }

    public void createCapabilities() {

        if(JEX.active_config().graphicsAPI() == GraphicsAPI.OpenGL){
            GLCapabilities caps = GL.createCapabilities();
            GL.setCapabilities(caps);
            GLUtil.setupDebugMessageCallback();

            if (caps.OpenGL43) {
                GL43.glDebugMessageControl(GL43.GL_DEBUG_SOURCE_API, GL43.GL_DEBUG_TYPE_OTHER, GL43.GL_DEBUG_SEVERITY_NOTIFICATION, (IntBuffer)null, false);
                setupDebugMessageCallback();
            } else if (caps.GL_KHR_debug) {
                KHRDebug.glDebugMessageControl(
                        KHRDebug.GL_DEBUG_SOURCE_API,
                        KHRDebug.GL_DEBUG_TYPE_OTHER,
                        KHRDebug.GL_DEBUG_SEVERITY_NOTIFICATION,
                        (IntBuffer)null,
                        false
                );
            }
        }
        else if (JEX.active_config().graphicsAPI() == GraphicsAPI.Vulkan){

        }

    }

    public float getAspectRatio(){
        return aspect_ratio;
    }

    private static void setupDebugMessageCallback() {
        GLDebugMessageCallbackI callback = new GLDebugMessageCallback() {
            @Override
            public void invoke(int i, int i1, int i2, int i3, int i4, long l, long l1) {
                String message = MemoryUtil.memUTF8(l);
                Log.error(new JEXception_GL(message));
            }
        };
        GL43.glDebugMessageCallback(callback, MemoryUtil.NULL);

        GL43.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
    }

    @EngineThread
    public void windowLoop(JEX engine) {
        glClearColor(0,0,0,1);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_COLOR_MATERIAL);

        long curr_millis = 0;
        while (!glfwWindowShouldClose(handle)){
            long frameStart = System.nanoTime();


            /*try (MemoryStack stack = stackPush()) {
                IntBuffer width  = stack.mallocInt(1);
                IntBuffer height = stack.mallocInt(1);

                glfwGetWindowSize(getHandle(), width, height);
                glViewport(0, 0, width.get(0), height.get(0));
                glClearColor(0,0,0,1);
            }*/
            boolean isRenderUpdate = false;

            curr_millis += delta_nanos;
            if(curr_millis >= update_millis){
                curr_millis = 0;
                isRenderUpdate = true;
            }
            if(isRenderUpdate)
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            engine.game_update(delta, isRenderUpdate);

            if(isRenderUpdate)
            {
                glfwSwapBuffers(handle);
            }

            engine.window_update();


            long frameEnd = System.nanoTime();
            delta_nanos = (frameEnd - frameStart);
            delta = (float) delta_nanos / 1000000000.0f;
            engine.endFrame();
            glfwPollEvents();
        }
    }

    public long getDeltaNanos() {
        return delta_nanos;
    }

    public float getDeltaTime() {
        return delta;
    }
}
