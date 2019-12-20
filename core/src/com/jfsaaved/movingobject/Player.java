package com.jfsaaved.movingobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends MovingObject {

    protected enum CurrentState {
        STANDING, WALKING, JUMP, GOING_UP, FALLING_DOWN;
    }

    private final float COL_FRAME_DELAY_MAX = 10f;
    private final float COL_FRAME_DELAY_MULTIPLIER = 70f;
    private final int STANDING_COL_FRAMES = 4;
    private final int WALKING_COL_FRAMES = 10;

    protected int colFrame;
    protected float colFrameDelay;

    protected Sprite[] standingSprite;
    protected Sprite[] walkingSprite;

    protected CurrentState currentState;

    private float horizontalSpeed;
    private float horizontalMaxSpeed;
    private float horizontalAcceleration;
    private float horizontalDeceleration;

    private float verticalStrength;
    private float gravityStrength;
    private float weight;

    public Player(float x, float y, int width, int height, TextureRegion textureRegion) {
        super(x, y, width, height);

        this.currentState = CurrentState.STANDING;

        this.colFrame = 0;
        this.colFrameDelay = COL_FRAME_DELAY_MAX;

        this.horizontalSpeed = 0f;
        this.horizontalMaxSpeed = 200f;
        this.horizontalAcceleration = 10f;
        this.horizontalDeceleration = 5f;

        this.verticalStrength = 0f;
        this.gravityStrength = 25f;
        this.weight = 100f;

        // Store each frame into a Sprite array
        standingSprite = new Sprite[STANDING_COL_FRAMES];
        for(int col = 0; col < STANDING_COL_FRAMES; col++) {
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        }

        walkingSprite = new Sprite[WALKING_COL_FRAMES];
        for(int col = 0; col < WALKING_COL_FRAMES; col++) {
            walkingSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
        }
    }

    public void update(float dt){
        updateJumping(dt);
        updateStanding(dt);
        updateWalking(dt);
        updateAnimations(dt);
    }

    private void updateWalking(float dt) {
        if (currentState == CurrentState.WALKING){
            if (horizontalSpeed < horizontalMaxSpeed)
                horizontalSpeed = horizontalSpeed + (horizontalAcceleration);
        } else if(currentState == CurrentState.STANDING) {
            if(horizontalSpeed > 0)
                horizontalSpeed = horizontalSpeed - (horizontalDeceleration);
        }
        if(flip) moveLeft(horizontalSpeed * dt);
        if(!flip)moveRight(horizontalSpeed * dt);
    }

    private void updateJumping(float dt){
        if(currentState.equals(CurrentState.JUMP)) {
            verticalStrength = (gravityStrength + weight) + 80f;
        }
        if(verticalStrength > 0f) {
            verticalStrength = (verticalStrength - (gravityStrength * dt )) - (weight * dt);
            currentState = CurrentState.GOING_UP;
        }
        if(verticalStrength <= (gravityStrength + weight) && verticalStrength > 1f) {
            verticalStrength = (verticalStrength) - (gravityStrength * dt) - (weight * dt);
            currentState = CurrentState.FALLING_DOWN;
        }
        if(verticalStrength <= 1f)
            verticalStrength = 0;

        if(verticalStrength > 10f) moveUp(verticalStrength * dt);
        if(!collidingDown) moveDown((gravityStrength + weight) * dt);
    }

    private void updateStanding(float dt){
        if(collidingDown)
            currentState = CurrentState.STANDING;
    }

    private void updateAnimations(float dt) {
        if(currentState.equals(CurrentState.WALKING)
            || currentState.equals(CurrentState.GOING_UP)
            || currentState.equals(CurrentState.JUMP))
            updateWalkingAnim(dt);
        else if(currentState.equals(CurrentState.STANDING) || currentState.equals(CurrentState.FALLING_DOWN))
            updateStandingAnim(dt);
    }

    protected void updateStandingAnim(float dt){
        colFrameDelay -= (dt * COL_FRAME_DELAY_MULTIPLIER);
        if(colFrameDelay < 0) {
            colFrame++;
            colFrameDelay = COL_FRAME_DELAY_MAX;
        }
        if(colFrame > (STANDING_COL_FRAMES - 1)) colFrame = 0;
    }

    protected void updateWalkingAnim(float dt){
        colFrameDelay -= (dt * COL_FRAME_DELAY_MULTIPLIER);
        if(colFrameDelay < 0) {
            colFrame++;
            colFrameDelay = COL_FRAME_DELAY_MAX;
        }
        if(colFrame > (WALKING_COL_FRAMES - 1)) colFrame = 0;
    }

    public void draw(SpriteBatch spriteBatch){
        if(!hide) {
            if(currentState == CurrentState.STANDING || currentState.equals(CurrentState.FALLING_DOWN)) {
                standingSprite[colFrame].setFlip(flip, false);
                spriteBatch.draw(standingSprite[colFrame], x, y);
            }
            else if(currentState.equals(CurrentState.WALKING)
                    || currentState.equals(CurrentState.GOING_UP)
                    || currentState.equals(CurrentState.JUMP)){
                walkingSprite[colFrame].setFlip(flip, false);
                spriteBatch.draw(walkingSprite[colFrame], x, y);
            }
        }
    }

    public void handleInput(float dt){
        if(currentState != CurrentState.GOING_UP && currentState != CurrentState.FALLING_DOWN) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                flip = true;
                horizontalSpeed = 100f;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                flip = false;
                horizontalSpeed = 100f;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                currentState = CurrentState.JUMP;
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                flip = true;
                horizontalSpeed = 100f;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                flip = false;
                horizontalSpeed = 100f;
            }
        }
    }

}


