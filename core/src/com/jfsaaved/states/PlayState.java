package com.jfsaaved.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.Sandbox;
import com.jfsaaved.movingobject.Player;

public class PlayState extends State{

    private Player player;

    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        player = new Player(Sandbox.images.getAtlas("assets").findRegion("player"),
                (int)Sandbox.WIDTH/2,
                (int) Sandbox.HEIGHT/2, 36, 54, 5);
    }

    @Override
    protected void update(float dt) {
        player.update(dt);
    }

    @Override
    protected void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();
        player.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    protected void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.end();
    }

}
