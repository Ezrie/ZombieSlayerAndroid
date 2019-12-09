package coreGame.Game;
/**
 * This class loads the graphics and calls for the start of the game.
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/06/2019
 */

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zombie.menu.Controller.GameLauncher;
import com.zombie.menu.Views.MainMenu;

import coreGame.View.Screens.PlayScreen;

public class ZombieGame extends Game {

    private Context ctx;

    //Declaring the graphics of the game. Used by all other classes and is memory heavy.
    public SpriteBatch batch;


    public ZombieGame(Context _ctx) {
        this.ctx = _ctx;
    }

    /**
     * Initializes the SpriteBatch and creates a new instance of PlayScreen, where the game will launch.
     */
    public void create () {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this, ctx));
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
