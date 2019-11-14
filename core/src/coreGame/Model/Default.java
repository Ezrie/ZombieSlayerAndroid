package coreGame.Model;

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
}
