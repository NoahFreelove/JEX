package org.JEX;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Core.GameObjects.Scripting.LambdaScript;
import org.JEX.Core.GameObjects.Scripting.LambdaScriptI;
import org.JEX.Core.IO.Filepath;
import org.JEX.Core.IO.FilepathType;
import org.JEX.Core.Levels.World;
import org.JEX.Core.Levels.WorldType;
import org.JEX.Logs.Log;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config, true);

        World world = new World(new LevelConfig("World1", WorldType.THIRD_DIMENSIONAL));
        instance.changeLevel(world);

        GameObject object = new GameObject();
        object.addScript(new LambdaScript(new LambdaScriptI() {
            @Override
            public void update(float delta_time) {
                Log.debug("HI!!!: " + delta_time);
            }
        }));

        world.add(object);
    }
}