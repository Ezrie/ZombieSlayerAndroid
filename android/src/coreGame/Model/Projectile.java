package coreGame.Model;
/**
 * This class creates the body and fixture for a bullet, but also includes the logic
 * dealing with any bullet collision.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/28/2019
 */
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.Util.GameConstants;
import coreGame.View.Screens.PlayScreen;

public abstract class Projectile extends Sprite {
    protected World world;
    protected PlayScreen screen;


    public Projectile(PlayScreen _screen, float _x, float _y, float _dirX, float _dirY){
        this.world = _screen.getWorld();
        this.screen = _screen;

    }

    /**
     * This method creates the projectile's body and its fixtures.
     */
    public abstract void defineProjectile(float _x, float _y);

    /**
     * This method updates the position of the bullet, but also destroys the body if it is
     * set to be destroyed--bullet colliding with an object.
     * @param _dt
     */
    public abstract void update(float _dt);

    public abstract void dispose();

    public abstract void damageSurvivor();

    public abstract void damageEnemy(Enemy enemy);
}
