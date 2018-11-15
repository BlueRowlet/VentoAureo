package com.figer.game;

import com.figer.game.GUI.Button;
import com.figer.game.GUI.List;
import com.figer.game.GUI.Signal;
import com.figer.game.Stage.Stage;
import com.figer.game.Stage.StageManager;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class DeckBuilderStage extends Stage {
    private List allCards;
    private List defaultCollection;
    private List newCollection;
    private List randomCards;

    private Button btnDefaultCollection;
    private Button btnNewCollection;
    private Button btnBack;

    private int VISIBLE;
    private int CREATOR;

    public DeckBuilderStage(StageManager stageManager) {
        super(stageManager);

        allCards = new List(0,0,200);
        defaultCollection = new List(600,0,200,25);
        newCollection = new List(0,0,200,25);
        randomCards = new List(0,0,200);

        VISIBLE = 0;
        CREATOR = 0;

        btnDefaultCollection = new Button(0, 25, 200, 50, "Default Collection");
        btnNewCollection = new Button(0, 100, 200, 50, "Create New");
        btnBack = new Button(0,175, 200,50,"Back");

        for(int i=0;i<20;i++) {
            defaultCollection.addCollectionElement("Ragnarok");
        }

        for(int i=0;i<150;i++){
            allCards.addCollectionElement("BITCH");
            allCards.setSize(allCards.getSize()+1);
            System.out.println(allCards.getElementByIndex(i));
        }
    }

    @Override
    public void draw(Renderer renderer) {
        btnDefaultCollection.draw(renderer);
        btnNewCollection.draw(renderer);
        btnBack.draw(renderer);

        switch(VISIBLE){
            case 1:
                defaultCollection.draw(renderer);
                break;
            case 2:
                newCollection.draw(renderer);
                break;
        }

        switch(CREATOR){
            case 2:
                for(int i=0; i<randomCards.getSize();i++) {
                    renderer.drawCard(300, 25+i*143, "cardBack", 1);
                }
        }
    }

    @Override
    public void update(Input input) {
        btnDefaultCollection.update(input);
        btnNewCollection.update(input);
        btnBack.update(input);

        if(btnDefaultCollection.consumeSignal() != Signal.NULL){
            VISIBLE = 1;
        }

        if(btnNewCollection.consumeSignal() != Signal.NULL){
            CREATOR = 2;
            randomCardPicker();
        }

        if(btnBack.consumeSignal() != Signal.NULL){
            stageManager.requestNumber(StageManager.INITIAL);
        }

        if(!btnDefaultCollection.touchInside(input) && !btnNewCollection.touchInside(input)){
            VISIBLE = 0;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onActivating() {

    }

    @Override
    public void onDeactivating() {

    }

    public void randomCardPicker(){
        int size = 3;
        randomCards.clear();
        randomCards.setSize(size);
        for (int i = 0; i < size; i++) {
            randomCards.addCollectionElement(allCards.getElementByIndex(allCards.randomCardPicker()));
        }

        for(int j=0; j<randomCards.getSize(); j++) {
            System.out.println(randomCards.getElementByIndex(j));
        }
    }
}
