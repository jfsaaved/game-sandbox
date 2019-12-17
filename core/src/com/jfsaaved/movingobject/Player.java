package com.jfsaaved.movingobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.jfsaaved.movingobject.MovingObject.CurrentState.STANDING;
import static com.jfsaaved.movingobject.MovingObject.CurrentState.WALKING;

public class Player extends MovingObject {

    public Player(float x, float y, int width, int height, TextureRegion textureRegion) {
        super(x, y, width, height, textureRegion);
    }

    public void handleMoveLeft() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft();
            flip = true;
            currentState = WALKING;
        }
    }

    public void handleMoveRight() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight();
            flip = false;
            currentState = WALKING;
        }
    }

    public void handleStanding() {
        currentState = STANDING;
    }

    public void handleInput(float dt){
        currentState = STANDING;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft();
            flip = true;
            currentState = WALKING;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight();
            flip = false;
            currentState = WALKING;
        }
    }

}


