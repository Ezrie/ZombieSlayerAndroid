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
    private Integer WORLD_TIMER = 300;
    private float TIMECOUNT = 0; //To be used later.
    private Integer SCORE = 0;
    private String NAME_LABEL = "Mario";
    private String TIME_LABEL = "Time";
    private String LEVEL_LABEL = "1-1";
    private String WORLD_LABEL = "WORLD";

    //HUD components formatted.
    private Label countdownLabel = new Label (String.format("%03d", WORLD_TIMER), textFont);
    private  Label scoreLabel = new Label(String.format("%06d", SCORE),textFont);
    private Label timeLabel = new Label(TIME_LABEL, textFont);
    private Label levelLabel = new Label(LEVEL_LABEL, textFont);
    private Label worldLabel = new Label(WORLD_LABEL, textFont);
    private Label nameLabel = new Label(NAME_LABEL, textFont);

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
        table.add(nameLabel).expandX().padTop(menuTopPad);
        table.add(worldLabel).expandX().padTop(menuTopPad);
        table.add(timeLabel).expandX().padTop(menuTopPad);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //Add the created table to the stage.
        stage.addActor(table);
    }
}