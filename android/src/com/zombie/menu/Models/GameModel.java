package com.zombie.menu.Models;

/**
 * Game manages all objects in the game and is responsible for updating all states and renders
 * all object to the screen
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zombie.menu.R;

public class GameModel extends SurfaceView implements SurfaceHolder.Callback {
    public Player player;
    public Joystick joystick;
    public GameLoopModel gameLoop;


    public GameModel(Context context) {
        super(context);

        //get SurfaceHolder and add Callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


        gameLoop = new GameLoopModel(this, surfaceHolder);

        // Initialize game objects
        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), 1000, 500, 30);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        // Handle touch event actions
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double) event.getX(), (double) event.getY())){
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return  true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;

        }


        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //drawUPS(canvas);
        //drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
    }


    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        //could possibly change to this?
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS:" + averageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS:" + averageFPS, 100, 200, paint);
    }

    public void update() {
        joystick.update();
        player.update(joystick);
    }

}
