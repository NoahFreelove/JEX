package org.JEX.Core.Levels;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Level {
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();

    protected GameObject[] gameObjects_arr = new GameObject[0];
    protected ArrayList<Renderer> renderable = new ArrayList<>();
    protected Renderer[] renderable_arr = new Renderer[0];
    protected final LevelConfig config;

    protected Level(LevelConfig config){
        this.config = config;
    }

    public abstract LevelIterator<GameObject> getGameObjects();
    public abstract LevelIterator<Renderer> getLevelRenderables();

    public abstract void add(GameObject object);
    public abstract void remove(GameObject object);
    public abstract void clear();

    public abstract void start();
    public abstract void update(float delta_time);
    public abstract void unload();
    // Different to start because start is called before the first frame of the new level
    // While load is called immediately after the previous level is unloaded.
    public abstract void load();
    public abstract LevelConfig getConfig();

    public abstract void loadLevel(HashMap<String, HashMap<String,String>>[] objects);
    public abstract HashMap<String, HashMap<String,String>>[] saveLevel();
}
