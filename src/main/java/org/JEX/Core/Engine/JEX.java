package org.JEX.Core.Engine;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Engine.FunctionPipelines.FunctionPipeline;
import org.JEX.Core.Engine.Window.GLFWWindow;
import org.JEX.Core.Engine.Window.WindowCreationResult;
import org.JEX.Core.Input.KeyboardHandler;
import org.JEX.Core.Input.MouseHandler;
import org.JEX.Core.Levels.Level;
import org.JEX.Logs.Exceptions.ArgumentExceptions.NullArgumentException;
import org.JEX.Logs.Exceptions.EngineSpecificExceptions.EngineNotYetStartedException;
import org.JEX.Logs.Exceptions.JEXception;
import org.JEX.Logs.Log;
import org.JEX.Rendering.RenderPipelines.RenderPipeline;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.JEX.Core.Configs.JEXConfig.createDefaultConfig;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.system.MemoryStack.stackPush;

public class JEX {
    private static JEXConfig active_config = createDefaultConfig();
    protected static JEX engine_instance;
    private static boolean engine_created = false;
    private static volatile boolean running = false;
    private Thread engine_thread;

    private CopyOnWriteArrayList<Runnable> engine_thread_runnables = new CopyOnWriteArrayList<>();

    private GLFWWindow window;

    private FunctionPipeline function_pipeline;
    private FunctionPipeline tmp_update_pipeline;
    private RenderPipeline render_pipeline;
    private RenderPipeline tmp_render_pipeline;

    private Level active_level;

    private static final ThreadLocal<Boolean> is_engine_thread = new ThreadLocal<>();
    private boolean new_level = false;

    protected JEX(JEXConfig config){
        engine_instance = this;
        engine_created = true;
        setConfig(config);
    }

    /**
     * Starts JEX and creates a window using the given config.
     * @param config The config to use for the engine.
     */
    public static JEX startEngine(JEXConfig config, boolean verify){
        if (engine_instance != null){
            engine_instance.setConfig(config);
            return engine_instance;
        }
        engine_instance = new JEX(config);
        is_engine_thread.set(false);

        engine_instance.engine_thread = new Thread(()->{
            GLFWErrorCallback.createPrint(System.err).set();
            WindowCreationResult result = engine_instance.create_engine_window(config);

            if(engine_instance.window == null || result != WindowCreationResult.GOOD){
                engine_created = false;
                engine_instance = null;
                if(verify){
                    System.exit(1);
                }
                return;
            }

            engine_instance.engine_init();

            is_engine_thread.set(true);
            running = true;
            //EventPoller.pollThread.start();
            engine_instance.window.windowLoop(engine_instance);
        });

        engine_instance.engine_thread.setName("JEX_Engine_Thread");
        engine_instance.engine_thread.start();

        return engine_instance;
    }

    private WindowCreationResult create_engine_window(JEXConfig config) {
        try {
            GLFWWindow window = config.window_class().getConstructor().newInstance();
            WindowCreationResult result = window.createWindow(config.window_width(), config.window_height(), config.window_title(), config.window_monitor(), config.window_share());
            engine_instance.window = window;
            return result;
        }
        catch (Exception e){
            Log.error(new JEXception(e));
            e.printStackTrace();
        }
        return WindowCreationResult.WINDOW_CREATION_FAILED;
    }

    @EngineThread
    private void engine_init(){

        glfwMakeContextCurrent(engine_instance.getHandle());
        glfwSetWindowAttrib(window.getHandle(), GLFW_RESIZABLE, (active_config.resizeable()? 1:0));

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the windowHandle size passed to glfwCreateWindow
            glfwGetWindowSize(getHandle(), pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if(videoMode != null){
                // Center the windowHandle
                glfwSetWindowPos(
                        getHandle(),
                        (videoMode.width() - pWidth.get(0)) / 2,
                        (videoMode.height() - pHeight.get(0)) / 2
                );
            }
        }
        glfwSwapInterval(active_config.vsync() ? 1 : 0);
        window.createCapabilities();
        glfwShowWindow(getHandle());

        config_input();

    }

