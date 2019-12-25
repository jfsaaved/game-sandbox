package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHandler {

    private enum AnimationState{
        STANDING, WALKING
    }

    private Sprite[] standingSprite;
    private Sprite[] walkingSprite;

    private int currentFrame;
    protected int currentDelay;
    private Sprite[] activeSprite;

    private boolean flip;
    private boolean hide;

    public AnimationHandler(){
        flip = false;
        hide = false;
    }

    public AnimationHandler handleStandingSprite(TextureRegion textureRegion, int width, int height, int frames) {
        standingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++) {
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        }
        return this;
    }

    public AnimationHandler handleWalkingSprite(TextureRegion textureRegion, int width, int height, int frames) {
        walkingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++) {
            walkingSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
        }
        return this;
    }

    public void setState(AnimationState animationState){
        if(animationState == AnimationState.WALKING)
            activeSprite = walkingSprite;
        else if(animationState == AnimationState.STANDING)
            activeSprite = standingSprite;
    }

    public void setStandingSprite(TextureRegion textureRegion, int width, int height, int frames){
        standingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++) {
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        }
    }

    public void setMovingSprite(TextureRegion textureRegion, int width, int height, int frames){
        walkingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++) {
            walkingSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
        }
    }

    public void update(float dt){
        if(activeSprite == null){
            activeSprite = standingSprite;
        }

        final int COL_FRAME_DELAY_MAX = 7;
        final int COL_FRAME_DELAY_MULTIPLIER = 70;
        currentDelay -= (dt * COL_FRAME_DELAY_MULTIPLIER);
        if(currentDelay < 0) {
            currentFrame++;
            currentDelay = COL_FRAME_DELAY_MAX;
        }
        if(currentFrame > activeSprite.length)
            currentFrame = 0;
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
