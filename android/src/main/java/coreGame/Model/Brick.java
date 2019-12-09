package coreGame.Model;
/**
 * This class creates the brick model, and it also destroys the brick
 * if a specific fixture collides with it.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/14/2019
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen _screen, Rectangle _bounds){
        super(_screen, _bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameConstants.BRICK_BIT);
    }

    @Override
    public void objectHit() {
        Gdx.app.log("Brick", "Collision");
        //setCategoryFilter(GameConstants.DESTROYED_BIT);
        //getCell().setTile(null);

    }

    @Override
    public void survivorCollision() {
        Gdx.app.log("Survivor-Brick", "Collision");
        //setCategoryFilter(GameConstants.DESTROYED_BIT);
        //getCell().setTile(null);
    }

    @Override
    public void zombieHit() {
        Gdx.app.log("Zombie-Brick", "Collision");
        //setCategoryFilter(GameConstants.DESTROYED_BIT);
        //getCell().setTile(null);
    }

    @Override
    public void bulletHit() {
        Gdx.app.log("Brick", "Collision");
        //setCategoryFilter(GameConstants.DESTROYED_BIT);
        //getCell().setTile(null);
    }
}
