package com.figer.game.Stage;

import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public abstract class Stage {
    protected StageManager stageManager;

    public Stage(StageManager stageManager){
        this.stageManager = stageManager;
    }

    public abstract void draw(Renderer render);
    public abstract void update(Input input);
    public abstract void dispose();
    public abstract void onActivating();
    public abstract void onDeactivating();
}
