package com.badlogic.mariogame;
/**
 * This class loads the graphics and calls for the start of the game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/06/2019
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.mariogame.Screens.PlayScreen;

public class MarioGame extends Game {

	//Declaring the graphics of the game. Used by all other classes and is memory heavy.
	public SpriteBatch batch;

	/**
	 * Initializes the SpriteBatch and creates a new instance of PlayScreen, where the game will launch.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	/**
	 * Renders what you see on the screen.
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Manually dispose of game's assets after game exits. Reduces memory usage.
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
