package com.jfsaaved.movingobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jfsaaved.Sandbox;

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

    private final float COL_FRAME_DELAY_MAX = 7f;
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
    private int jumpStrength;

    // Change in Velocity over time
    private int acceleration;

    // Speed over time
    private int velocityLeft;
    private int velocityRight;
    private int velocityUp;
    private int velocityDown;
    private int velocityMax;
    private int jumpMax;

    public Player(float x, float y, int width, int height, TextureRegion textureRegion) {
        super(x, y, width, height);

        this.actionState = ActionState.IDLE;
        this.environmentState = EnvironmentState.ON_GROUND;
        this.freeFallState = FreeFallState.NEUTRAL;

        speed = 2;
        weight = 4;
        jumpStrength = 2;
        acceleration = (10 - weight) * speed;
        velocityMax = (150 - (weight * 2)) * speed;
        jumpMax = (80 - (weight * 2)) * jumpStrength;
        velocityUp = 0;
        velocityDown = 0;
        velocityLeft = 0;
        velocityRight = 0;

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

    public void reset(){
        setX(Sandbox.WIDTH/2);
        setY(Sandbox.HEIGHT/2);

        this.actionState = ActionState.IDLE;
        this.environmentState = EnvironmentState.ON_GROUND;
        this.freeFallState = FreeFallState.NEUTRAL;

        speed = 2;
        weight = 4;
        jumpStrength = 2;
        acceleration = (10 - weight) * speed;
        velocityMax = (150 - (weight * 2)) * speed;
        jumpMax = (80 - (weight * 2)) * jumpStrength;
        velocityUp = 0;
        velocityDown = 0;
        velocityLeft = 0;
        velocityRight = 0;

        this.colFrame = 0;
        this.colFrameDelay = COL_FRAME_DELAY_MAX;
    }

    public void update(float dt){
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
        if(actionState == ActionState.IDLE && environmentState == EnvironmentState.ON_GROUND) updateOnIdle(dt);
        if(actionState == ActionState.JUMP) updateOnJump(dt);
        if(actionState == ActionState.MOVE && environmentState == EnvironmentState.ON_GROUND) updateOnMove(dt);
        updateJump(dt);
    }

    private void checkEnvironmentStates(float dt){
        environmentState = EnvironmentState.ON_GROUND;
        if(freeFallState == FreeFallState.GOING_UP || freeFallState == FreeFallState.GOING_DOWN)
            environmentState = EnvironmentState.IN_AIR;
    }

    public void handleInput(float dt){
        setActionState(ActionState.IDLE);
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) flip = true;
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) flip = false;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            setActionState(ActionState.MOVE);
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z))
            if(environmentState == EnvironmentState.ON_GROUND)
                if(freeFallState == FreeFallState.NEUTRAL)
                    setActionState(ActionState.JUMP);

        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) reset();

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
        environmentState = EnvironmentState.IN_AIR;
        freeFallState = FreeFallState.GOING_UP;
    }

    private void updateJump(float dt){
        if(environmentState == EnvironmentState.IN_AIR){
            if(freeFallState == FreeFallState.GOING_UP) {
                int extraUmpf = 0;
                if(velocityLeft > 0) extraUmpf = velocityLeft/8;
                else if(velocityRight > 0) extraUmpf = velocityRight/8;
                if(velocityUp < jumpMax + extraUmpf) {
                    velocityUp = velocityUp + acceleration;
                }
                if(velocityUp >= jumpMax + extraUmpf) {
                    freeFallState = FreeFallState.GOING_DOWN;
                }
            }
            if(freeFallState == FreeFallState.GOING_DOWN){
                if(velocityUp > 0)
                    velocityUp = velocityUp - acceleration;
            }

            if(velocityLeft > 0) {
                velocityLeft = velocityLeft - (acceleration/(jumpStrength*2));
                moveLeft(velocityLeft * dt);
            }
            if(velocityLeft < 0) velocityLeft = 0;

            if(velocityRight > 0) {
                velocityRight = velocityRight - (acceleration/(jumpStrength*2)) ;
                moveRight(velocityRight * dt);
            }
            if(velocityRight < 0) velocityRight = 0;
        }

        if(collidingDown){
            if(velocityDown > 0) {
                velocityUp = 0;
                velocityDown = 0;
                environmentState = EnvironmentState.ON_GROUND;
                freeFallState = FreeFallState.NEUTRAL;
            }
        }

        moveUp(velocityUp * dt);
    }

    public int getWeight(){
        return weight;
    }

    public int getSpeed(){
        return speed;
    }

    public int getAcceleration(){
        return acceleration;
    }

    public int getVelocityMax(){
        return velocityMax;
    }

    public void setVelocityUp(int velocityUp){
        this.velocityUp = velocityUp;
    }

    public int getVelocityUp(){
        return velocityUp;
    }

    public void setVelocityDown(int velocityDown){
        this.velocityDown = velocityDown;
    }

    public int getVelocityDown(){
        return velocityDown;
    }

}


