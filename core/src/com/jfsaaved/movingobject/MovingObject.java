package com.jfsaaved.movingobject;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class MovingObject {

    protected Rectangle hurtBox;

    protected float x;
    protected float y;
    protected int width;
    protected int height;

    protected boolean flip;
    protected boolean hide;

    protected boolean collidingUp;
    protected boolean collidingDown;
    protected boolean collidingLeft;
    protected boolean collidingRight;

    protected int weight;

    public MovingObject(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.hurtBox = new Rectangle(x, y, width, height);

        this.hide = false;
        this.flip = false;

        this.collidingUp = false;
        this.collidingDown = false;
        this.collidingLeft = false;
        this.collidingRight = false;

        this.weight = 5;
    }

    public void drawBox(ShapeRenderer shapeRenderer){
        //if(!hide)
            //shapeRenderer.rect(hurtBox.getX(), hurtBox.getY(), hurtBox.getWidth(), hurtBox.getHeight());
    }

    public void moveRight(float amount){
        if(!collidingRight) {
            this.x = this.x + amount;
            this.hurtBox.x = x;
        }
    }

    public void moveLeft(float amount){
        if(!collidingLeft) {
            this.x = this.x - amount;
            this.hurtBox.x = x;
        }
    }

    public void moveUp(float amount){
        if(!collidingUp) {
            this.y += amount;
            this.hurtBox.y = y;
        }
    }

    public void moveDown(float amount){
        if(!collidingDown) {
            this.y -= amount;
            this.hurtBox.y = y;
        }
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

    public void setCollidingUp(boolean value) {
        this.collidingUp = value;
    }

    public void setCollidingDown(boolean value) {
        this.collidingDown = value;
    }

    public boolean getCollidingDown() { return this.collidingDown; }

    public void setCollidingLeft(boolean value) {
        this.collidingLeft = value;
    }

    public void setCollidingRight(boolean value) {
        this.collidingRight = value;
    }

    public Rectangle getHurtBox(){
        return hurtBox;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight(){
        return this.weight;
    }

}
