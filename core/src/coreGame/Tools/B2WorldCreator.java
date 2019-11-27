package coreGame.Tools;
/**
 * This class creates objects' layers with the loaded tile map in the corresponding world.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/21/2019
 */
import coreGame.Model.Default;
import coreGame.Model.Brick;
import coreGame.Model.Coin;
import coreGame.Model.Zombie;
import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class B2WorldCreator {
    private Array<Zombie> zombies;
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //Defines a new type of object which will surround the tiles that can be interacted with.
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Default, the second layer in the map is the ground object. For each tile, create an object.
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Default(screen, rect);
        }

        //Pipe, the third layer in the map is the pipe object.
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Default(screen, rect);
        }

        //Brick, the fifth layer in the map is the brick object.
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(screen, rect);
        }

        //Coin, the fourth layer in the map is the coin object.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);
        }

        //This creates all zombies from the tile map for this level.
        zombies = new Array<Zombie>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            zombies.add(new Zombie(screen, rect.getX() / GameConstants.PPM, rect.getY() / GameConstants.PPM));
        }
    }
    // ========================================= Getters =================================
    public Array<Zombie> getZombies() {
        return zombies;
    }
}
