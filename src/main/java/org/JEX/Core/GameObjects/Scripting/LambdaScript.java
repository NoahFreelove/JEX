package org.JEX.Core.GameObjects.Scripting;

public final class LambdaScript extends Script {
    private ILambdaScript script;

    public LambdaScript(ILambdaScript script){
        this.script = script;
    }

    private LambdaScript(){
        super();
    }

    @Override
    public void start() {
        if(script == null)
            return;
        script.start();
    }

    @Override
    public void update(float delta_time) {
        if(script == null)
            return;
        script.update(delta_time);
    }

    public void setLambdaScript(ILambdaScript newScript){
        this.script = newScript;
    }
}
