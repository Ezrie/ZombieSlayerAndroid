package com.badlogic.mariogame;
/**
 * This class loads the graphics and creates the PlayScreen.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.mariogame.Screens.PlayScreen;

public class MarioGame extends Game {

	//Declaring the graphics of the game by creating an instance of SpriteBatch (used by all other classes, memory heavy)
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
