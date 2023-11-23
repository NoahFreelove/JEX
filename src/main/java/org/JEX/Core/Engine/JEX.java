package org.JEX.Core.Engine;

import org.JEX.Core.Annotations.EngineThread;
import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Engine.FunctionPipelines.FunctionPipeline;
import org.JEX.Core.Engine.Window.GLFWWindow;
import org.JEX.Core.Engine.Window.WindowCreationResult;
import org.JEX.Core.Scripting.ILambdaScript;
import org.JEX.Core.Scripting.LambdaScript;
import org.JEX.Core.Scripting.Script;
import org.JEX.Core.Input.KeyboardHandler;
import org.JEX.Core.Input.MouseHandler;
import org.JEX.Core.Levels.Level;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Logs.Exceptions.ArgumentExceptions.JEXception_Argument;
import org.JEX.Logs.Exceptions.ArgumentExceptions.NullArgumentException;
import org.JEX.Logs.Exceptions.EngineSpecificExceptions.EngineNotYetStartedException;
import org.JEX.Logs.Exceptions.JEXception;
import org.JEX.Logs.Exceptions.NullException.NullReturnException;
import org.JEX.Logs.Exceptions.ScriptExceptions.InstanceCreationError;
import org.JEX.Logs.Log;
import org.JEX.Rendering.RenderPipelines.RenderPipeline;
import org.JEX.Rendering.Renderers.Renderer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
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

    private static CopyOnWriteArrayList<Runnable> engine_thread_runnables = new CopyOnWriteArrayList<>();

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

    public static Level getLevel() {
        return engine_instance.active_level;
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
                KeyboardHandler.keyRepeat(key);
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

        if(tmp_render_pipeline != null)
        {
            render_pipeline = tmp_render_pipeline;
            tmp_render_pipeline = null;
        }

        if(render_pipeline == null || active_level == null)
            return;

        render_pipeline.preFrameInit();
        render_pipeline.render(active_level.getLevelRenderables(), active_level.getConfig());
    }

    public static void queueEngineFunction(Runnable runnable){
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

    public static Script instanceScript(Class<? extends Script> clazz, GameObject target){
        // if class is abstract, return null with InstanceCreationError
        if(Modifier.isAbstract(clazz.getModifiers())){
            Log.error(new InstanceCreationError("Cannot create instance of abstract class.", clazz, "returned null script."));
            return null;
        }
        try {
            Constructor<? extends Script> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true); // Make the constructor accessible
            Script newInstance = constructor.newInstance();
            target.object_reference.addScript(newInstance);
            newInstance.addToObject(target);
            newInstance.setEnabled(true);
            return newInstance;
            // Use the instance as needed
        } catch (Exception e) {
            Log.error(new InstanceCreationError("Error when trying to create new instance.", clazz, "returned null script."));
            return null;
        }
    }

    public static void addLambdaScript(ILambdaScript script, GameObject target){
        target.object_reference.addScript(script).addToObject(target);
    }

    public static boolean isRunning(){
        return running;
    }

    public GameObject makeGameObject(){
        return new GameObject(new GameObjectInterface());
    }

    public static GameObject createGameObject(){
        return engine_instance.makeGameObject();
    }

     class GameObjectInterface {
        private boolean isActive = true;
        private boolean has_renderer;
        private Renderer renderer;

        private String name = "GameObject";
        private String name_tag = "untagged";
        private int tag;
        private final int mem_ID;

        private Script[] scripts;

        private Transform transform = new Transform();

        public GameObjectInterface() {
            mem_ID = this.hashCode();
            tag = name_tag.hashCode();
            scripts = new Script[0];
        }

        public boolean hasRenderer() {
            return has_renderer;
        }

        public Renderer getRenderer(){
            if(!has_renderer)
            {
                Log.error(new NullReturnException("GameObject doesn't have a renderer: " +
                        "Check if the GameObject has a renderer before calling this method", "Returned null."));
                return null;
            }

            return renderer;
        }

        public static Renderer[] getBulkRenderers(GameObjectInterface[] input){
            ArrayList<Renderer> renderers = new ArrayList<>();
            for(GameObjectInterface object : input){
                if(object.has_renderer){
                    renderers.add(object.getRenderer());
                }
            }
            return renderers.toArray(new Renderer[0]);
        }


        public HashMap<String, HashMap<String,String>> saveGameObject(){
            return new HashMap<String, HashMap<String,String>>();
        }

        public void loadGameObject(HashMap<String,HashMap<String,String>> input){

        }

        public String getName() {
            return name;
        }

        public String getTag() {
            return name_tag;
        }

        public int getTagID(){return tag;}

        public void setName(String name) {
            this.name = name;
        }

        public void setTag(String name_tag) {
            this.name_tag = name_tag;
            this.tag = this.name_tag.hashCode();
        }
        private LambdaScript addScript(ILambdaScript lambdaScript){
            LambdaScript output = new LambdaScript(lambdaScript);
            output.setEnabled(true);
            addScript(output);
            return output;
        }

        private boolean addScript(Script script){
            if(script == null){
                Log.error(new NullReturnException("Cannot add null Script", "Aborted Script Add..."));
                return false;
            }
            // Increase the size of the array
            Script[] new_scripts = new Script[scripts.length + 1];
            // Copy the old array into the new array
            System.arraycopy(scripts, 0, new_scripts, 0, scripts.length);
            // Add the new script to the end of the new array
            new_scripts[scripts.length] = script;
            // Set the new array as the scripts array
            scripts = new_scripts;
            return true;
        }

        public boolean removeScript(int index){
            if(index>= scripts.length || index<0){
                Log.error(new JEXception_Argument(Integer.class, 0, index, "Index out of bound to remove script."));
                return false;
            }
            // Decrease the size of the array
            Script[] new_scripts = new Script[scripts.length - 1];
            // Copy the old array into the new array
            System.arraycopy(scripts, 0, new_scripts, 0, index);
            // Copy the rest of the old array into the new array
            System.arraycopy(scripts, index+1, new_scripts, index, scripts.length - index - 1);
            // Set the new array as the scripts array
            scripts = new_scripts;
            return true;
        }

        public JEXIterator<Script> getScripts(){
            return new JEXIterator<>(scripts);
        }

        public void start(){
            if(!isActive)
                return;
            getScripts().forEach(obj -> {
                if(obj.isEnabled()){
                    obj.start();
                }
            });
        }
        public void update(float delta_time){
            if(!isActive)
                return;
            getScripts().forEach(script -> {
                if(script.isEnabled()) {
                    script.update(delta_time);
                }
            });
        }

        public void revokeRenderer(){
            this.renderer = null;
        }

        public void setRenderer(Renderer r){
            if(r == null)
                return;
            has_renderer = true;
            renderer = r;
        }
        public Transform getTransform(){
            return transform;
        }

        public void setTransform(Transform t) {
             this.transform = t;
         }

        public void setActive(boolean val){
            this.isActive = val;
        }
        public boolean isActive(){
            return isActive;
        }
     }
}