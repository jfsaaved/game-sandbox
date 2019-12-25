package com.jfsaaved.movingobject;

import com.jfsaaved.Sandbox;

public class Player extends MovingObject {

    public Player(int x, int y, int width, int height, int weight) {
        super(Sandbox.images.getAtlas("assets").findRegion("player"), x, y, width, height, weight);
    }

}


