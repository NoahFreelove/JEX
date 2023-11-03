package org.JEX;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Core.GameObjects.Scripting.ILambdaScript;
import org.JEX.Core.IO.Filepath;
import org.JEX.Core.IO.FilepathType;
import org.JEX.Core.Levels.World;
import org.JEX.Core.Levels.LevelType;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // TODO: Resource Manager
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config, true);

        World world = new World(new LevelConfig("World1", LevelType.THIRD_DIMENSIONAL));
        instance.changeLevel(world);

        GameObject object = new GameObject();
        object.addScript(new ILambdaScript() {
            @Override
            public void update(float delta_time) {
                //Log.debug("HI!!!: " + delta_time);
            }
        });

        world.add(object);
    }
}