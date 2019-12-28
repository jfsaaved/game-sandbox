package com.jfsaaved.movingobject;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MovementHandler {

    private MovingObject movingObject;

    private Vector2 velocityVec;
    private float speed;
    private float angle;

    private Vector2 accelerationVec;
    private float acceleration;

    private float maxSpeed;
    private float decelerate;

    public MovementHandler(MovingObject movingObject){
        this.movingObject = movingObject;

        this.velocityVec = new Vector2(0,0);
        this.angle = 0;
        this.speed = 0;

        this.accelerationVec = new Vector2(0,0);
        this.acceleration = 400;

        maxSpeed = 500;
        decelerate = 400;
    }

    public float getSpeed(){
        return this.speed;
    }

    public void setSpeed(float speed){
        if(velocityVec.len() == 0)
            velocityVec.set(speed,0);
        else
            velocityVec.setLength(speed);
    }

    public void setAngle(float angle){
        velocityVec.setAngle(angle);
    }

    public boolean isMoving(){
        return velocityVec.len() > 0;
    }

    public void accelerateAtAngle(float angle){
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public void update(float dt){
        velocityVec.add( accelerationVec.x * dt, accelerationVec.y * dt );

        // decrease speed (decelerate) when not accelerating
        if (accelerationVec.len() == 0)
            speed -= decelerate * dt;

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // update velocity
        setSpeed(speed);

        // apply velocity
        moveBy( velocityVec.x * dt, velocityVec.y * dt );

        // reset acceleration
        accelerationVec.set(0,0);
    }

    protected void moveBy(float x, float y){
        movingObject.x = movingObject.x + x;
        movingObject.y = movingObject.y + y;
    }

}
