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
import coreGame.Model.Projectile;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;

public class WorldContactListener implements ContactListener {
    int count = 0;
    Fixture object;

    /*
    This method identifies what fixtures are colliding when they do collide.
     */
    @Override
    public void beginContact(Contact _contact) {

        Fixture fixA = _contact.getFixtureA();
        Fixture fixB = _contact.getFixtureB();

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
    private void objectIdentifier(Fixture _fixA, Fixture _fixB){

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
                objectIdentifierWithProjectile(_fixB, _fixA);
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

    private void objectIdentifierWithSurvivor(Fixture _fixture, Fixture _survivor){
        object = _fixture;
        //This fires the event between survivor and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).survivorCollision();
            Gdx.app.log("Survivor-Tile", "Collision");
        }
        /*
        //This fires the event between survivor and a projectile.
        else if (object.getUserData() != null && Projectile.class.isAssignableFrom(object.getUserData().getClass())) {
            //((InteractiveTileObject) object.getUserData()).survivorCollision();
            ((Projectile) object.getUserData()).damageSurvivor();
        }
         */
        //This fires the event between survivor and an enemy.
        else if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
                ((Enemy) object.getUserData()).damageSurvivor();
                Gdx.app.log("Survivor-Enemy", "Collision");
        }
    }

    private void objectIdentifierWithZombie(Fixture _fixture, Fixture _Zombie){
        object = _fixture;
        //This fires the event between a zombie and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            ((InteractiveTileObject) object.getUserData()).zombieHit();
            Gdx.app.log("Zombie-Tile", "Collision");
        }
        //This fires the event between a zombie and a survivor.
        else if (object.getUserData() != null && Survivor.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Enemy) _Zombie.getUserData()).damageSurvivor();
            Gdx.app.log("Zombie-Survivor", "Collision");
        }
        //This fires the event between a zombie and a projectile.
        else if (object.getUserData() != null && Projectile.class.isAssignableFrom(object.getUserData().getClass())) {
            ((Projectile) object.getUserData()).damageEnemy((Enemy) _Zombie.getUserData());
            ((Projectile) _fixture.getUserData()).destroy();
            Gdx.app.log("Zombie-Projectile", "Collision");
        }
    }

    private void objectIdentifierWithProjectile(Fixture _fixture, Fixture _Projectile) {
        object = _fixture;
        //This fires the event between a projectile and a tile.
        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
            //Set projectile to be destroyed.
            ((Projectile) _Projectile.getUserData()).destroy();
            Gdx.app.log("Projectile-Tile", "Collision");
        }
        /*
        //This fires the event between a projectile and a survivor.
        else if (object.getUserData() != null && Survivor.class.isAssignableFrom(object.getUserData().getClass())) {
            //Deal damage and set projectile to be destroyed.
            ((Survivor)object.getUserData()).minusHealthPoints((5);
            ((Projectile) _Projectile.getUserData()).destroy();
        }
         */
        //This fires the event between a projectile and a zombie.
        else if (object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
            //Deal damage and set projectile to be destroyed.
            ((Projectile) _Projectile.getUserData()).damageEnemy((Enemy)_fixture.getUserData());
            ((Projectile) _Projectile.getUserData()).destroy();
            Gdx.app.log("Projectile-Zombie", "Collision");
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
