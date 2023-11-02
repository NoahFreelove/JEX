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
    public LevelIterator<GameObject> getGameObjects() {

        return new LevelIterator<>(gameObjects_arr);
    }

    @Override
    public LevelIterator<Renderer> getLevelRenderables() {
        return new LevelIterator<>(renderable_arr);
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
    public void update(float delta_time) {

    }

    @Override
    public void unload() {

    }

    @Override
    public void load(){}

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
