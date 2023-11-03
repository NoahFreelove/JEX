package org.JEX.Core.Levels;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Logs.Exceptions.ArgumentExceptions.NullArgumentException;
import org.JEX.Logs.Log;
import org.JEX.Rendering.Renderers.Renderer;

import java.util.HashMap;
import java.util.Objects;

public class World extends Level{

    public World(LevelConfig config) {
        super(config.setType(LevelType.THIRD_DIMENSIONAL));
    }

    @Override
    public JEXIterator<GameObject> getGameObjects() {

        return new JEXIterator<>(gameObjects_arr);
    }

    @Override
    public JEXIterator<Renderer> getLevelRenderables() {
        return new JEXIterator<>(renderable_arr);
    }

    @Override
    public void add(GameObject object) {
        if(object == null)
        {
            Log.error(new NullArgumentException("Cannot add null GameObject to world", "Aborted add to world"));
            return;
        }
        if(gameObjects.contains(object)){
            Log.warn("Didn't add object to world because it is already in the world: " + config.getName());
        }
        gameObjects.add(object);

        gameObjects_arr = gameObjects.toArray(new GameObject[0]);
    }

    @Override
    public void remove(GameObject object) {
        gameObjects.remove(object);
        gameObjects_arr = gameObjects.toArray(new GameObject[0]);
    }

    @Override
    public void clear() {
        gameObjects.clear();
        gameObjects_arr = new GameObject[0];
    }

    @Override
    public void start() {
        getGameObjects().forEach(GameObject::start);
    }

    @Override
    public void update(float delta_time) {
        getGameObjects().forEach(obj -> {
            obj.update(delta_time);
        });
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

    @Override
    public void getGameObject(SearchQuery query) {
        if(query == null)
        {
            Log.error(new NullArgumentException("Cannot search for null query.","Aborting search."));
            return;
        }
        query.tag_id = query.tag.hashCode();

        if(query.type == SearchQuery.SearchType.Name){
            getGameObjects().forEachBool(obj -> {
                if(Objects.equals(obj.getName(), query.name)){
                    query.found = true;
                    query.object = obj;
                    return true;
                }
                return false;
            });
        }
        else if(query.type == SearchQuery.SearchType.Tag){
            getGameObjects().forEachBool(obj -> {
                if(obj.getTagID() == query.tag_id){
                    query.found = true;
                    query.object = obj;
                    return true;
                }
                return false;
            });
        }
        else if(query.type == SearchQuery.SearchType.NameTag){
            getGameObjects().forEachBool(obj -> {
                if(Objects.equals(obj.getName(), query.name) && obj.getTagID() == query.tag_id){
                    query.found = true;
                    query.object = obj;
                    return true;
                }
                return false;
            });
        }
    }
}
