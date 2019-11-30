package com.badlogic.mariogame.Scenes;
/**
 * This is the class which creates and displays the HUD for the main game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/06/2019
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.mariogame.GameConstants;

public class HUD {

    //Creates a new stage and viewport where the HUD will be in.
    public Stage stage;
    private Viewport viewport;

    //Font and color of the HUD items.
    private Label.LabelStyle textFont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    //HUD literals.
    private Integer worldTimer = 300;
    private float timeCount = 0; //To be used later.
    private Integer scoreLabel = 0;
    private String nameLabel = "Mario";
    private String timeLabel = "Time";
    private String levelLabel = "1-1";
    private String worldLabel = "WORLD";

    //HUD components formatted.
    private Label countdownHUD = new Label (String.format("%03d", worldTimer), textFont);
    private  Label scoreHUD = new Label(String.format("%06d", scoreLabel),textFont);
    private Label timeHUD = new Label(timeLabel, textFont);
    private Label levelHUD = new Label(levelLabel, textFont);
    private Label worldHUD = new Label(worldLabel, textFont);
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
        table.add(scoreHUD).expandX();
        table.add(levelHUD).expandX();
        table.add(countdownHUD).expandX();

        //Add the created table to the stage.
        stage.addActor(table);
    }
}