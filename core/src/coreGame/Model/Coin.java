package coreGame.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Coin extends InteractiveTileObject{
    public Coin(PlayScreen _screen, Rectangle _bounds){
        super(_screen, _bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameConstants.COIN_BIT);
    }

    @Override
    public void objectHit() {
        Gdx.app.log("Coin", "Collision");
    }
}
