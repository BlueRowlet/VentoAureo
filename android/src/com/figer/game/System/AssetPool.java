package com.figer.game.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class AssetPool {
    //Textures
    Texture cards;

    //Texture Regions
    private ObjectMap<String, TextureRegion> cardRegion;

    public AssetPool(){
        cards = new Texture(Gdx.files.internal("cardBack.png"));

        cardRegion = new ObjectMap<>();
        cardRegion.put("cardBack", new TextureRegion(cards, 0,0,96,128));
        //SpriteSheet
//        cardRegions.put("DankCard1", new TextureRegion(cards,0, 0, 128, 128));
//        cardRegions.put("DankCard2", new TextureRegion(cards,0, 128, 128, 128));
//        cardRegions.put("DankCard3", new TextureRegion(cards, 128, 0, 128, 128));
//        cardRegions.put("DankCard4", new TextureRegion(cards,128, 128, 128, 128));
        for (TextureRegion region : cardRegion.values()) {
            region.flip(false, true);
        }
    }

    public void dispose(){
        cards.dispose();
    }

    public TextureRegion getCardRegion(String name) {
        return cardRegion.get(name);
    }

}
