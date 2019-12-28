package com.jfsaaved.movingobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {

    private MovingObject movingObject;

    public InputHandler(MovingObject movingObject){
        this.movingObject = movingObject;
    }

    public void update(float dt){
        boolean pressedButton = false;

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            movingObject.spriteHandler.setFlip(true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            movingObject.spriteHandler.setFlip(false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            movingObject.spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING);
            movingObject.movementHandler.accelerateAtAngle(180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            movingObject.spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING);
            movingObject.movementHandler.accelerateAtAngle(0);
        }

        if(!pressedButton) movingObject.spriteHandler.setAnimationState(SpriteHandler.AnimationState.STANDING);
    }

}
