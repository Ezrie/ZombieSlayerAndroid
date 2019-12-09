package coreGame.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import coreGame.Util.GameConstants;
import coreGame.View.Scenes.HUD;
import coreGame.View.Screens.PlayScreen;

public class Zombie extends Enemy {
    private float stateTime;
    private TextureRegion zTexture;
    private Sprite sprite;
    private boolean setToDestroy;
    private boolean isDestroyed;
    public Vector2 velocity;

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
        velocity = new Vector2(.2f ,0);
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
        b2body.setActive(false);
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
            b2body.setLinearVelocity(velocity);
            sprite.setPosition(this.getPositionX(), this.getPositionY());
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

    //==================================== Getters ==================================
    public float getPositionX(){
        return b2body.getPosition().x - (sprite.getWidth() / 2f);
    }

    public float getPositionY(){
        return b2body.getPosition().y - (sprite.getWidth() / 2f);
    }
}
