package com.jfsaaved.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.jfsaaved.Sandbox;

public class TextImage {


    protected Rectangle textBox;
    protected Sprite[][] textSheet;

    protected int textNumCols;
    protected int testNumRows;
    protected int textLetterWidth;
    protected int textLetterHeight;

    protected float textScale;
    protected float textSpacing;
    protected Color textColor;
    protected String text;

    protected float textDelay;
    protected float textDelayCurrent;
    protected int textLength;
    protected boolean textComplete;

    public static final float TEXT_SPACING_DEFAULT = -17.5f;
    public static final float TEXT_SCALE_DEFAULT = 0.25f;
    public static final float TEXT_DELAY_DEFAULT = 0.10f;

    public TextImage(String text, float x, float y) {

        // Text delays
        this.textDelay = TEXT_DELAY_DEFAULT;
        this.textDelayCurrent = TEXT_DELAY_DEFAULT;
        this.textLength = 0;
        this.textComplete = false;

        // Presentation variables
        this.text = text;
        this.textScale = TEXT_SCALE_DEFAULT;
        this.textSpacing = TEXT_SPACING_DEFAULT;
        this.textColor = Color.WHITE;

        // Hard coded stuff of attributes of the .png file
        textLetterWidth = 45;
        textLetterHeight = 45;

        textBox = new Rectangle(x, y, ((textLetterWidth + textSpacing) * textScale) * text.length(), (textLetterHeight * textScale));

        TextureRegion image = Sandbox.images.getAtlas("assets").findRegion("fontsheet");

        testNumRows = image.getRegionHeight() / textLetterHeight;
        textNumCols = image.getRegionWidth() / textLetterWidth;

        textSheet = new Sprite[testNumRows][textNumCols];

        for(int rows = 0 ; rows < testNumRows; rows++) {
            for (int cols = 0 ; cols < textNumCols; cols++) {
                textSheet[rows][cols] = new Sprite(image, (textLetterWidth *cols), textLetterHeight *rows, textLetterWidth, textLetterHeight);
                textSheet[rows][cols].setColor(textColor);
                textSheet[rows][cols].setSize(textLetterWidth * textScale, textLetterHeight * textScale);
            }
        }
    }

    public void update(float dt){
        if(!textComplete){
            if(textLength < text.length()){
                if(textDelayCurrent > 0)
                    textDelayCurrent -= 100f * dt;
                else{
                    textLength++;
                    textDelayCurrent = textDelay;
                }
            }else{
                textComplete = true;
                textLength = text.length();
            }
        }
    }

    public void drawText(SpriteBatch sb){
        for(int i = 0; i < textLength; i ++){

            char c = text.charAt(i);
            int index;
            index = c - 32;

            int row = index / textSheet[0].length;
            int col = index % textSheet[0].length;

            textSheet[row][col].setPosition(textBox.getX() + (i * (textLetterWidth + textSpacing) * textScale), textBox.getY());
            textSheet[row][col].setColor(textColor);
            textSheet[row][col].draw(sb);
        }
    }

    public void drawTextBox(ShapeRenderer sr){
        sr.rect(textBox.getX(), textBox.getY(), textBox.getWidth(), textBox.getHeight());
    }

    public boolean isComplete(){
        return textComplete;
    }

    public void shiftHalfLeft() {
        textBox.setX(textBox.getX() - textBox.getWidth()/2);
    }

    // Does not update the box
    public void updateText(String text){
        this.text = text;
    }

    public void setTextPosition(float x, float y) {
        textBox.setPosition(x, y);
    }

    public void setTextScale(int textScale){
        this.textScale = textScale;
    }

    public float getTextHeight(){
        return textBox.getHeight();
    }

    public float getTextWidth(){
        return textBox.getWidth();
    }

    public float getTextX(){
        return textBox.getX();
    }

    public float getTextY(){
        return textBox.getY();
    }

    public void setTextColor(Color textColor){
        this.textColor = textColor;
    }

    public boolean contains(float x, float y){
        return textBox.contains(x,y);
    }

}
