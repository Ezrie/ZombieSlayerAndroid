package coreGame.View.Scenes;
/**
 * This is the class which creates and displays the HUD for the main game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/29/2019
 */

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

public class HUD implements Disposable {

    //Creates a new stage and viewport where the HUD will be in.
    public Stage stage;
    private Viewport viewport;

    //Font and color of the HUD items.
    private static Label.LabelStyle textFont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    //HUD literals.
    private Integer worldTimer = 300;
    //This will be used later.
    private float timeCount = 0;
    private static Integer scoreCount = 0;
    private String nameLabel = "Survivor";
    private String timeLabel = "Time";
    private int healthLabel = 100;
    private static String scoreLabel = "SCORE";

    //HUD components formatted.
    private Label countdownHUD = new Label (String.format("%03d", worldTimer),textFont);
    private static Label scoreHUD = new Label(String.format("%06d", scoreCount),textFont);
    private Label timeHUD = new Label(timeLabel, textFont);
    private Label healthHUD = new Label(String.format("%03d", healthLabel), textFont);
    private Label worldHUD = new Label(scoreLabel, textFont);
    private Label nameHUD = new Label(nameLabel, textFont);
    //Size of the HUD's top margin.
    private int menuTopPad = 10;

    /**
     * Creates the HUD within the stage by using a table and labels.
     *
     * @param sb
     */
    public HUD(SpriteBatch sb) {

        //Create a new camera showing a stage that will hold the HUD. The viewport is the same size as the world.
        OrthographicCamera viewCamera = new OrthographicCamera();
        viewport = new FitViewport(GameConstants.V_WIDTH, GameConstants.V_HEIGHT, viewCamera);
        stage = new Stage(viewport, sb);

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

    public void update(float _dt){
        timeCount += _dt;
        if (timeCount >= 1){
            worldTimer--;
            countdownHUD.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        scoreCount += value;
        scoreHUD.setText(String.format("%06d", scoreCount));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}