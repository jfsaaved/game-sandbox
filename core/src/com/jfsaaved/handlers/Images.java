package com.jfsaaved.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

public class Images {

    private HashMap<String, TextureAtlas> atlases;

    public Images(){
        atlases = new HashMap<String, TextureAtlas>();
    }

    public Images(String path, String key){
        atlases = new HashMap<>();
        atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
    }

    public void loadAtlas(String path, String key){
        atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
    }

    public TextureAtlas getAtlas(String key){
        return atlases.get(key);
    }

}
