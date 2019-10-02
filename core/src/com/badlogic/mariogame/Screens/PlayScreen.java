package com.badlogic.mariogame.Screens;
/**
 * This class updates and renders the game's world/map and the camera.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
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

public class PlayScreen implements Screen, GameConstants {

    //Declares a new game
    private MarioGame game;
    //Camera that follows the game
    private OrthographicCamera gameCam;
    //Veiwports determine the aspect ratio for different devices
    private Viewport gamePort;
    private HUD hud;

    //Loads the map into the game
    private TmxMapLoader mapLoader;
    //References the map itself (graphic)
    private TiledMap map;
    //Renders the map to the screen
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    //The game "world" where the camera, screen, map, and tile object are created and rendered
    public PlayScreen(MarioGame game) {
        PlayScreen.this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, gameCam);
        hud = new HUD(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        gameCam.position.set(gamePort.getWorldWidth() / TWO, gamePort.getWorldHeight() / TWO, CAM_Z);

        world = new World(new Vector2(ZERO, ZERO), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Ground, the second layer in the map is the ground object
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / TWO, rect.getY() + rect.getHeight() / TWO);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / TWO, rect.getHeight() / TWO);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Pipe, the third layer in the map is the pipe object
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / TWO, rect.getY() + rect.getHeight() / TWO);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / TWO, rect.getHeight() / TWO);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Brick, the fifth layer in the map is the brick object
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / TWO, rect.getY() + rect.getHeight() / TWO);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / TWO, rect.getHeight() / TWO);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Coin, the fourth layer in the map is the coin object
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / TWO, rect.getY() + rect.getHeight() / TWO);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / TWO, rect.getHeight() / TWO);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void show() {

    }

    //Updates the camera whenever the screen is touched (temp method for testing)
    public void handleInput(float _dt) {
        if (Gdx.input.isTouched()) {
            gameCam.position.x += 100 * _dt;
        }
    }

    //Updates the camera position continuously
    public void update(float _dt) {
        handleInput(_dt);
        gameCam.update();
        renderer.setView(gameCam);
    }

    //Clears the map and re-renders each update
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

    //Used if the game viewport is re-sized
    @Override
    public void resize(int _width, int _height) {
        gamePort.update(_width, _height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}