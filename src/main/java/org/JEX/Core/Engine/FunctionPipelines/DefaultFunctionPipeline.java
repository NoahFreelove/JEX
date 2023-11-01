package org.JEX.Core.Engine.FunctionPipelines;

import org.JEX.Core.Engine.JEX;

public final class DefaultFunctionPipeline implements FunctionPipeline {
    public DefaultFunctionPipeline(){

    }

    @Override
    public void game_update(float delta_time, boolean is_render_update) {

        if(is_render_update){
            JEX.getInstance().triggerRenderPipeline();
        }
    }


    @Override
    public void endFrame() {
        JEX.getInstance().triggerEngineRunnables();
    }
}
