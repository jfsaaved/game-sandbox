package com.jfsaaved.movingobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends MovingObject {

    protected enum ActionState {
        IDLE, MOVE, JUMP
    }

    protected enum EnvironmentState {
        IN_AIR, ON_GROUND, IN_WATER
    }

    protected enum FreeFallState{
        GOING_UP, GOING_DOWN, NEUTRAL
    }

    private final float COL_FRAME_DELAY_MAX = 10f;
    private final float COL_FRAME_DELAY_MULTIPLIER = 70f;
    private final int STANDING_COL_FRAMES = 4;
    private final int WALKING_COL_FRAMES = 10;

    protected int colFrame;
    protected float colFrameDelay;

    protected Sprite[] standingSprite;
    protected Sprite[] walkingSprite;

    protected ActionState actionState;
    protected EnvironmentState environmentState;
    protected FreeFallState freeFallState;

    private int speed;

    // Change in Velocity over time
    private int acceleration;

    // Speed over time
    private int velocityLeft;
    private int velocityRight;
    private int velocityMax;

    public Player(float x, float y, int width, int height, TextureRegion textureRegion) {
        super(x, y, width, height);

        this.actionState = ActionState.IDLE;
        this.environmentState = EnvironmentState.ON_GROUND;
        this.freeFallState = FreeFallState.NEUTRAL;

        speed = 2;
        acceleration = 10 * speed;
        velocityMax = 150 * speed;
        velocityLeft = 0;
        velocityRight = 0;

        weight = 5;

        this.colFrame = 0;
        this.colFrameDelay = COL_FRAME_DELAY_MAX;

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
        checkFreeFallStates(dt);
        checkEnvironmentStates(dt);
        checkActionStates(dt);
        updateAnimations(dt);
    }

    private void updateAnimations(float dt) {
        if(actionState == ActionState.IDLE)
            updateStandingAnim(dt);
        else
            updateWalkingAnim(dt);
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
            if(actionState == ActionState.IDLE) {
                if(colFrame > (STANDING_COL_FRAMES - 1)) colFrame = 0;
                standingSprite[colFrame].setFlip(flip, false);
                spriteBatch.draw(standingSprite[colFrame], x, y);
            }
            else {
                if(colFrame > (WALKING_COL_FRAMES - 1)) colFrame = 0;
                walkingSprite[colFrame].setFlip(flip, false);
                spriteBatch.draw(walkingSprite[colFrame], x, y);
            }
        }
    }

    private void setActionState(ActionState state){
        this.actionState = state;
    }

    private void checkActionStates(float dt){
        if(actionState == ActionState.IDLE) updateOnIdle(dt);
        if(actionState == ActionState.MOVE) updateOnMove(dt);
        if(actionState == ActionState.JUMP) updateOnJump(dt);
    }

    private void checkFreeFallStates(float dt){
        freeFallState = FreeFallState.GOING_DOWN;
        if(collidingDown) freeFallState = FreeFallState.NEUTRAL;
    }

    private void checkEnvironmentStates(float dt){
        environmentState = EnvironmentState.ON_GROUND;
        if(freeFallState == FreeFallState.GOING_UP || freeFallState == FreeFallState.GOING_DOWN)
            environmentState = EnvironmentState.IN_AIR;
    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z))
            if(environmentState == EnvironmentState.ON_GROUND)
                if(freeFallState == FreeFallState.NEUTRAL)
                    setActionState(ActionState.JUMP);
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) flip = true;
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) flip = false;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            setActionState(ActionState.MOVE);
        else setActionState(ActionState.IDLE);
    }

    private void updateOnIdle(float dt) {
        if(velocityLeft > 0) {
            velocityLeft = velocityLeft - acceleration;
            moveLeft(velocityLeft * dt);
        }
        if(velocityLeft < 0) velocityLeft = 0;

        if(velocityRight > 0) {
            velocityRight = velocityRight - acceleration ;
            moveRight(velocityRight * dt);
        }
        if(velocityRight < 0) velocityRight = 0;
    }

    private void updateOnMove(float dt){
        if(!flip) {
            if(velocityRight < velocityMax) {
                if(velocityLeft > 0)
                    velocityLeft = velocityLeft - acceleration;
                velocityRight = velocityRight + acceleration;
            } else {
                velocityRight = velocityMax;
                velocityLeft = 0;
            }
            moveRight(velocityRight * dt);
        } else {
            if(velocityLeft < velocityMax) {
                if(velocityRight > 0)
                    velocityRight = velocityRight - acceleration;
                velocityLeft = velocityLeft + acceleration;
            } else {
                velocityLeft = velocityMax;
                velocityRight = 0;
            }
            moveLeft(velocityLeft * dt);
        }
    }

    private void updateOnJump(float dt) {

    }


}


