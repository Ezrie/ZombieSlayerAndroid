package coreGame.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import coreGame.Util.GameConstants;
import coreGame.View.Scenes.HUD;
import coreGame.View.Screens.PlayScreen;

public class Zombie extends Enemy {

    private final float ZOMBIE_VELOCITY = 0.2f;
    private final float ZOMBIE_MAX_SPEED = 0.7f;
    private float stateTime;
    private TextureRegion zTexture;
    private Sprite sprite;
    private boolean setToDestroy;
    private boolean isDestroyed;
    private Survivor target;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 direction;
    private double hypotenuse;

    public Zombie(PlayScreen _screen, float _x, float _y) {
        super(_screen, _x, _y);
        this.defineEnemy(_x, _y);

        TextureRegion zombieTexture = _screen.getTextures()[2][7];
        zTexture = new TextureRegion(zombieTexture);
        this.setRegion(zTexture);

        sprite = new Sprite(zTexture);
        sprite.setBounds(sprite.getX(), sprite.getY(), 16 / GameConstants.PPM, 16 / GameConstants.PPM );
        //sprite.setPosition(getPositionX(), getPositionY());

        setToDestroy = false;
        isDestroyed = false;
        velocity = new Vector2(0,0);
        direction = new Vector2(0, 0);
        target = _screen.player;
    }

    public void changeVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }


    @Override
    protected void defineEnemy(float _x, float _y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(_x, _y);
        //Makes the zombie body dynamic; the survivor is affected by the physics in the box 2d world.
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        // Continuous damage
        //b2body.setBullet(true);

        //This creates the polygon shape/fixture of the survivor that will collide with objects.
        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.5f;
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ GameConstants.PPM);

        //This defines the filter for Survivor.
        fdef.filter.categoryBits = GameConstants.ZOMBIE_BIT;
        //The mask filter defines what the zombie can collide with.
        fdef.filter.maskBits = GameConstants.DEFAULT_BIT |
                GameConstants.COIN_BIT |
                GameConstants.BRICK_BIT |
                GameConstants.ZOMBIE_BIT |
                GameConstants.OBJECT_BIT |
                GameConstants.SURVIVOR_BIT;

        fdef.shape = shape;
        //The sensor makes the fixture to longer collide with anything in the box 2d world if set to true.
        fdef.isSensor = false;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float _dt){
        stateTime += _dt;
        if (setToDestroy && !isDestroyed){
            world.destroyBody(b2body);
            isDestroyed = true;
            stateTime = 0;
        }
        else if (!isDestroyed) {
            updateZombiePosition();
            sprite.setPosition(this.getPositionX(), this.getPositionY());
        }
    }

    private void updateZombiePosition() {
        direction.x = target.getPositionX() - this.getPositionX();
        direction.y = target.getPositionY() - this.getPositionY();
        hypotenuse = Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));
        direction.x = direction.x / (float) hypotenuse;
        direction.y = direction.y / (float) hypotenuse;

        if (this.getVelocityX() <= ZOMBIE_MAX_SPEED) {
            this.setVelocityX(direction.x * ZOMBIE_VELOCITY);
        }
        if (this.getVelocityY() <= ZOMBIE_MAX_SPEED) {
            this.setVelocityY(direction.y * ZOMBIE_VELOCITY);
        }
    }

    public void draw(Batch batch){
        if (!isDestroyed || stateTime < 1) {
            super.draw(batch);
            sprite.draw(batch);
        }
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    @Override
    public void damageSurvivor() {
        Gdx.app.log("Survivor-Zombie", "Collision");
        HUD.changeHealth(5);
        setToDestroy = false;
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
        return this.b2body.getLinearVelocity().x;
    }

    public float getPositionX(){
        return b2body.getPosition().x - (sprite.getWidth() / 2f);
    }

    public float getPositionY(){
        return b2body.getPosition().y - (sprite.getWidth() / 2f);
    }
}
