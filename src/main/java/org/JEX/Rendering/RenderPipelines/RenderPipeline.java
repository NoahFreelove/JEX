package org.JEX.Rendering.RenderPipelines;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Rendering.Renderers.Renderer;

/**
 * Render Pipelines are meant to be purely functional. They should not contain any state, just render a set of
 * renderers
 */
public interface RenderPipeline {
    void preFrameInit();

    void render(Renderer[] renderers, LevelConfig config);
}
