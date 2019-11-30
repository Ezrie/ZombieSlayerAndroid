package com.badlogic.mariogame.Sprites;
/**
 * This class creates the player object.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/17/2019
 */

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.mariogame.GameConstants;

import java.awt.Rectangle;

public class Mario extends Sprite {
    public World world;
    public Body b2body;

    public Mario(World _world){
        this.world = _world;
        defineMario();
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GameConstants.PPM, 32 / GameConstants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/ GameConstants.PPM, 5 / GameConstants.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
