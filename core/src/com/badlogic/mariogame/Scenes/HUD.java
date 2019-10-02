package com.badlogic.mariogame.Scenes;
/**
 * This is the class which creates and displays the HUD for the main game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
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
import com.badlogic.mariogame.MarioGame;

public class HUD implements GameConstants {

    //HUD components
    public Stage stage;
    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label nameLabel;
    private Viewport viewport;

    //Creates text in the game where information is displayed
    public HUD(SpriteBatch sb) {

        OrthographicCamera viewCamera = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, viewCamera);
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label (String.format("%03d", WORLD_TIMER), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", SCORE), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(TIME_LABEL, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(LEVEL_LABEL, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label(WORLD_LABEL, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        nameLabel = new Label(NAME_LABEL, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(nameLabel).expandX().padTop(TEN);
        table.add(worldLabel).expandX().padTop(TEN);
        table.add(timeLabel).expandX().padTop(TEN);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
}