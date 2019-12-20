package com.jfsaaved;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.handlers.Images;
import com.jfsaaved.states.GameStateManager;
import com.jfsaaved.states.PlayState;

public class Sandbox extends ApplicationAdapter {

	public static float WIDTH = 1280;
	public static float HEIGHT = 768;

	public static GameStateManager gameStateManager;
	public static SpriteBatch spriteBatch;
	public static ShapeRenderer shapeRenderer;
	public static Images images;
	
	@Override
	public void create () {
		gameStateManager = new GameStateManager();
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		images = new Images("pack1.pack","assets");
		gameStateManager.push(new PlayState(gameStateManager));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
		gameStateManager.shapeRender(shapeRenderer);
		//gameStateManager.renderGrid(shapeRenderer);
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
