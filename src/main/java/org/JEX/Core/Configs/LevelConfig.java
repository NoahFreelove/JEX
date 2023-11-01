package org.JEX.Core.Configs;

import org.JEX.Core.Levels.WorldType;

public final class LevelConfig {
    private String name;
    private WorldType type;

    public LevelConfig() {
    }

    public LevelConfig(String name, WorldType type) {
        this.name = name;
        this.type = type;
    }

    public LevelConfig setType(WorldType type){
        this.type = type;
        return this;
    }
}
