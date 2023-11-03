package org.JEX.Core.Configs;

import org.JEX.Core.Levels.LevelType;

public final class LevelConfig {
    private String name;
    private LevelType type;

    public LevelConfig() {
    }

    public LevelConfig(String name, LevelType type) {
        this.name = name;
        this.type = type;
    }

    public LevelConfig setType(LevelType type){
        this.type = type;
        return this;
    }

    public String getName(){
        return name;
    }
}
