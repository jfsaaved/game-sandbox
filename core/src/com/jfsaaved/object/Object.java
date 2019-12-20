package com.jfsaaved.object;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Object {

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected Rectangle hurtBox;

    public Object(float x, float y, int width, int height, TextureRegion textureRegion){
        this.hurtBox = new Rectangle(x, y, width, height);
    }

    public Rectangle getHurtBox(){
        return hurtBox;
    }

    public void drawBox(ShapeRenderer shapeRenderer){
        shapeRenderer.rect(hurtBox.getX(), hurtBox.getY(), hurtBox.getWidth(), hurtBox.getHeight());
    }
}
