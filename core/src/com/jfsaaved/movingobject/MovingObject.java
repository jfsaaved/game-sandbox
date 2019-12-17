package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class MovingObject {

    protected enum CurrentState {
        STANDING, WALKING, JUMPING;
    }

    protected Rectangle hurtBox;
    protected float x;
    protected float y;
    protected int width;
    protected int height;

    protected Sprite[] standingSprite;
    protected Sprite[] walkingSprite;

    protected int colFrame;
    protected float colFrameDelay;

    protected CurrentState currentState;
    protected boolean flip;
    protected boolean hide;

    public MovingObject(float x, float y, int width, int height, TextureRegion textureRegion){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colFrame = 0;
        this.colFrameDelay = 5f;

        this.currentState = CurrentState.STANDING;
        this.hurtBox = new Rectangle(x, y, width, height);
        this.hide = false;
        this.flip = false;

        // Store each frame into a Sprite array
        standingSprite = new Sprite[4];
        for(int col = 0; col < 4; col++) {
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        }

        walkingSprite = new Sprite[10];
        for(int col = 0; col < 10; col++) {
            walkingSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
        }
    }

    public void update(float dt){
        switch(currentState) {
            case STANDING :
                updateStandingAnim(dt);
                break;
            case WALKING :
                updateWalkingAnim(dt);
                break;
        }
    }

    protected void updateStandingAnim(float dt){
        colFrameDelay -= 70f * dt;
        if(colFrameDelay < 0) {
            colFrame++;
            colFrameDelay = 10f;
        }
        if(colFrame > 3) colFrame = 0;
    }

    protected void updateWalkingAnim(float dt){
        colFrameDelay -= 70f * dt;
        if(colFrameDelay < 0) {
            colFrame++;
            colFrameDelay = 10f;
        }
        if(colFrame > 9) colFrame = 0;
    }

    public void draw(SpriteBatch spriteBatch){
        if(!hide) {
            if(currentState == CurrentState.STANDING) {
                standingSprite[colFrame].setFlip(flip, false);
                spriteBatch.draw(standingSprite[colFrame], x, y);
            }
            else if(currentState == CurrentState.WALKING) {
                walkingSprite[colFrame].setFlip(flip, false);
                spriteBatch.draw(walkingSprite[colFrame], x, y);
            }
        }
    }

    public void drawBox(ShapeRenderer shapeRenderer){
        if(!hide)
            shapeRenderer.rect(hurtBox.getX(), hurtBox.getY(), hurtBox.getWidth(), hurtBox.getHeight());
    }

    public void moveRight(){
        this.x = this.x + 1;
        this.hurtBox.setX(this.hurtBox.getX() + 1);
    }

    public void moveLeft(){
        this.x = this.x - 1;
        this.hurtBox.setX(this.hurtBox.getX() - 1);
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public boolean contains(Rectangle box2){
        return this.hurtBox.contains(box2);
    }

    public boolean overlap(Rectangle box2){
        return this.hurtBox.overlaps(box2);
    }

}
