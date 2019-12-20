package com.jfsaaved.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.jfsaaved.Sandbox;
import com.jfsaaved.movingobject.Player;
import com.jfsaaved.object.InvisibleWall;
import com.jfsaaved.ui.DialogueImages;
import jdk.jfr.consumer.RecordedClass;

public class PlayState extends State{

    private Player player;
    private InvisibleWall wall;
    private DialogueImages diag;

    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        String[] text1 = {"Hello world!"};
        diag = new DialogueImages(orthographicCamera, text1);

        wall = new InvisibleWall(Sandbox.WIDTH/2, Sandbox.HEIGHT/2 - 192, 320, 192);
        this.player = new Player(Sandbox.WIDTH/2, Sandbox.HEIGHT/2, 36, 54, Sandbox.images.getAtlas("assets").findRegion("player"));
    }

    public void wallDetection(){
        player.setCollidingDown(false);
        Rectangle outerBox = new Rectangle(player.getX() - 3, player.getY() - 3, 39, 57);
        if((wall.getHurtBox().overlaps(outerBox))){
            player.setCollidingDown(true);
        }
    }

    @Override
    protected void update(float dt) {
        wallDetection();
        player.update(dt);
        player.handleInput(dt);
        diag.update(dt);
    }

    @Override
    protected void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();
        player.draw(spriteBatch);
        diag.drawDialogue(spriteBatch);
        spriteBatch.end();
    }

    @Override
    protected void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        player.drawBox(shapeRenderer);
        wall.drawBox(shapeRenderer);
        shapeRenderer.end();
    }
}
