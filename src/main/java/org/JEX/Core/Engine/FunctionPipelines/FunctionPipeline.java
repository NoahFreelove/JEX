package org.JEX.Core.Engine.FunctionPipelines;

/**
 * Function pipelines allow for the user to define how they want JEX to run game_updates. A standard one is provided
 * But if you wanted to skip certain parts of the update or render loop, you could do that here.
 * Different to RenderPipelines which are used to define how the render loop is run. These are purely for the update loop.
 */
public interface FunctionPipeline {


    void game_update(float delta_time, boolean isRenderUpdate);

    void endFrame();

    default void on_unload(){}
    default void on_load(){}
}
