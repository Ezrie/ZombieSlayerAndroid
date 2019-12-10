package coreGame.View.Screens;
/**
 * This class updates and renders the game's world/map and the camera.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/28/2019
 */

import android.content.Context;
import android.content.Intent;

import coreGame.Events.WorldContactListener;
import coreGame.Model.Bullet;
import coreGame.Model.Enemy;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;
import coreGame.Game.ZombieGame;
import coreGame.Tools.B2WorldCreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.menu.Views.GameEnd;

import java.util.ArrayList;

import coreGame.View.Scenes.HUD;

public class PlayScreen implements Screen {

    private Context ctx;

    private final int VEL_ITERATIONS = 6;
    private final int POS_ITERATIONS = 2;
    private final float FPS = 1 / 60f;

    //This creates Survivor.
    public Survivor player;
    //Creates an arrayList for bullets.
    ArrayList<Bullet> bullets;
    ArrayList<Body> allBodies;
    ArrayList<Body> toBeDeleted;
    private float curDelta;
    private final float cooldown = 1f;

    //Declares a new game.
    private ZombieGame game;
    //Camera that follows the game.
    private OrthographicCamera gameCam;
    //The viewport determines the aspect ratio for different devices.
    private Viewport gamePort;
    //HUD displays the informational text across the top of the screen.
    public HUD hud;

    //Loads the map into the game.
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private Texture spriteSheet;
    //Cuts the sprite sheet into individual textures.
    private TextureRegion[][] tiles;
    //Creates a renderer for this map.
    private OrthogonalTiledMapRenderer renderer;

    //The world where all physics will take place using Box2D. The world will have "bodies."
    private World world;
    //This renderer gives graphical representation of the bodies in the world.
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    /**
     * The game's "world" where the camera, screen, map, and objects are created and rendered.
     *
     * @param _game
     */
    public PlayScreen(ZombieGame _game, Context ctx) {
        this.ctx = ctx;
        //Loads sprite sheet image.
        spriteSheet = new Texture(Gdx.files.internal("spritesheet.png"));
        tiles = TextureRegion.split(spriteSheet,16,16);
        //Creates a new instance of the game.
        this.game = _game;
        //New world with no gravity and allows sleeping bodies.
        world = new World(new Vector2(0, 0), true);
        player = new Survivor(this);
        bullets = new ArrayList<>();

        allBodies = new ArrayList<>();
        toBeDeleted = new ArrayList<>();

        gameCam = new OrthographicCamera();
        //Zoom level
        gamePort = new ExtendViewport(3f, 3f, gameCam);
        //Camera starting position.
        float initCameraX = gamePort.getWorldWidth() / 2;
        float initCameraY = gamePort.getWorldHeight() / 2;
        float initCameraZ = 0;
        gameCam.position.set(initCameraX, initCameraY, initCameraZ);
        //gameCam.setToOrtho(false, gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2);

        //gamePort.setScreenSize(GameConstants.V_WIDTH, GameConstants.V_HEIGHT);
        hud = new HUD(game.batch, ctx);

        //Loads in the map asset and renderer.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Mapv2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameConstants.PPM);

        //Creates a new Box2D world for physical movements.
        Box2D.init();
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());
    }


    /**
     * This method is called to show this screen as the current screen.
     * To be implemented
     */
    @Override
    public void show() {

    }

    /**
     * This updates the camera position continuously. Called by render(float _dt).
     *
     * @param _dt is the change in time since the last called update.
     */
    public void update(float _dt) {
        curDelta += _dt;

        //This sets the fps of the game. Iterations are just for precision for the physics.
        world.step(FPS, VEL_ITERATIONS, POS_ITERATIONS);

        //This updates the HUD, specifically the timer countdown.
        hud.update(_dt);
        if(hud.getHealth() == 0 || hud.getTime() == 0) {
                Intent theEnd = new Intent(ctx, GameEnd.class);
                theEnd.putExtra("isWin", false);
                ctx.startActivity(theEnd);
        }
        //Pass it hud for now. Will make controller later
        player.update(_dt, hud);


        //Fires weapon if the cooldown isn't active.
        if (this.hud.button.isButtonPressed() && curDelta >= cooldown){
            bullets.add(new Bullet(this, player.getPositionX(), player.getPositionY(), player.getDirection(this.hud).x, player.getDirection(this.hud).y));
            curDelta = 0;
        }
        /*
        for (Enemy enemy : creator.getZombies()) {
            enemy.update(_dt);
        }
        */

        for (Bullet bullet : bullets){
            bullet.update(_dt);
        }


        /*

        if(creator.getZombies().isEmpty()){
            Intent theEnd = new Intent(ctx, GameEnd.class);
            theEnd.putExtra("isWin", true);
            ctx.startActivity(theEnd);
        }
        */
        //This makes the game camera follow the player.
        gameCam.position.x = player.getPositionX();
        gameCam.position.y = player.getPositionY();
        gameCam.update();
        // This renders only what can be seen by the player -- the gameCam.
        renderer.setView(gameCam);
    }



    /**
     * Clears the map and re-renders each update.
     *
     * @param _delta is the time in seconds since the last render.
     */
    @Override
    public void render(float _delta) {
        update(_delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders the tiles as interactive objects; this shows the render lines.
        b2dr.render(world, gameCam.combined);

        renderer.render();

        //Deletes the bodies which have been assigned to be destroyed by the contact listener.
        for (Body body : toBeDeleted) {
            world.destroyBody(body);
        }
        toBeDeleted.clear();

        //This sets what we can see only in the game camera.
        game.batch.setProjectionMatrix(gameCam.combined);
        //This opens the "box" to put our textures for the game camera.
        game.batch.begin();

        //This draws the batch.
        player.draw(game.batch);

        for (Bullet bullet : bullets){
            bullet.draw(game.batch);
        }
/*
        for (Enemy enemy : creator.getZombies())
            enemy.draw(game.batch);

 */
        //This stops putting textures for the game camera.
        game.batch.end();

        //This draws the what is in the HUD camera.
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
     * This method hides the screen when the screen is no longer the current screen.
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
        player.dispose();
        /*
        for (Enemy enemy : creator.getZombies()) {
            enemy.dispose();
        }

         */
        for (Bullet bullet : bullets){
            bullet.dispose();
        }
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public void addToDestroyList(Body _body) {
        toBeDeleted.add(_body);
    }

    //=================== GETTERS =====================

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }
    public TextureRegion[][] getTextures() {
        return tiles;
    }
}

