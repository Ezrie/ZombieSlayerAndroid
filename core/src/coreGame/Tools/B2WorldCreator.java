package coreGame.Tools;
/**
 * This class creates objects' layers with the loaded tile map in the corresponding world.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/21/2019
 */
import coreGame.Util.GameConstants;
import coreGame.Model.Brick;
import coreGame.Model.Coin;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map){
        //Defines a new type of object which will surround the tiles that can be interacted with.
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Ground, the second layer in the map is the ground object. For each tile, create an object.
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            //Gets the rectangle object from the tile map.
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //Defines the type of the body object as static (not effected by the physics mechanic).
            bdef.type = BodyDef.BodyType.StaticBody;
            //Positions the start of the body object at the start of the tile.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);

            body = world.createBody(bdef);

            //Defines the height and width of the body object to be the same as the tile map's object.
            shape.setAsBox((rect.getWidth() / 2) / GameConstants.PPM, (rect.getHeight() / 2) / GameConstants.PPM);
            fdef.shape = shape;
            //Creates a new fixture out of the body object.
            body.createFixture(fdef);
        }

        //Pipe, the third layer in the map is the pipe object.
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / GameConstants.PPM, (rect.getHeight() / 2) / GameConstants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Brick, the fifth layer in the map is the brick object.
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(world, map, rect);
        }

        //Coin, the fourth layer in the map is the coin object.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(world, map, rect);
        }
    }
}
