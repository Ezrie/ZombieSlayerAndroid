package coreGame.Events;
/**
 * This class is called when two fixtures in our box 2d world collide.
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/28/2019
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import coreGame.Model.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    /*
    This method identifies what fixtures are colliding when they do collide.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "survivor" || fixB.getUserData() == "survivor"){
            //If fixture A is survivor, then use fixA. Otherwise, use fixB.
            Fixture survivor = fixA.getUserData() == "survivor" ? fixA : fixB;
            //From previous statement, this finds out which fixture is the object(not survivor).
            Fixture object = survivor == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).objectHit();
            }
        }
    }
    /*
    This method simply returns a message when a collision has ended.
     */
    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("Collision ends.", "");
        Gdx.app.log("1", "What do?");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
