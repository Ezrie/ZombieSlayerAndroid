package coreGame.View.Scenes;
/**
 * This is the class which creates and displays the HUD for the main game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/29/2019
 */

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

import coreGame.Model.Joystick;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Locale;

/*

Wireframe
System Architecture
Joystick

*/

public class HUD extends SurfaceView implements Disposable {

    //Creates a new stage and viewport where the HUD will be in.
    public Stage stage;
    private Viewport viewport;

    //Joystick that will be used for movement.
    private Joystick joystick;

    //Font and color of the HUD items.
    private static Label.LabelStyle textFont;

    //HUD literals.
    private Integer worldTimer = 300;
    //This will be used later.
    private float timeCount = 0;
    private static Integer scoreCount = 0;
    private String nameLabel = "Survivor";
    private String timeLabel = "Time";
    private static Integer healthLabel = 100;
    private static String scoreLabel = "SCORE";

    //HUD components formatted.
    private Label countdownHUD = new Label (String.format("%03d", worldTimer),textFont);
    private static Label scoreHUD = new Label(String.format("%06d", scoreCount),textFont);
    private Label timeHUD = new Label(timeLabel, textFont);
    private static Label healthHUD = new Label(String.format("%03d", healthLabel), textFont);
    private Label worldHUD = new Label(scoreLabel, textFont);
    private Label nameHUD = new Label(nameLabel, textFont);
    //Size of the HUD's top margin.
    private int menuTopPad = 10;

    /**
     * Creates the HUD within the stage by using a table and labels.
     *
     * @param sb
     */
    public HUD(SpriteBatch sb, Context ctx) {
        super(ctx);
        //Create a new camera showing a stage that will hold the HUD. The viewport is the same size as the world.
        OrthographicCamera viewCamera = new OrthographicCamera();
        viewport = new FitViewport(GameConstants.V_WIDTH, GameConstants.V_HEIGHT, viewCamera);
        stage = new Stage(viewport, sb);

        textFont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


        //Joystick movement controls.
        joystick = new Joystick(275, 700, 70, 40);

        //Creates a table at the top of the game's window.
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //Add the labels to the table and give the first row a top margin.
        //.expandX() Fills the table across the viewport's x-axis.
        table.add(nameHUD).expandX().padTop(menuTopPad);
        table.add(worldHUD).expandX().padTop(menuTopPad);
        table.add(timeHUD).expandX().padTop(menuTopPad);
        table.row();
        table.add(healthHUD).expandX();
        table.add(scoreHUD).expandX();
        table.add(countdownHUD).expandX();

        //Add the created table to the stage.
        stage.addActor(table);
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

    public void update(float _dt){
        timeCount += _dt;
        if (timeCount >= 1){
            worldTimer--;
            countdownHUD.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

    }

    public static void changeHealth(int _value){
        healthLabel -= _value;
        healthHUD.setText(String.format(Locale.getDefault(),"%03d", healthLabel));
    }

    public static void addScore(int _value){
        scoreCount += _value;
        scoreHUD.setText(String.format(Locale.getDefault(),"%06d", scoreCount));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}