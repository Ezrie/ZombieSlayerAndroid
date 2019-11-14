package coreGame.Model;

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
        this.screen = screen;
        setPosition(_x,_y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

    public abstract void update(float _dt);

    public abstract void enemyHit();


}
