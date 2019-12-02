package coreGame.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import coreGame.Util.GameConstants;

public class Joystick {
    private Body bodyObj;
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private float actuatorX;
    private float actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius){

        // Outer and inner circle of the joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        // Radii of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // Paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //Box2d body for the joystick
        BodyDef bdef = new BodyDef();
        //Outer circle will not move, so use it as the body's position.
        bdef.position.set(outerCircleCenterPositionX, outerCircleCenterPositionY);
        //Makes the joystick body static; it is not affected by the physics in the box 2d world.
        bdef.type = BodyDef.BodyType.StaticBody;

        //Box2d fixture for outer circle
        FixtureDef fdefOut = new FixtureDef();
        CircleShape shapeOut = new CircleShape();
        shapeOut.setRadius(outerCircleRadius);
        fdefOut.shape = shapeOut;


        //Box2d fixture for inner moving circle
        FixtureDef fdefIn = new FixtureDef();
        CircleShape shapeIn = new CircleShape();
        shapeIn.setRadius(innerCircleRadius);

    }

    public void defineJoystick() {

    }

    public void draw() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );

        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < outerCircleRadius) {
            actuatorX = (float) (deltaX / outerCircleRadius);
            actuatorY = (float) (deltaY / outerCircleRadius);
        } else{
            actuatorX = (float) (deltaX/deltaDistance);
            actuatorY = (float) (deltaY/deltaDistance);
        }

    }

    public void resetActuator() {
        actuatorX = 0f;
        actuatorY = 0f;
    }

    public float getActuatorX() {
        return actuatorX;
    }

    public float getActuatorY() {
        return actuatorY;
    }
}
