package coreGame.View.Screens;
/**
 * This class updates and renders the game's world/map and the camera.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/28/2019
 */

import coreGame.Events.WorldContactListener;
import coreGame.Model.Survivor;
import coreGame.Util.GameConstants;
import coreGame.Game.ZombieGame;
import coreGame.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import coreGame.View.Scenes.HUD;

public class PlayScreen implements Screen {
    private final int VEL_ITERATIONS = 6;
    private final int POS_ITERATIONS = 2;
    private final float FPS = 1 / 60f;
    private final int POS_MAX_SPEED = 1;
    private final int NEG_MAX_SPEED = -1;
    float speedX;
    float speedY;
    float DAMPING = 10f;
    //This creates Survivor.
    private Survivor player;
    //
    private TextureAtlas atlas;
    //Declares a new game.
    private ZombieGame game;
    //Camera that follows the game.
    private OrthographicCamera gameCam;
    //The viewport determines the aspect ratio for different devices.
    private Viewport gamePort;
    //HUD displays the informational text across the top of the screen.
    private HUD hud;

    //Loads the map into the game.
    private TmxMapLoader mapLoader;
    private TiledMap map;
    //Creates a renderer for this map.
    private OrthogonalTiledMapRenderer renderer;

    //The world where all physics will take place using Box2D. The world will have "bodies."
    private World world;
    //This renderer gives graphical representation of the bodies in the world.
    private Box2DDebugRenderer b2dr;

    /**
     * The game's "world" where the camera, screen, map, and objects are created and rendered.
     *
     * @param _game
     */
    public PlayScreen(ZombieGame _game) {
        atlas = new TextureAtlas("Bad.pack");
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
        world = new World(new Vector2(0, 0), true);
        player = new Survivor(world, this);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        //Linear damping slows down the player movement if no keys are being pressed.
        player.b2body.setLinearDamping(DAMPING);

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
     * This moves the camera whenever the game screen is touched.
     *
     * @param _dt
     */
    public void handleInput(float _dt) {
        speedX = player.b2body.getLinearVelocity().x;
        speedY = player.b2body.getLinearVelocity().y;

        //On a button press, an impulse is applied to the player in a specific direction.
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && speedY <= POS_MAX_SPEED)
            player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && speedX <= POS_MAX_SPEED)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && speedX >= NEG_MAX_SPEED)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && speedY >= NEG_MAX_SPEED)
            player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);

    }

    /**
     * This Updates the camera position continuously.
     *
     * @param _dt is delta time.
     */
    public void update(float _dt) {
        handleInput(_dt);
        //This sets the fps of the game. Iterations are just for precision.
        world.step(FPS, VEL_ITERATIONS, POS_ITERATIONS);
        //This updates the HUD, specifically the timer countdown.
        hud.update(_dt);
        //This updates the player position.
        player.update(_dt);
        //This makes the game camera follow the player.
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        // This renders only what can be seen by the player -- the gameCam.
        renderer.setView(gameCam);
    }

    /**
     * Clears the map and re-renders each update.
     * The _delta is the time in seconds since the last render.
     *
     * @param _delta
     */
    @Override
    public void render(float _delta) {
        update(_delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //Renders the tiles as interactive objects; this shows the render lines.
        b2dr.render(world, gameCam.combined);
        //This sets what we can see only in the game camera.
        game.batch.setProjectionMatrix(gameCam.combined);
        //This opens the "box" to put our textures for the game camera.
        game.batch.begin();
        //This draws the batch.
        player.draw(game.batch);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
    //=================== GETTERS =====================

    public TextureAtlas getAtlas() {
        return atlas;
    }


}

