package com.figer.game.Game;

import com.figer.game.System.Renderer;

public class Card {
    private String name;
    private float x, y;

    public Card(String name, float x,float y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void draw(Renderer renderer){
        renderer.drawCard(x, y, name);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
