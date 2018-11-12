package com.figer.game.Game;

import com.badlogic.gdx.utils.Array;
import com.figer.game.System.Renderer;

import java.util.Collections;
import java.util.Comparator;

public class CardCollection {
    private Array<Card> cards;

    public CardCollection() {
        cards = new Array<Card>();
    }

    public void draw(Renderer renderer){
        for(Card c:cards) c.draw(renderer);
    }

    public void sort(){
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Float.compare(o1.getX(), o2.getX());
            }
        });
    }

    public Card addCard(String name, float x, float y){
        Card card = new Card(name, x, y);
        cards.add(card);
        return card;
    }
}
