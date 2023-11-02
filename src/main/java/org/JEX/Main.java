package org.JEX;

import org.JEX.Core.Configs.JEXConfig;
import org.JEX.Core.Engine.JEX;
import org.JEX.Core.GameObjects.GameObject;
import org.JEX.Core.IO.Filepath;
import org.JEX.Core.IO.FilepathType;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        JEXConfig config = JEXConfig.createFromFile(new Filepath("config.txt", FilepathType.ClassLoader));

        JEX instance = JEX.startEngine(config);

        //Log.print(new BufferedFile(new Filepath("file.txt", FilepathType.ClassLoader),true).getDataAsString());
    }
}