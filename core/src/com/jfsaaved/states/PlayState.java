package com.jfsaaved.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.Sandbox;
import com.jfsaaved.movingobject.Player;
import com.jfsaaved.object.InvisibleWall;
import com.jfsaaved.object.Object;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State{

    private Player player;
    private List<Object> objects;

    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        this.objects = new ArrayList<>();
        InvisibleWall invisibleWall = new InvisibleWall((Sandbox.WIDTH/2) - 320 , Sandbox.HEIGHT/2, 320, 192 );
        InvisibleWall invisibleWall2 = new InvisibleWall((Sandbox.WIDTH/2) + 320 , Sandbox.HEIGHT/2, 320, 192 );
        objects.add(invisibleWall);
        objects.add(invisibleWall2);
        this.player = new Player(Sandbox.WIDTH/2, Sandbox.HEIGHT/2, 36, 54, Sandbox.images.getAtlas("assets").findRegion("player"));
    }

    @Override
    protected boolean detectCollision(float x, float y, List<Object> objects) {
        for(Object object : objects) if(object.contains(x, y)) return true;
        return false;
    }

    @Override
    protected void handleInput(float dt) {
        player.handleStanding();
        if(!detectCollision(player.getX() + 37, player.getY(), objects))
            player.handleMoveRight();
        if(!detectCollision(player.getX() - 1, player.getY(), objects))
            player.handleMoveLeft();
    }

    @Override
    protected void update(float dt) {
        handleInput(dt);
        player.update(dt);
    }

    @Override
    protected void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();
        player.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    protected void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        player.drawBox(shapeRenderer);
        shapeRenderer.end();
    }
}