    private void config_input() {
        glfwSetKeyCallback(getHandle(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);

            if(action == GLFW_PRESS){
                KeyboardHandler.onKeyPress(key);
            }
            else if(action == GLFW_RELEASE){
                KeyboardHandler.onKeyRelease(key);
            }
            else if(action == GLFW_REPEAT){
                KeyboardHandler.onKeyPress(key);
            }
        });

        glfwSetMouseButtonCallback(getHandle(), (window, button, action, mods) -> {
            if(action == GLFW_PRESS){
                MouseHandler.onMousePress(button);
            }
            else if(action == GLFW_RELEASE) {
                MouseHandler.onMouseRelease(button);
            }
        });
        // Position callback
        glfwSetCursorPosCallback(getHandle(), (window, xpos, ypos) -> {
            MouseHandler.updatePosition((float) xpos, (float) ypos);
        });
    }

    @EngineThread
    public void game_update(float delta_time, boolean renderUpdate){
        if(tmp_update_pipeline != null)
        {
            function_pipeline = tmp_update_pipeline;
            tmp_update_pipeline = null;
        }
        if(function_pipeline == null || active_level == null)
            return;

        if(new_level)
        {
            new_level = false;
            this.function_pipeline.level_start(active_level);
        }

        function_pipeline.game_update(active_level, delta_time, renderUpdate);
    }

    public void triggerEngineRunnables(){
        engine_thread_runnables.forEach(Runnable::run);
        engine_thread_runnables.clear();
    }

    @EngineThread
    public void window_update(){
    }

    public static JEX getInstance(){
        return engine_instance;
    }

    protected void preConfigUpdate(JEXConfig oldConfig, JEXConfig newConfig){}

    public static JEXConfig active_config(){
        return active_config;
    }

    public static void setEngineConfig(JEXConfig config){
        engine_instance.setConfig(config);
    }

    public void setConfig(JEXConfig config){
        if(!engine_created)
        {
            Log.error(new EngineNotYetStartedException("Cannot set config before engine has started."));
            return;
        }
        preConfigUpdate(active_config, config);
        active_config = config;
        setFunctionalPipeline(active_config.functionPipeline());
        setRenderPipeline(active_config.renderPipeline());
    }

    public GLFWWindow getWindow(){
        return window;
    }

    public long getHandle(){
        return window.getHandle();
    }

    public void setFunctionalPipeline(FunctionPipeline pipeline){
        if(pipeline == null){
            Log.error(new NullArgumentException("Cannot set functional pipeline to null."));
            return;
        }
        tmp_update_pipeline = pipeline;
    }

    public void setRenderPipeline(RenderPipeline pipeline){
        if(pipeline == null){
            Log.error(new NullArgumentException("Cannot set render pipeline to null."));
            return;
        }
        tmp_render_pipeline = pipeline;
    }

    // Runs the active render pipeline
    @EngineThread
    public void triggerRenderPipeline(){
        if(render_pipeline == null || active_level == null)
            return;

        if(tmp_render_pipeline != null)
        {
            render_pipeline = tmp_render_pipeline;
            tmp_render_pipeline = null;
        }

        render_pipeline.preFrameInit();
        render_pipeline.render(active_level.getLevelRenderables(), active_level.getConfig());
    }

    public void queueEngineFunction(Runnable runnable){
        engine_thread_runnables.add(runnable);
    }

    public void endFrame() {
        function_pipeline.endFrame();
    }

    public void changeLevel(Level active_level) {
        if(function_pipeline != null)
            this.function_pipeline.on_level_unload(this.active_level);
        this.active_level = active_level;
        if(function_pipeline != null)
            this.function_pipeline.on_level_load(active_level);
        new_level = true;
    }

    public static boolean isRunning(){
        return running;
    }
}
