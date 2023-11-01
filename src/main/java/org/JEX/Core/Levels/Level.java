package org.JEX.Core.Levels;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Level {
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();
    protected ArrayList<Renderer> renderable = new ArrayList<>();
    protected final LevelConfig config;

    protected Level(LevelConfig config){
        this.config = config;
    }

    public abstract GameObject[] getGameObjects();
    public abstract Renderer[] getLevelRenderables();

    public abstract void add(GameObject object);
    public abstract void remove(GameObject object);
    public abstract void clear();

    public abstract void start();
    public abstract void update();
    public abstract void unload();
    public abstract LevelConfig getConfig();

    public abstract void loadLevel(HashMap<String, HashMap<String,String>>[] objects);
    public abstract HashMap<String, HashMap<String,String>>[] saveLevel();
}
