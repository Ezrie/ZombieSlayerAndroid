package coreGame.Model;
/**
 * This class creates the Survivor object.
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/21/2019
 */
import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Survivor extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion survivorStand;

    public Survivor(PlayScreen _screen){
        super(_screen.getAtlas().findRegion("Attack (1)"));

        this.world = _screen.getWorld();
        defineSurvivor();
        survivorStand = new TextureRegion(getTexture(), 1, 1, 430, 519);
        setBounds(0, 0, 16 / GameConstants.PPM, 16 / GameConstants.PPM);
        setRegion(survivorStand);

    }

    /**
     * Defines Survivor with shape and starting position.
     */
    public void defineSurvivor(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GameConstants.PPM,32 / GameConstants.PPM);
        //Makes the survivor body dynamic; the survivor is affected by the physics in the box 2d world.
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //This creates the polygon shape/fixture of the survivor that will collide with objects.
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ GameConstants.PPM);

        //This defines the filter for Survivor.
        fdef.filter.categoryBits = GameConstants.SURVIVOR_BIT;
        //The mask filter defines what the survivor can collide with.
        fdef.filter.maskBits = GameConstants.DEFAULT_BIT |
                GameConstants.COIN_BIT |
                GameConstants.BRICK_BIT |
                GameConstants.OBJECT_BIT |
                GameConstants.ZOMBIE_BIT;

        fdef.shape = shape;
        //The sensor makes the fixture to longer collide with anything in the box 2d world if set to true.
        fdef.isSensor = false;
        b2body.createFixture(fdef).setUserData("survivor");


    }

    /**
     * This will update the position of the Survivor sprite with its polygon.
     * @param _dt is delta time.
     */
    public void update(float _dt){
        setPosition(getPositionX(), getPositionY());
    }

    //==================================== Getters ==================================
    public float getPositionX(){
        return b2body.getPosition().x - getWidth() / 2;
    }

    public float getPositionY(){
        return b2body.getPosition().y - getHeight() / 2;
    }
}
