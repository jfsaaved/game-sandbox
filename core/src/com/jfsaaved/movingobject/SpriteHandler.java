package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteHandler {

    protected enum AnimationState{
        STANDING, WALKING
    }

    private Sprite[] standingSprite;
    private Sprite[] walkingSprite;

    private int currentFrame;
    protected int currentDelay;
    private Sprite[] activeSprite;

    private boolean flip;
    private boolean hide;

    private AnimationState animationState;

    public SpriteHandler(){
        flip = false;
        hide = false;
    }

    public SpriteHandler handleStandingSprite(TextureRegion textureRegion, int width, int height, int frames) {
        standingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        return this;
    }

    public SpriteHandler handleWalkingSprite(TextureRegion textureRegion, int width, int height, int frames) {
        walkingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            walkingSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
        return this;
    }

    public void setAnimationState(AnimationState animationState){
        if(animationState == AnimationState.WALKING)
            activeSprite = walkingSprite;
        else if(animationState == AnimationState.STANDING)
            activeSprite = standingSprite;
    }

    public AnimationState getAnimationState(){
        return this.animationState;
    }

    public void setStandingSprite(TextureRegion textureRegion, int width, int height, int frames){
        standingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
    }

    public void setMovingSprite(TextureRegion textureRegion, int width, int height, int frames){
        walkingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            walkingSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
    }

    public void update(float dt){
        final int COL_FRAME_DELAY_MAX = 10;
        final int COL_FRAME_DELAY_MULTIPLIER = 100;

        if(activeSprite == null)
            activeSprite = standingSprite;

        currentDelay -= (dt * COL_FRAME_DELAY_MULTIPLIER);

        if(currentDelay < 0) {
            currentFrame++;
            currentDelay = COL_FRAME_DELAY_MAX;
        }

        if(currentFrame > activeSprite.length-1)
            currentFrame = 0;

        activeSprite[currentFrame].setFlip(flip, false);
    }

    public Sprite getActiveSprite(){
        return activeSprite[currentFrame];
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

}
