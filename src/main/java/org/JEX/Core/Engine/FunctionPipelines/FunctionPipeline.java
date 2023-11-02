package org.JEX.Core.Engine.FunctionPipelines;

import org.JEX.Core.Levels.Level;

/**
 * Function pipelines allow for the user to define how they want JEX to run game_updates. A standard one is provided
 * But if you wanted to skip certain parts of the update or render loop, you could do that here.
 * Different to RenderPipelines which are used to define how the render loop is run. These are purely for the update loop.
 */
public interface FunctionPipeline {


    void level_start(Level activeLevel);
    void game_update(Level level, float delta_time, boolean isRenderUpdate);

    void endFrame();

    default void on_level_unload(Level previousLevel){}
    default void on_level_load(Level newLevel){}
}
