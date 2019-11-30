package coreGame.Model;
/**
 * This class creates the default model -- everything else that is not a
 * brick or coin tile on the map. The default model serves as a tile that
 * is not interactive with any fixtures besides simply colliding -- no other
 * event is triggered.
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

public class Default extends InteractiveTileObject{
    public Default(PlayScreen _screen, Rectangle _bounds){
        super(_screen, _bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameConstants.DEFAULT_BIT);
    }

    @Override
    public void objectHit() {
        Gdx.app.log("Default", "Collision");
    }
    @Override
    public void survivorCollision() {
        Gdx.app.log("Survivor-Default", "Collision");
    }

    @Override
    public void zombieHit() {
        Gdx.app.log("Zombie-Default", "Collision");
    }

    @Override
    public void bulletHit() {
        Gdx.app.log("Bullet-Default", "Collision");
    }
}
