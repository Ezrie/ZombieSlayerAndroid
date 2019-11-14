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

import coreGame.Model.Enemy;
import coreGame.Model.InteractiveTileObject;
import coreGame.Tools.B2WorldCreator;

public class WorldContactListener implements ContactListener {
    /*
    This method identifies what fixtures are colliding when they do collide.
     */
    @Override
    public void beginContact(Contact _contact) {
        Fixture fixA = _contact.getFixtureA();
        Fixture fixB = _contact.getFixtureB();
        Fixture object;

        switch (fixA.getUserData().toString()) {
            case "survivor":
                objectIdentifier(fixB);
            case "zombie":
                objectIdentifier(fixB);
            default:
                objectIdentifier(fixA);
        }
    }

        /*if(fixA.getUserData() == "survivor" || fixB.getUserData() == "survivor"){
            //If fixture A is survivor, then use fixA. Otherwise, use fixB.
            Fixture survivor = fixA.getUserData() == "survivor" ? fixA : fixB;
            //From previous statement, this finds out which fixture is the object(not survivor).
            Fixture object = survivor == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).objectHit();
            }*/

    //This is a helper method for beginContact.
    public void objectIdentifier(Fixture _fixture){
        Fixture object = _fixture;
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).objectHit();
        }
        else if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Enemy) object.getUserData()).enemyHit();
        }
        else if (object.getUserData() != null && B2WorldCreator.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Enemy) object.getUserData()).enemyHit();
        }
    }
    /*
    This method simply returns a message when a collision has ended.
     */
    @Override
    public void endContact(Contact _contact) {
        Gdx.app.log("Collision ends.", "");
    }

    @Override
    public void preSolve(Contact _contact, Manifold _oldManifold) {

    }

    @Override
    public void postSolve(Contact _contact, ContactImpulse _impulse) {

    }
}
