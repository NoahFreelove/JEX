package org.JEX.Core.Configs;

import org.JEX.Core.Engine.FunctionPipelines.DefaultFunctionPipeline;
import org.JEX.Core.Engine.FunctionPipelines.FunctionPipeline;
import org.JEX.Core.Engine.Window.GLFWWindow;
import org.JEX.Core.Engine.Window.GraphicsAPI;
import org.JEX.Core.IO.BufferedFile;
import org.JEX.Core.IO.Filepath;
import org.JEX.Logs.Exceptions.ConfigExceptions.FieldParseException;
import org.JEX.Logs.Exceptions.ConfigExceptions.InaccessibleAttributeException;
import org.JEX.Logs.Exceptions.ConfigExceptions.NoSuchConfigAttributeException;
import org.JEX.Logs.Exceptions.ConfigExceptions.UnknownConfigTypeException;
import org.JEX.Logs.Exceptions.JEXception;
import org.JEX.Logs.Log;
import org.JEX.Rendering.RenderPipelines.GLRenderPipeline;
import org.JEX.Rendering.RenderPipelines.RenderPipeline;

public class JEXConfig {
    private static final JEXConfig default_config = new JEXConfig();

    //region Logging
    private boolean enable_logs = true;
    private boolean log_errors = true;
    private boolean log_warnings = true;
    private boolean log_debug = true;
    //endregion

    private Class<? extends GLFWWindow> window_class = GLFWWindow.class;
    private Class<? extends FunctionPipeline> default_function_pipeline = DefaultFunctionPipeline.class;
    private Class<? extends RenderPipeline> default_render_pipeline = GLRenderPipeline.class;
    private int window_width = 500;
    private int window_height = 500;
    private String window_title = "JEX Window";
    private long window_monitor = 0L;
    private long window_share = 0L;
    private GraphicsAPI graphics_API = GraphicsAPI.OpenGL;
    private boolean vsync = true;
    private boolean resizable = true;

    private JEXConfig() {}

    public static JEXConfig createBlankConfig(){
        return new JEXConfig();
    }
    public static JEXConfig createDefaultConfig(){
        return copy(default_config);
    }
    public static JEXConfig createFromConfig(JEXConfig config){
        return copy(config);
    }
    public static JEXConfig createFromFile(Filepath filepath){
        JEXConfig config = new JEXConfig();
        BufferedFile bf = new BufferedFile(filepath, true);
        config.parseConfig(bf.getDataAsLines());
        return config;
    }

    /**
     * Uses Java Reflection to parse a config array.
     * @param lines The config file as an array of lines.
     */
    public void parseConfig(String[] lines){
        for(String line : lines){
            String[] parts = line.split(":");
            if(parts.length != 3){
                continue;
            }
            String varName = parts[0].trim();
            String varValue = parts[1].trim();
            String varType = parts[2].trim();

            if(varName.equals("window_class")){
                try {
                    window_class = Class.forName(varValue).asSubclass(GLFWWindow.class);
                }
                catch (ClassNotFoundException e){
                    Log.error(new JEXception(e));
                }
            }
            else if(varName.equals("graphics_api")){
                if (varValue.equals("opengl")){
                    graphics_API = GraphicsAPI.OpenGL;
                }
                else if (varValue.equals("vulkan")){
                    graphics_API = GraphicsAPI.Vulkan;
                }
                else {
                    graphics_API = GraphicsAPI.Unknown;
                    Log.error(new JEXception("Unknown graphics API: " + varValue));
                }
                continue;
            }

            try {
                switch (varType){
                    case "boolean","bool":
                        this.getClass().getDeclaredField(varName).setAccessible(true);
                        this.getClass().getDeclaredField(varName).set(this, Boolean.parseBoolean(varValue));
                        break;
                    case "int","integer":
                        this.getClass().getDeclaredField(varName).setAccessible(true);
                        this.getClass().getDeclaredField(varName).set(this, Integer.parseInt(varValue));
                        break;
                    case "double":
                        this.getClass().getDeclaredField(varName).setAccessible(true);
                        this.getClass().getDeclaredField(varName).set(this, Double.parseDouble(varValue));
                        break;
                    case "float":
                        this.getClass().getDeclaredField(varName).setAccessible(true);
                        this.getClass().getDeclaredField(varName).set(this, Float.parseFloat(varValue));
                        break;
                    case "String","string":
                        this.getClass().getDeclaredField(varName).setAccessible(true);
                        this.getClass().getDeclaredField(varName).set(this, varValue);
                        break;
                    default:
                        Log.error(new UnknownConfigTypeException("Unknown config type: \"" + varType + "\" for variable \"" + varName + "\"",
                                "Skipped in config loading process."));
                        break;
                }
            } catch (NoSuchFieldException e) {
                Log.error(new NoSuchConfigAttributeException("Config file contains variable \"" + varName + "\" which is not a valid config attribute."));
            } catch (IllegalAccessException e) {
                Log.error(new InaccessibleAttributeException("Config file contains variable \"" + varName + "\" which is not accessible."));
            }
            catch (Exception e){
                Log.error(new FieldParseException("For name: \"" + varName + "\" and type: \"" +
                        varType + "\" and value: \"" + varValue + "\"",
                        "Skipped in config loading process."));
            }
        }
    }

    /**
     * Creates a unique copy of the config.
     * @param original input config to copy
     * @return a completely new config with the same values as the input config. (Deep copy)
     */
    public static JEXConfig copy(JEXConfig original){
        JEXConfig copy = new JEXConfig();
        copy.enable_logs = original.enable_logs;
        copy.log_errors = original.log_errors;
        copy.log_debug = original.log_debug;
        return copy;
    }

    public static JEXConfig getDefault(){
        return copy(default_config);
    }

    public boolean enable_logs() {
        return enable_logs;
    }

    public boolean log_errors() {
        return log_errors;
    }

    public boolean log_debug() {
        return log_debug;
    }
    public boolean log_warnings(){
        return log_warnings;
    }

    public Class<? extends GLFWWindow> window_class() {
        return window_class;
    }

    public int window_width() {
        return window_width;
    }

    public int window_height() {
        return window_height;
    }

    public String window_title() {
        return window_title;
    }

    public long window_monitor() {
        return window_monitor;
    }

    public long window_share() {
        return window_share;
    }


    public boolean vsync() {
        return vsync;
    }

    public boolean resizeable() {
        return resizable;
    }

    public GraphicsAPI graphicsAPI() {
        return graphics_API;
    }

    public FunctionPipeline functionPipeline(){
        try {
            return default_function_pipeline.getConstructor().newInstance();
        }
        catch (Exception e){
            Log.error(new JEXception(e));
            return null;
        }
    }

    public RenderPipeline renderPipeline(){
        try {
            return default_render_pipeline.getConstructor().newInstance();
        }
        catch (Exception e){
            Log.error(new JEXception(e));
            return null;
        }
    }
}
