package com.figer.game;

import com.figer.game.Game.Card;
import com.figer.game.Game.CardCollection;
import com.figer.game.Stage.Stage;
import com.figer.game.Stage.StageManager;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class GameStage extends Stage {

    private String connectedDevice;

    //Cards
    private CardCollection cardCollection;

    public GameStage(StageManager stageManager) {
        super(stageManager);
        cardCollection = new CardCollection();
    }

    @Override
    public void draw(Renderer renderer) {
        cardCollection.draw(renderer);
    }

    @Override
    public void update(Input input) {
        cardCollection.update(input);
        cardCollection.sort();
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
