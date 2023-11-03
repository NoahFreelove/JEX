package org.JEX.Core.GameObjects.Scripting;

public class LambdaScript extends Script {
    private LambdaScriptI script;

    public LambdaScript(LambdaScriptI script){
        this.script = script;
    }

    @Override
    public void start() {
        script.start();
    }

    @Override
    public void update(float delta_time) {
        script.update(delta_time);
    }
}
