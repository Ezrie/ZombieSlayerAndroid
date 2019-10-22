package com.badlogic.mariogame.Screens;
/**
 * This class updates and renders the game's world/map and the camera.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/06/2019
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.mariogame.GameConstants;
import com.badlogic.mariogame.Scenes.HUD;
import com.badlogic.mariogame.MarioGame;
import com.badlogic.mariogame.Sprites.Mario;

import static sun.audio.AudioPlayer.player;

public class PlayScreen implements Screen {

    //Declares a new game.
    private MarioGame game;
    //Camera that follows the game.
    private OrthographicCamera gameCam;
    //Veiwport that determine the aspect ratio for different devices.
    private Viewport gamePort;
    //HUD displays the informational text across the top of the screen.
    private HUD hud;
    private int maxSpeed = 2;
    private float speed = 0.15f;

    //Loads the map into the game.
    private TmxMapLoader mapLoader;
    private TiledMap map;
    //Creates a renderer for this map.
    private OrthogonalTiledMapRenderer renderer;

    //The world where all physics will take place using Box2D.
    private World world;
    private Box2DDebugRenderer b2dr;

    //Create the player object
    private Mario player;

    /**
     * The game's "world" where the camera, screen, map, and objects are created and rendered.
     *
     * @param _game
     */
    public PlayScreen(MarioGame _game) {

        //Creates a new instance of the game.
        this.game = _game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(GameConstants.V_WIDTH / GameConstants.PPM, GameConstants.V_HEIGHT / GameConstants.PPM, gameCam);
        hud = new HUD(game.batch);

        //Loads in the map asset and renderer.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameConstants.PPM);

        //Camera starting position.
        float initCameraX = gamePort.getWorldWidth() / 2;
        float initCameraY = gamePort.getWorldHeight() / 2;
        float initCameraZ = 0;
        //Create the camera object.
        gameCam.position.set(initCameraX, initCameraY, initCameraZ);

        //Creates a new Box2D world for physical movements.
        //-10 refers to the force of gravity (downwards in the y direction).
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        //New player object
        player = new Mario(world);
        player.b2body.setLinearDamping(5f);

        //Defines a new type of object which will surround the tiles that can be interacted with.
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //These will be moved into separate classes later.
        //Ground, the second layer in the map is the ground object. For each tile, create an object.
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            //Gets the rectangle object from the tile map.
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //Defines the type of the body object as static (not effected by the physics mechanic).
            bdef.type = BodyDef.BodyType.StaticBody;
            //Positions the start of the body object at the start of the tile.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);

            body = world.createBody(bdef);

            //Defines the height and width of the body object to be the same as the tile map's object.
            shape.setAsBox((rect.getWidth() / 2) / GameConstants.PPM, (rect.getHeight() / 2) / GameConstants.PPM);
            fdef.shape = shape;
            //Creates a new fixture out of the body object.
            body.createFixture(fdef);
        }

        //Pipe, the third layer in the map is the pipe object.
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //Defines the type of the body object as static (not effected by the physics mechanic).
            bdef.type = BodyDef.BodyType.StaticBody;
            //Positions the start of the body object at the start of the tile.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);

            body = world.createBody(bdef);

            //Defines the height and width of the body object to be the same as the tile map's object.
            shape.setAsBox((rect.getWidth() / 2) / GameConstants.PPM, (rect.getHeight() / 2) / GameConstants.PPM);
            fdef.shape = shape;
            //Creates a new fixture out of the body object.
            body.createFixture(fdef);
        }

        //Brick, the fifth layer in the map is the brick object.
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //Defines the type of the body object as static (not effected by the physics mechanic).
            bdef.type = BodyDef.BodyType.StaticBody;
            //Positions the start of the body object at the start of the tile.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);

            body = world.createBody(bdef);

            //Defines the height and width of the body object to be the same as the tile map's object.
            shape.setAsBox((rect.getWidth() / 2) / GameConstants.PPM, (rect.getHeight() / 2) / GameConstants.PPM);
            fdef.shape = shape;
            //Creates a new fixture out of the body object.
            body.createFixture(fdef);
        }

        //Coin, the fourth layer in the map is the coin object.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //Defines the type of the body object as static (not effected by the physics mechanic).
            bdef.type = BodyDef.BodyType.StaticBody;
            //Positions the start of the body object at the start of the tile.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);

            body = world.createBody(bdef);

            //Defines the height and width of the body object to be the same as the tile map's object.
            shape.setAsBox((rect.getWidth() / 2) / GameConstants.PPM, (rect.getHeight() / 2) / GameConstants.PPM);
            fdef.shape = shape;
            //Creates a new fixture out of the body object.
            body.createFixture(fdef);
        }
    }

    /**
     * To be implemented
     */
    @Override
    public void show() {

    }

    /**
     * Moves the camera whenever the game screen is touched.
     *
     * @param _dt
     */
    public void handleInput(float _dt) {

        //On a button press (up, down, right, or left) a linear impulse (from Box2d) will be applied.
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) ) && player.b2body.getLinearVelocity().y <= maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(0, speed), player.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(0, -speed), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= maxSpeed){
            player.b2body.applyLinearImpulse(new Vector2(speed, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -maxSpeed){
            player.b2body.applyLinearImpulse(new Vector2(-speed, 0), player.b2body.getWorldCenter(), true);
        }
    }

    /**
     * Updates the camera position continuously.
     *
     * @param _dt
     */
    public void update(float _dt) {
        handleInput(_dt);

        //How accurate the physics calculations will be to the real world. Updates 60 times a second.
        world.step(1/60f, 6, 2);
        //Move gamecam with player
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;

        gameCam.update();
        renderer.setView(gameCam);
    }

    /**
     * Clears the map and re-renders each update.
     *
     * @param _delta
     */
    @Override
    public void render(float _delta) {
        update(_delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //Renders the tiles as interactive objects
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    /**
     * Updates the gamePort if the window is re-sized.
     *
     * @param _width
     * @param _height
     */
    @Override
    public void resize(int _width, int _height) {
        gamePort.update(_width, _height);
    }

    /**
     * To be implemented
     */
    @Override
    public void pause() {

    }

    /**
     * To be implemented
     */
    @Override
    public void resume() {

    }

    /**
     * To be implemented
     */
    @Override
    public void hide() {

    }

    /**
     * To be implemented
     */
    @Override
    public void dispose() {

    }
}