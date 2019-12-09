package coreGame.Model;
/**
 * This class serves as a parent class for any type of enemy.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/14/2019
 */
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import coreGame.View.Screens.PlayScreen;

public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Enemy(PlayScreen _screen, float _x, float _y){
        this.world = _screen.getWorld();
        this.screen = _screen;
    }

    protected abstract void defineEnemy(float _x, float _y);

    public abstract void update(float _dt);

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public abstract void damageSurvivor();

    public abstract void dispose();

}
