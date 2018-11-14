package com.figer.game.Game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

import java.util.Collections;
import java.util.Comparator;

public class CardCollection {
    private Array<Card> cards;
    private boolean hovered;

    public CardCollection() {
        cards = new Array<Card>();
    }

    public void draw(Renderer renderer){
        for(Card c:cards) {
            c.draw(renderer);
            if(hovered) {
                c.drawPreview(renderer);
            }
        }
    }

    public void drawList(Renderer renderer){
        int yPos = 0;
        for(Card c:cards){
            renderer.drawText(c.getName(), 5, 5+yPos*20, Renderer.WHITE);
            yPos++;
        }
    }

    public void update(Input input){
        hovered = false;
        for(int i=cards.size-1; i>=0; i--){
            Card c = cards.get(i);
            if(c.touchInside(input) && !hovered){
                c.setScale(1.2f);
                hovered = true;
            } else {
                c.setScale(1f);
            }
        }
    }

    public void sort(){
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                float c1 = o1.getX();
                float c2 = o2.getX();
                if (o1.getScale() > 1) c1 = Renderer.WIDTH;
                if (o2.getScale() > 1) c2 = Renderer.WIDTH;
                return Float.compare(c1, c2);
            }
        });
    }

    public Card addCard(String name, float x, float y){
        Card card = new Card(name, x, y);
        cards.add(card);
        return card;
    }

    public Card addEmptyCard(String name){
        Card card = new Card(name);
        cards.add(card);
        return card;
    }

    public int randomCardPicker(){
        return MathUtils.random(0, cards.size - 1);
    }
}
