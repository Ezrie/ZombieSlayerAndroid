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

import java.util.Date;
import java.sql.Timestamp;
import java.time.Instant;

import coreGame.Model.Enemy;
import coreGame.Model.InteractiveTileObject;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;

public class WorldContactListener implements ContactListener {
    int count = 0;
    Date startDate;
    Timestamp startTime;
    Date curDate;
    Timestamp curTime;

    /*
    This method identifies what fixtures are colliding when they do collide.
     */
    @Override
    public void beginContact(Contact _contact) {
        startDate = new Date();
        startTime = new Timestamp(startDate.getTime());

        Fixture fixA = _contact.getFixtureA();
        Fixture fixB = _contact.getFixtureB();
        Fixture object;

        objectIdentifier(fixA, fixB);

    }

        /*if(fixA.getUserData() == "survivor" || fixB.getUserData() == "survivor"){
            //If fixture A is survivor, then use fixA. Otherwise, use fixB.
            Fixture survivor = fixA.getUserData() == "survivor" ? fixA : fixB;
            //From previous statement, this finds out which fixture is the object(not survivor).
            Fixture object = survivor == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).objectHit();
            }*/

    //This is a helper method for beginContact to identify what is being collided.
    public void objectIdentifier(Fixture _fixA, Fixture _fixB){
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());

        switch (_fixA.getFilterData().categoryBits) {
            case GameConstants.SURVIVOR_BIT:
                count = 0;
                objectIdentifierWithSurvivor(_fixB, _fixA);
                break;
            case GameConstants.ZOMBIE_BIT:
                count = 0;
                objectIdentifierWithZombie(_fixB, _fixA);
                break;
            case GameConstants.PROJECTILE_BIT:
                count = 0;
                objectIdentifierWithSurvivor(_fixB, _fixA);
                break;
            default:
                {
                    count++;
                    if (count == 1){
                        objectIdentifier(_fixB, _fixA);
                        break;
                    }
                    else {
                        count = 0;
                        Gdx.app.log("unknown", "Collision");
                        //objectIdentifierWithSurvivor(_fixB);
                        break;
                    }
            }
        }
    }

    public void objectIdentifierWithSurvivor(Fixture _fixture, Fixture _survivor){
        Fixture object = _fixture;
        //This fires the event between survivor and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).survivorCollision();
        }
        //This fires the event between survivor and an enemy.
        else if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
            if ((curTime.getTime() - startTime.getTime()) > Enemy.damageDeltaTime) {
                ((Enemy) object.getUserData()).damageSurvivor();
                curTime = new Timestamp(curDate.getTime());
            }
        }
    }

    public void objectIdentifierWithZombie(Fixture _fixture, Fixture _Zombie){
        Fixture object = _fixture;
        //This fires the event between a zombie and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).zombieHit();
        }
        //This fires the event between a zombie and a survivor.
        else if (object.getUserData() != null && Survivor.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Enemy) _Zombie.getUserData()).damageSurvivor();
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
