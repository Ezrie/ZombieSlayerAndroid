package coreGame.Model;
/**
 * This class creates the coin model.
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

public class Coin extends InteractiveTileObject{
    public Coin(PlayScreen _screen, Rectangle _bounds){
        super(_screen, _bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(GameConstants.COIN_BIT);
    }

    @Override
    public void objectHit() {
        Gdx.app.log("Coin", "Collision");
    }

    @Override
    public void survivorCollision() {
        Gdx.app.log("Survivor-Coin", "Collision");
    }

    @Override
    public void zombieHit() {
        Gdx.app.log("Zombie-Coin", "Collision");
    }

    @Override
    public void bulletHit() {
        Gdx.app.log("Bullet-Coin", "Collision");
    }
}
