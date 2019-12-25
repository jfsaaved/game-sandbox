package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jfsaaved.Sandbox;

public abstract class MovingObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int weight;


    protected AnimationHandler animationHandler;
    protected CollisionHandler collisionHandler;
    protected InputHandler inputHandler;
    protected GravityHandler gravityHandler;
    protected MovementHandler movementHandler;

    public MovingObject(TextureRegion textureRegion, int x, int y, int width, int height, int weight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.weight = weight;
        animationHandler = new AnimationHandler()
                .handleStandingSprite(textureRegion, width, height, 4)
                .handleWalkingSprite(textureRegion, width, height, 10);
        inputHandler = new InputHandler(this);
        movementHandler = new MovementHandler(this);
        collisionHandler = new CollisionHandler();
        gravityHandler = new GravityHandler();
    }

    public void update(float dt){
        inputHandler.update(dt);
        animationHandler.update(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(animationHandler.getActiveSprite(), x, y);
    }

}
