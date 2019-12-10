package coreGame.Model;
/**
 * This class defines an enemy subtype, zombies, to be used in the game repeatedly.
 *
 * Child of Enemy
 * Aware of HUD, PlayScreen, Survivor
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 12/10/2019
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import coreGame.Util.GameConstants;
import coreGame.View.Scenes.HUD;
import coreGame.View.Screens.PlayScreen;

public class Zombie extends Enemy {

    private final float ZOMBIE_VELOCITY = 0.2f;
    private final float ZOMBIE_MAX_SPEED = 0.7f;
    private final float ATTACK_DISTANCE = 1.5f;
    private final int DAMAGE_AMOUNT = 5;
    private final int MAX_HEALTH = 20;
    private int curHealth;

    private Sprite sprite;
    private boolean spriteIsFlipped;
    private Survivor player;

    private float bounceBack = 0.5f;
    private float hitboxRadius = 6f;
    private Vector2 direction;

    /**
     * Constructor for zombie objects. Requires the screen they came from and starting positions.
     *
     * @param _screen is the screen which the zombies will appear on.
     * @param _x is the starting x position, gotten from the tile map spawn objects.
     * @param _y is the starting y position, gotten from the tile map spawn objects.
     */
    public Zombie(PlayScreen _screen, float _x, float _y) {
        //Set up the b2body and positioning of the zombie.
        super(_screen, _x, _y);
        this.defineEnemy(_x, _y);
        this.player = _screen.player;


        //The texture used for the zombies is at [2][7] from the sprite images (spritesheet.png)
        TextureRegion zombieTexture = _screen.getTextures()[2][7];
        TextureRegion zTexture = new TextureRegion(zombieTexture);
        this.setRegion(zTexture);
        //Create a sprite from the given texture, and lay it on top of the b2body.
        this.sprite = new Sprite(zTexture);
        this.sprite.setBounds(this.sprite.getX(), this.sprite.getY(), GameConstants.SPRITE_SIZE, GameConstants.SPRITE_SIZE );
        //Flip the sprite when the direction is changed. Starting position is not flipped.
        this.spriteIsFlipped = false;

        //Initialize health and starting direction.
        this.curHealth = this.MAX_HEALTH;
        this.direction = new Vector2(0, 0);
    }

    /**
     * This method is called by the PlayScreen, and updates the positions of the zombies, also
     * removing them if their health is below zero.
     *
     * @param _dt is the time difference since the last update was called.
     */
    public void update(float _dt) {
        //If the health of the zombie is below zero, remove the object and it's attached resources.
        if (this.curHealth <= 0){
            this.dispose();
        }
        /*
        Checks if the zombie is active (within range of the player) and updates the sprite's
        position to be equal to the Box2D's position.
         */
        else if(isActive()) {
            this.updateZombiePosition();
            this.sprite.setPosition(this.getPositionX(), this.getPositionY());
        }
    }

    /**
     * Called by PlayScreen, this method renders the sprites onto the screen, flipping them if
     * the zombie is facing the negative x direction.
     *
     * @param batch is the pack of sprites used for the entire game.
     */
    public void draw(Batch batch) {
        if (direction.x < 0) {
            spriteIsFlipped = true;
        } else {
            spriteIsFlipped = false;
        }
        //Sets the sprite to be flipped horizontally only when spriteIsFlipped is true.
        sprite.setFlip(spriteIsFlipped, false);
        super.draw(batch);
        sprite.draw(batch);

    }

    /**
     * To prevent memory leaks, it's necessary to dispose of unused resources.
     */
    public void dispose() {
        //Remove the fixture from the body.
        this.b2body.destroyFixture(b2body.getFixtureList().first());
        //Remove the body from the world.
        world.destroyBody(this.b2body);
        //Remove the sprite's texture.
        sprite.getTexture().dispose();
    }

    /**
     * Using the WorldContactListener, this method logs the collision between the zombie and the
     * player, and reduces the player's health by the zombie's damage amount.
     */
    @Override
    public void damageSurvivor() {
        Gdx.app.log("Survivor-Zombie", "Collision");
        HUD.minusHealth(DAMAGE_AMOUNT);
    }

    /**
     * Creates a Box2D body for the zombies to have physics applications and movement.
     *
     * @param _x is the starting x position of the body.
     * @param _y is the starting y position of the body.
     */
    @Override
    protected void defineEnemy(float _x, float _y) {
        //Create the body.
        BodyDef bdef = new BodyDef();
        bdef.position.set(_x, _y);
        //Makes the zombie body dynamic; affected by physics within the Box2D world.
        bdef.type = BodyDef.BodyType.DynamicBody;
        //Creates a body given the definition, and puts it in the world.
        this.b2body = this.world.createBody(bdef);

        //This creates the polygon fixture that will collide with objects.
        FixtureDef fdef = new FixtureDef();
        //Restitution is how much bounce-back and object has.
        fdef.restitution = this.bounceBack;
        CircleShape shape = new CircleShape();
        //The size of the shape will determine how big of a hitbox the zombie has.
        shape.setRadius(this.hitboxRadius / GameConstants.PPM);
        fdef.shape = shape;

        //This defines the filters for zombies.
        fdef.filter.categoryBits = GameConstants.ZOMBIE_BIT;
        //The mask filter defines what the zombie can collide with.
        fdef.filter.maskBits = GameConstants.DEFAULT_BIT |
                GameConstants.COIN_BIT |
                GameConstants.BRICK_BIT |
                GameConstants.ZOMBIE_BIT |
                GameConstants.OBJECT_BIT |
                GameConstants.SURVIVOR_BIT;

        //The sensor makes the fixture to longer collide with anything in the box 2d world if set to true.
        fdef.isSensor = false;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * A helper method for update(_dt). It sets the zombie's velocity in the direction towards
     * the player.
     *
     */
    private void updateZombiePosition() {
        //This normalizes the direction. For any x and y, the hypotenuse will be 1.
        direction.x = direction.x / getDirectionHypotenuse();
        direction.y = direction.y / getDirectionHypotenuse();

        //With normalized direction, we can multiply by speed to get the desired movement.
        if (this.getVelocityX() <= ZOMBIE_MAX_SPEED) {
            this.setVelocityX(direction.x * ZOMBIE_VELOCITY);
        }
        if (this.getVelocityY() <= ZOMBIE_MAX_SPEED) {
            this.setVelocityY(direction.y * ZOMBIE_VELOCITY);
        }
    }
    /**
     * This method sees if the zombie is within range of the player by calculating the hypotenuse
     * of the triangle made between the zombie and the player.
     *
     * @return a boolean that describes if the zombie is active or not.
     */
    private boolean isActive() {
        //Don't trigger enemies that are further than the attack distance.
        if (getDirectionHypotenuse() > ATTACK_DISTANCE) {
            return false;
        }
        return true;
    }

    /**
     * A helper method that calculates the distance between to points (a^2 + b^2 = c^2).
     *
     * @return a float of the distance between two points.
     */
    private float getDirectionHypotenuse() {
        float directionX = player.getPositionX() - this.getPositionX();
        float directionY = player.getPositionY() - this.getPositionY();
        float hypotenuse = (float) Math.sqrt((directionX * directionX) + (directionY * directionY));
        return hypotenuse;
    }

    //==================================== Setters ==================================//

    public void setVelocityX(float _x) {
        this.b2body.setLinearVelocity(_x, this.b2body.getLinearVelocity().y);
    }

    public void setVelocityY(float _y) {
        this.b2body.setLinearVelocity(this.b2body.getLinearVelocity().x, _y);
    }

    public void minusHealth(int _value) {
        this.curHealth -= _value;
    }

    //==================================== Getters ==================================//

    public float getVelocityX() {
        return this.b2body.getLinearVelocity().x;
    }

    public float getVelocityY() {
        return this.b2body.getLinearVelocity().y;
    }

    public float getPositionX(){
        return b2body.getPosition().x - (sprite.getWidth() / 2f);
    }

    public float getPositionY(){
        return b2body.getPosition().y - (sprite.getWidth() / 2f);
    }
}
