package coreGame.Util;
/**
 * This class contains the constants used across multiple classes.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/29/2019
 */
public class GameConstants {

    //Width and height of the world's camera.
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;

    //Because box2D uses measurements in meters, we have to scale pixels to meters.
    public static final float PPM = 100;

    /*
    Each fixture has a filter. The two parts of a filter is category and mask.
    The "category" looks at what fixture is being used, and a "mask" looks at what the fixture
    can collide with.
     */
    public static final short DEFAULT_BIT = 1;
    public static final short SURVIVOR_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short ZOMBIE_BIT = 32;
}
