package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends MovingObject {

    private InputHandler inputHandler;

    public Player(TextureRegion textureRegion, int x, int y, int width, int height, int weight) {
        super(x, y, width, height, weight);
        inputHandler = new InputHandler(this);
        movementHandler = new MovementHandler(this);
        spriteHandler = new SpriteHandler()
                .handleStandingSprite(textureRegion, width, height, 4)
                .handleWalkingSprite(textureRegion, width, height, 10);
    }

    @Override
    public void update(float dt){
        inputHandler.update(dt);
        spriteHandler.update(dt);
        movementHandler.update(dt);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(spriteHandler.getActiveSprite(), x, y);
    }

}


