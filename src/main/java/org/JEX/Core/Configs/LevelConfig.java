package org.JEX.Core.Configs;

import org.JEX.Audio.AudioListener;
import org.JEX.Core.Levels.LevelType;
import org.JEX.Rendering.Camera.Camera;

public final class LevelConfig {
    private String name;
    private LevelType type;
    private Camera active_camera;
    private AudioListener audioListener;

    public LevelConfig() {
    }

    public LevelConfig(String name, LevelType type) {
        this.name = name;
        this.type = type;
    }

    public LevelConfig(String name, LevelType type, Camera active_camera) {
        this.name = name;
        this.type = type;
        this.active_camera = active_camera;
    }

    public LevelConfig setType(LevelType type){
        this.type = type;
        return this;
    }

    public String getName(){
        return name;
    }

    public LevelType getType() {
        return type;
    }

    public Camera getActiveCamera() {
        return active_camera;
    }

    public AudioListener getAudioListener() {
        return audioListener;
    }
}
