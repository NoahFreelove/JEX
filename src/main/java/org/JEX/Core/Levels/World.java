package org.JEX.Core.Levels;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.HashMap;

public class World extends Level{

    protected World(LevelConfig config) {
        super(config.setType(WorldType.THIRD_DIMENSIONAL));
    }

    @Override
    public GameObject[] getGameObjects() {
        return new GameObject[0];
    }

    @Override
    public Renderer[] getLevelRenderables() {
        return new Renderer[0];
    }

    @Override
    public void add(GameObject object) {

    }

    @Override
    public void remove(GameObject object) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void unload() {

    }

    @Override
    public LevelConfig getConfig() {
        return config;
    }

    @Override
    public void loadLevel(HashMap<String, HashMap<String, String>>[] objects) {

    }

    @Override
    public HashMap<String, HashMap<String, String>>[] saveLevel() {
        return new HashMap[0];
    }
}
