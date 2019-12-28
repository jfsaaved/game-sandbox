package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class MovingObject {

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected int weight;

    protected SpriteHandler spriteHandler;
    protected MovementHandler movementHandler;

    public MovingObject(float x, float y, int width, int height, int weight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch spriteBatch);

}
