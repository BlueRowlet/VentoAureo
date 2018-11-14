package com.figer.game;

import com.figer.game.GUI.Button;
import com.figer.game.GUI.List;
import com.figer.game.GUI.Signal;
import com.figer.game.Game.CardCollection;
import com.figer.game.Stage.Stage;
import com.figer.game.Stage.StageManager;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class DeckBuilderStage extends Stage {
    private List allCards;
    private List collection1;
    private List collection2;
    private List collection3;
    private List collection4;
    private List randomCards;

    private Button btnCollection1;
    private Button btnCollection2;
    private Button btnCollection3;
    private Button btnCollection4;

    private int VISIBLE;
    private int CREATOR;

    public DeckBuilderStage(StageManager stageManager) {
        super(stageManager);

        allCards = new List(0,0,200);
        collection1 = new List(0,0,200,25);
        collection2 = new List(0,0,200,25);
        collection3 = new List(0,0,200,25);
        collection4 = new List(0,0,200,25);
        randomCards = new List(0,0,200);

        VISIBLE = 0;
        CREATOR = 0;

        btnCollection1 = new Button(0, 500, 200, 50, "Default Collection");
        btnCollection2 = new Button(200, 500, 200, 50, "Create New");
        btnCollection3 = new Button(400, 500, 200, 50, "Create New");
        btnCollection4 = new Button(600, 500, 200, 50, "Create New");

        for(int i=0;i<20;i++) {
            collection1.addCollectionElement("Ragnarok");
        }

        for(int i=0;i<150;i++){
            allCards.addCollectionElement("BITCH");
            allCards.setSize(allCards.getSize()+1);
            System.out.println(allCards.getElementByIndex(i));
        }
    }

    @Override
    public void draw(Renderer renderer) {
        btnCollection1.draw(renderer);
        btnCollection2.draw(renderer);
        btnCollection3.draw(renderer);
        btnCollection4.draw(renderer);

        switch(VISIBLE){
            case 1:
                collection1.draw(renderer);
                break;
            case 2:
                collection2.draw(renderer);
                break;
            case 3:
                collection3.draw(renderer);
                break;
            case 4:
                collection4.draw(renderer);
                break;
        }

        switch(CREATOR){
            case 2:
                for(int i=0; i<randomCards.getSize();i++) {
                    renderer.drawCard(300+i*100, 25, "cardBack", 1);
                }
        }
    }

    @Override
    public void update(Input input) {
        btnCollection1.update(input);
        btnCollection2.update(input);
        btnCollection3.update(input);
        btnCollection4.update(input);

        if(btnCollection1.consumeSignal() != Signal.NULL){
            VISIBLE = 1;
        }

        if(btnCollection2.consumeSignal() != Signal.NULL){
            CREATOR = 2;
            randomCardDrawer();
        }

        if(btnCollection3.consumeSignal() != Signal.NULL){
            VISIBLE = 3;
        }
        if(btnCollection4.consumeSignal() != Signal.NULL){
            VISIBLE = 4;
        }
        if(!btnCollection1.touchInside(input) && !btnCollection2.touchInside(input)
        && !btnCollection3.touchInside(input) && !btnCollection4.touchInside(input)){
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

    public void randomCardDrawer(){
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
