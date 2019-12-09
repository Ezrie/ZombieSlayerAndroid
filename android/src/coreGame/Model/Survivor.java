package coreGame.Model;
/**
 * This class creates the Survivor object.
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/14/2019
 */
import coreGame.Util.GameConstants;
import coreGame.View.Scenes.HUD;
import coreGame.View.Screens.PlayScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Survivor extends Sprite {
    public World world;
    public Body b2body;
    public Vector2 startPos = new Vector2(800 / GameConstants.PPM, 700 / GameConstants.PPM);
    private final float MAX_SPEED = 1f;
    float DAMPING = 10f;
    public int healthPoints;
    private TextureRegion sTexture;
    private Sprite sprite;
    public final Vector2 ZERO_VECTOR = new Vector2(0, 0);

    public Survivor(PlayScreen _screen){
        this.world = _screen.getWorld();

        defineSurvivor();
        setHealthPoints(100);

        TextureRegion tex = _screen.getTextures()[4][5];
        sTexture = new TextureRegion(tex);
        //Sets textures to the coordinates defined by the sprite batch (from getTextures[coords]).
        this.setRegion(sTexture);

        sprite = new Sprite(sTexture);
        sprite.setBounds(sprite.getX(), sprite.getY(), 16 / GameConstants.PPM, 16 / GameConstants.PPM);
    }

    /**
     * Defines Survivor with shape and starting position.
     */
    public void defineSurvivor(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(startPos);
        //Makes the survivor body dynamic; the survivor is affected by the physics in the box 2d world.
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        //b2body.setBullet(true);
        //Linear damping slows down the player movement if no keys are being pressed.
        b2body.setLinearDamping(DAMPING);

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

    public void update(float _dt, HUD hud) {
        if (hud.handleJoystickInput().equals(ZERO_VECTOR)) {
            setVelocity(ZERO_VECTOR);
        } else {
            if (getVelocityX() <= MAX_SPEED) {
                setVelocity(new Vector2(getVelocityX() + (hud.handleJoystickInput().x / 5), getVelocityY() ));
            }
            if (getVelocityY() <= MAX_SPEED) {
                setVelocity(new Vector2(getVelocityX(), getVelocityY() + (hud.handleJoystickInput().y / 5) ));
            }
        }
        this.sprite.setPosition(this.getPositionX(), this.getPositionY());
    }

    public void draw(Batch batch) {
        super.draw(batch);
        this.sprite.draw(batch);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    //==================================== Getters ==================================
    public float getPositionX(){
        return b2body.getPosition().x - (sprite.getWidth() / 2f);
    }

    public float getPositionY(){
        return b2body.getPosition().y - (sprite.getWidth() / 2f);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public float getVelocityX() {
        return b2body.getLinearVelocity().x;
    }

    public float getVelocityY() {
        return b2body.getLinearVelocity().y;
    }

    //=================================== Setters =====================================
    public void setHealthPoints(int _healthPoints) {
        this.healthPoints = _healthPoints;
    }

    public void setVelocity(Vector2 _velocity) {
        this.b2body.setLinearVelocity(_velocity);
    }


}
