package com.figer.game.Game;

import com.figer.game.System.AssetPool;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class Card {
    public static final int W = 96, H = 128;

    private String name;
    private float x, y, scale;

    public Card(String name, float x, float y){
        this.name = name;
        this.x = x;
        this.y = y;
        scale = 1;
    }

    public void draw(Renderer renderer) {
            renderer.drawCard(x, y, name, scale);
    }

    public void drawPreview(Renderer renderer){
        renderer.drawCard(500, 100, name, 3f);
    }

    public boolean touchInside(Input input){
        return (input.getX() > x && input.getX() < x + W &&
                    input.getY() > y && input.getY() < y + H);
    }

    public String getName() {
        return name;
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}
