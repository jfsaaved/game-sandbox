package com.jfsaaved.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.jfsaaved.Sandbox;
import com.jfsaaved.movingobject.Player;
import com.jfsaaved.object.Object;

import java.util.List;

public abstract class State {

    protected GameStateManager gameStateManager;
    protected OrthographicCamera orthographicCamera;
    protected Vector3 mouse;

    protected State (GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.orthographicCamera = new OrthographicCamera();
        this.mouse = new Vector3();
        this.updateCam((int) 640, (int) 384, Sandbox.WIDTH/2 + 160, Sandbox.HEIGHT/2 + 96);
    }

    protected void updateCam(int width, int height, float x, float y){
        orthographicCamera.setToOrtho(false, width, height);
        orthographicCamera.position.set(x, y, 0);
        orthographicCamera.update();
    }

    protected void renderGrid(ShapeRenderer shapeRenderer){
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(int i = 0; i < 4; i ++) {
            for(int j = 0; j < 4; j++) {
                shapeRenderer.rect(i * 320, j * 192, 320, 192);
            }
        }
        shapeRenderer.end();
    }

    protected abstract boolean detectCollision(float x, float y, List<Object> objects);
    protected abstract void handleInput(float dt);
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch spriteBatch);
    protected abstract void shapeRender(ShapeRenderer shapeRenderer);
    public OrthographicCamera getOrthographicCamera(){ return this.orthographicCamera; }

}
