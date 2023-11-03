package org.JEX.Rendering.RenderPipelines;

import org.JEX.Core.Configs.LevelConfig;
import org.JEX.Core.Util.JEXIterator;
import org.JEX.Rendering.Renderers.Renderer;

public class GLRenderPipeline implements RenderPipeline {

    public GLRenderPipeline() {

    }

    @Override
    public void preFrameInit() {

    }

    @Override
    public void render(JEXIterator<Renderer> renderers, LevelConfig config) {
        while (renderers.hasNext()){
            renderers.next().render(config);
        }
    }
}
