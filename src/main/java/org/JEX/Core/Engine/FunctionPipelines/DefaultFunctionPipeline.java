package org.JEX.Core.Engine.FunctionPipelines;

import org.JEX.Core.Engine.JEX;
import org.JEX.Core.Levels.Level;
import org.JEX.Logs.Log;

import static org.JEX.Logs.Log.print;

public final class DefaultFunctionPipeline implements FunctionPipeline {
    public DefaultFunctionPipeline(){

    }

    @Override
    public void level_start(Level activeLevel) {
        activeLevel.start();
    }

    @Override
    public void game_update(Level activeLevel, float delta_time, boolean is_render_update) {
        activeLevel.update(delta_time);
        if(is_render_update){
            JEX.getInstance().triggerRenderPipeline();
        }
    }


    @Override
    public void endFrame() {
        JEX.getInstance().triggerEngineRunnables();
    }

    @Override
    public void on_level_unload(Level oldLevel) {
        oldLevel.unload();
    }
}
