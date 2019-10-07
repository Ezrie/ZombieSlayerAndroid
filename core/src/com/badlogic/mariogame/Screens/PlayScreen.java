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

public class PlayScreen implements Screen {

    //Declares a new game.
    private MarioGame game;
    //Camera that follows the game.
    private OrthographicCamera gameCam;
    //Veiwport that determine the aspect ratio for different devices.
    private Viewport gamePort;
    //HUD displays the informational text across the top of the screen.
    private HUD hud;

    //Loads the map into the game.
    private TmxMapLoader mapLoader;
    private TiledMap map;
    //Creates a renderer for this map.
    private OrthogonalTiledMapRenderer renderer;

    //The world where all physics will take place using Box2D.
    private World world;
    private Box2DDebugRenderer b2dr;

    /**
     * The game's "world" where the camera, screen, map, and objects are created and rendered.
     *
     * @param _game
     */
    public PlayScreen(MarioGame _game) {

        //Creates a new instance of the game.
        this.game = _game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(GameConstants.V_WIDTH, GameConstants.V_HEIGHT, gameCam);
        hud = new HUD(game.batch);

        //Loads in the map asset and renderer.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //Camera starting position.
        float initCameraX = gamePort.getWorldWidth() / 2;
        float initCameraY = gamePort.getWorldHeight() / 2;
        float initCameraZ = 0;
        //Create the camera object.
        gameCam.position.set(initCameraX, initCameraY, initCameraZ);

        //Creates a new Box2D world for physical movements.
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

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
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            //Defines the height and width of the body object to be the same as the tile map's object.
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            //Creates a new fixture out of the body object.
            body.createFixture(fdef);
        }

        //Pipe, the third layer in the map is the pipe object.
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Brick, the fifth layer in the map is the brick object.
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Coin, the fourth layer in the map is the coin object.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
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
        if (Gdx.input.isTouched()) {
            //Moves the camera in the positive x direction.
            gameCam.position.x += 100 * _dt;
        }
    }

    /**
     * Updates the camera position continuously.
     *
     * @param _dt
     */
    public void update(float _dt) {
        handleInput(_dt);
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