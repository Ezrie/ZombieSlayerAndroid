package coreGame.Model;
/**
 * This class updates and renders the game's world/map and the camera.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/14/2019
 */

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen _screen, Rectangle _bounds){
        this.world = _screen.getWorld();
        this.map = _screen.getMap();
        this.bounds = _bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //This sets the body-type of the body to static. A static body is not affected by physics in the 2d world.
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((_bounds.getX() + _bounds.getWidth() / 2) / GameConstants.PPM, (_bounds.getY() + _bounds.getHeight() / 2) / GameConstants.PPM);

        //This creates the body in the box 2d world.
        body = world.createBody(bdef);
        shape.setAsBox((_bounds.getWidth() / 2) / GameConstants.PPM, (_bounds.getHeight() / 2) / GameConstants.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void objectHit();
    public abstract void survivorCollision();
    public abstract void zombieHit();
    public abstract void bulletHit();

    // ======================================== SETTERS ==================================================

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    //This retrieves the specific cell from the tile map.
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * GameConstants.PPM / 16), (int) (body.getPosition().y * GameConstants.PPM / 16));
    }
}
