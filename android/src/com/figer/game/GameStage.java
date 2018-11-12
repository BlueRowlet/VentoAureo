package com.figer.game;

import android.content.Context;

import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class GameStage extends Stage {

    private String connectedDevice;

    public GameStage(StageManager stageManager) {
        super(stageManager);
    }

    @Override
    public void draw(Renderer render) {
    }

    @Override
    public void update(Input input) {
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onActivating() {
        this.connectedDevice = stageManager.getConnectedDevice();
    }

    @Override
    public void onDeactivating() {

    }
}
