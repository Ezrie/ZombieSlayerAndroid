package coreGame.Model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public class Zombie extends Enemy {
    private float stateTime;

    public Zombie(PlayScreen _screen, float _x, float _y) {
        super(_screen, _x, _y);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GameConstants.PPM,32 / GameConstants.PPM);
        //Makes the zombie body dynamic; the survivor is affected by the physics in the box 2d world.
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

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
        b2body.createFixture(fdef).setUserData("zombie");

    }

    @Override
    public void update(float _dt) {
        stateTime += _dt;
    }

    @Override
    public void enemyHit() {
        Gdx.app.log("Enemy", "Collision");
    }

}
