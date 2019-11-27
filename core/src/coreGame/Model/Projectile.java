package coreGame.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Projectile extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    boolean fireWeapon;
    boolean setToDestroy;
    boolean isDestroyed;
    float stateTime;

    public Projectile(PlayScreen _screen, float _x, float _y, boolean fireWeapon){
        this.world = _screen.getWorld();
        this.screen = _screen;
        this.fireWeapon = fireWeapon;
        setPosition(_x,_y);
        defineProjectile();
        setBounds(_x, _y, 6 / GameConstants.PPM, 6 / GameConstants.PPM);
        defineProjectile();
    }

    public void defineProjectile(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireWeapon ? getX() + 12 /GameConstants.PPM : getX() - 12 /GameConstants.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
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
                GameConstants.OBJECT_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireWeapon ? 2 : -2, 2.5f));

    }

    public void update(float _dt){
        stateTime =+ _dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !isDestroyed) {
            world.destroyBody(b2body);
            isDestroyed = true;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        if((fireWeapon && b2body.getLinearVelocity().x < 0) || (!fireWeapon && b2body.getLinearVelocity().x > 0))
            setToDestroy();
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }
}
