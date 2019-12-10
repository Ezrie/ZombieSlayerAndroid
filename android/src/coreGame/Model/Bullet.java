package coreGame.Model;

import com.badlogic.gdx.Gdx;
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
import coreGame.View.Scenes.HUD;
import coreGame.View.Screens.PlayScreen;

public class Bullet extends Projectile {

    public Body b2body;
    float stateTime;
    private Sprite sprite;
    private Vector2 direction;
    public static final int SPEED = 1;
    private static Texture texture;

    public Bullet(PlayScreen _screen, float _x, float _y, float _dirX, float _dirY) {
        super(_screen, _x, _y, _dirX, _dirY);
        this.defineProjectile( _x, _y);
        direction = _screen.player.getDirection(_screen.hud);
        direction.x = direction.x * SPEED;
        direction.y = direction.y * SPEED;
        b2body.setLinearVelocity(direction);

        //if (texture == null)
        texture = new Texture("touchpad-knob.png");
        sprite = new Sprite(texture);
        sprite.setBounds(sprite.getX(), sprite.getY(), 4 / GameConstants.PPM, 4 / GameConstants.PPM);
        sprite.setPosition(_x,_y);
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
        shape.setRadius(4 / GameConstants.PPM);
        fdef.filter.categoryBits = GameConstants.PROJECTILE_BIT;
        fdef.filter.maskBits = GameConstants.DEFAULT_BIT |
                GameConstants.COIN_BIT |
                GameConstants.BRICK_BIT |
                GameConstants.ZOMBIE_BIT |
                GameConstants.GHOST_BIT |
                GameConstants.OBJECT_BIT;

        fdef.shape = shape;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);

    }
    /**
     * This method updates the position of the bullet, but also destroys the body if it is
     * set to be destroyed--bullet colliding with an object.
     * @param _dt
     */
    public void update(float _dt){
        stateTime =+ _dt;

        if(stateTime > 0.1) {
            this.b2body.setLinearVelocity(0, 0);
            this.dispose();
            stateTime = 0;
        }
        else {
            sprite.setPosition(this.getPositionX(), this.getPositionY());
        }
    }
    //If these are called, they've collided with an object, so remove them after applying damage.
    public void damageSurvivor() {
        Gdx.app.log("Survivor-Projectile", "Collision");
        HUD.minusHealth(10);
        this.dispose();
    }

    public void damageEnemy(Enemy enemy) {
        Gdx.app.log("Enemy-Projectile", "Collision");
        enemy.minusHealth(10);
        this.dispose();
    }

    @Override
    public void draw(Batch batch){
        sprite.draw(batch);
    }

    public void dispose(){
        this.b2body.destroyFixture(b2body.getFixtureList().first());
        world.destroyBody(this.b2body);
        sprite.getTexture().dispose();
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
