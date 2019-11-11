package coreGame.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;

public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameConstants.BRICK_BIT);
    }

    @Override
    public void objectHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(GameConstants.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
