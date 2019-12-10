package coreGame.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Bullet extends Projectile {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    boolean fireWeapon;
    boolean setToDestroy; //remove
    boolean isDestroyed;
    float stateTime;
    float x, y;
    private Sprite sprite;
    private Vector2 velocity;
    //===
    public static final int SPEED = 500;
    private static Texture texture;

    public Bullet(PlayScreen _screen, float _x, float _y, boolean _fireWeapon) {
        super(_screen, _x, _y, _fireWeapon);
        this.world = _screen.getWorld();
        this.screen = _screen;
        this.fireWeapon = _fireWeapon;
        this.defineProjectile( _x, _y);

        //if (texture == null)
        texture = new Texture("touchpad-knob.png");
        sprite = new Sprite(texture);
        sprite.setBounds(sprite.getX(), sprite.getY(), 6 / GameConstants.PPM, 6 / GameConstants.PPM);
        sprite.setPosition(_x,_y);

        setToDestroy = false;
        isDestroyed = false;
    }



    /**
     * This method creates the projectile's body and its fixtures.
     */
    @Override
    public void defineProjectile(float _x, float _y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(_x, _y);
        //The projectile is affected by the physics of the b2world.
        bdef.type = BodyDef.BodyType.DynamicBody;
        //This checks if we are in a middle of a time step (60 per second).
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / GameConstants.PPM);
        fdef.filter.categoryBits = GameConstants.PROJECTILE_BIT;
        fdef.filter.maskBits = GameConstants.DEFAULT_BIT |
                GameConstants.COIN_BIT |
                GameConstants.BRICK_BIT |
                GameConstants.ZOMBIE_BIT |
                GameConstants.GHOST_BIT |
                GameConstants.OBJECT_BIT;

        fdef.shape = shape;
        //fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        //b2body.setLinearVelocity(new Vector2(fireWeapon ? 2 : -2, 2.5f));

    }
    /**
     * This method updates the position of the bullet, but also destroys the body if it is
     * set to be destroyed--bullet colliding with an object.
     * @param _dt
     */
    public void update(float _dt){
        stateTime =+ _dt;
        velocity = new Vector2(2f ,0);
        //
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !isDestroyed) {
            world.destroyBody(b2body);
            isDestroyed = true;
        }
        else if (!isDestroyed){
            b2body.setLinearVelocity(velocity);
            sprite.setPosition(this.getPositionX(), this.getPositionY());
        }
        //This keeps the velocity in vertical direction capped at 2. Use this when the joystick is done.
        //if(b2body.getLinearVelocity().y > 2f)
        // b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        //if((fireWeapon && b2body.getLinearVelocity().x < 0) || (!fireWeapon && b2body.getLinearVelocity().x > 0))
        //  setToDestroy();



    }

    public void setToDestroy(){
        this.setToDestroy = true;
    }

    public boolean isDestroyed(){
        return this.isDestroyed;
    }

    @Override
    public void draw(Batch batch){
        if (!isDestroyed)
            sprite.draw(batch);
    }

    public void setVelocityX(float _x) {
        this.b2body.setLinearVelocity(_x, this.b2body.getLinearVelocity().y);
    }

    public void setVelocityY(float _y) {
        this.b2body.setLinearVelocity(this.b2body.getLinearVelocity().x, _y);
    }

    //==================================== Getters ==================================
    public float getVelocityX() {
        return this.b2body.getLinearVelocity().x;
    }

    public float getVelocityY() {
        return this.b2body.getLinearVelocity().y;
    }

    public float getPositionX(){
        return b2body.getPosition().x - (sprite.getWidth() / 2f);
    }

    public float getPositionY(){
        return b2body.getPosition().y - (sprite.getWidth() / 2f);
    }
}
