package edu.byu.cs.superasteroids;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.Level;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Asteroid;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Background;
import edu.byu.cs.superasteroids.model_classes.visible_objects.BgObject;
import edu.byu.cs.superasteroids.model_classes.visible_objects.MiniMap;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Projectile;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Ship;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Viewport;

/**
 * Created by Marshall Garey
 * This is a container for all the information about game objects that we need in order to play
 * the game. It just contains lists of various game data and loads the model from the database.
 */
public class AsteroidsGame {

    /**
     * Handle for other classes to be able to access game data
     */
    public static AsteroidsGame SINGLETON = new AsteroidsGame();

    private static Context gameContext;

    private final static String BACKGROUND_IMAGE_FILE = "images/space.bmp";

    /**
     * The game's database
     */
    private static Database database;

    /**
     * a list of types of asteroids in the game
     */
    private static ArrayList<AsteroidType> asteroidTypes;

    /**
     * a list of background object types in the game
     */
    private static ArrayList<String> bgObjectTypes;

    private static ArrayList<BgObject> bgObjects;

    /**
     * a list of levels in our game
     */
    private static ArrayList<Level> levels;

    /**
     * the current level the player is on
     */
    private static int currentLevelNumber;

    /**
     * a list of possible cannons for the ship
     */
    private static ArrayList<CannonType> cannons;

    /**
     * a list of possible engines for the ship
     */
    private static ArrayList<EngineType> engines;

    /**
     * a list of possible extra parts for the ship
     */
    private static ArrayList<ExtraPartType> extraPartTypes;

    /**
     * a list of possible main bodies for the ship
     */
    private static ArrayList<MainBodyType> mainBodies;

    /**
     * a list of possible power cores for the ship
     */
    private static ArrayList<PowerCoreType> powerCoreTypes;

    /**
     * list of all asteroids in the game - key is id, value is the asteroid
     */
    private static HashMap<Integer, Asteroid> asteroids;

    /**
     * the game's minimap
     */
    private static MiniMap miniMap;

    /**
     * the game's ship
     */
    private static Ship ship;

    /**
     * list of projectile's in the game; key is id
     */
    private static HashMap<Integer, Projectile> projectiles;

    /**
     * the game's background - consists of an image
     */
    private static Background background;

    /**
     * Private constructor to prevent other classes from instantiating this one
     */
    private AsteroidsGame() {}

    public static void initAsteroidsGame() {
        initAsteroidsGame(gameContext);
    }

    /**
     * Import all game data
     * TODO: there's a bug - the game crashes if I start the game, then press back, then select a new part of the
     * ship, then click start again. Look for references to this method (initAsteroidsGame) and also startActivity(game)
     */
    public static void initAsteroidsGame(Context context) {
        gameContext = context;
        // Open the database
        database = new Database(context);

        // get AsteroidTypes
        getAsteroidData();

        // get bgObjectTypes
        getObjectData();

        // get levels
        levels = new ArrayList<>();
        levels = database.getLevelDAO().getAll();

        // initialize to first level of the game
        currentLevelNumber = Level.STARTING_LEVEL;

        // get cannons
        cannons = new ArrayList<>();
        cannons = database.getCannonDAO().getAll();

        // get engines
        engines = new ArrayList<>();
        engines = database.getEngineDAO().getAll();

        // get extraPartTypes
        extraPartTypes = new ArrayList<>();
        extraPartTypes = database.getExtraPartDAO().getAll();

        // get mainBodies
        mainBodies = new ArrayList<>();
        mainBodies = database.getMainBodyDAO().getAll();

        // get powerCoreTypes
        powerCoreTypes = new ArrayList<>();
        powerCoreTypes = database.getPowerCoreDAO().getAll();

        // create new objects
        ship = new Ship(0, 0, 5, 0, 0, null, null, null, null, null);

        // Initialize viewport to avoid crashing
        int levelWidth = getLevel(currentLevelNumber).getLevelWidth();
        int levelHeight = getLevel(currentLevelNumber).getLevelHeight();
        Viewport.init(levelWidth,levelHeight);

        // Initialize the background
        background = new Background(BACKGROUND_IMAGE_FILE, levelWidth, levelHeight);

        // Initialize the list of background objects for the current level.
        initBgObjects();
    }

    /**
     * Initialize a list of the background object for the current level.
     */
    private static void initBgObjects() {

        bgObjects = new ArrayList<>();

        // Add all the objects for the current level:
        Level level = getLevel(currentLevelNumber);
        for (Level.LevelObject object : level.getLevelObjects()) {

            // Subtract 1 from the object id because they are referenced in the database starting at 1, not 0.
            String file = bgObjectTypes.get(object.getObjectId() - 1);

            // Add the object to the list.
            bgObjects.add(new BgObject(object.getPosX(), object.getPosY(), file, object.getScale()));
        }
    }

    /**
     * Loads content of the asteroids game into memory (the activities content manager)
     * @param contentManager A reference to the content manager of the calling class / activity
     */
    public static void loadContent(ContentManager contentManager) {
        // Load the ship parts:
        ship.loadContent(contentManager);

        // Load the background
        background.loadContent(contentManager);

        // Load the background objects
        initBgObjects();
        for (BgObject object : bgObjects) {
            object.loadImage(contentManager);
        }

        // TODO: load other content
    }

    /**
     * Unloads content of the asteroids game from memory (the activities content manager)
     * @param contentManager A reference to the content manager of the calling class / activity
     */
    public static void unloadContent(ContentManager contentManager) {
        // Unload the ship parts
        ship.unloadContent(contentManager);

        // Unload the background
        background.getBackgroundImage().unloadImage(contentManager);

        // Unload the background objects
        for (BgObject object : bgObjects) {
            object.unloadImage(contentManager);
        }
    }

    /**
     * Draws everything on the screen
     */
    public static void draw() {
        // Draw the background FIRST
        background.draw();

        // Draw the background images
        for (BgObject object : bgObjects) {
            object.draw();
        }

        // Draw the ship
        ship.draw();

        // Draw the asteroids

        // TODO: draw everything else - asteroids, minimap, projectiles, etc.
    }

    /**
     * Updates all objects in the game.
     */
    public static void update(PointF movePoint, double elapsedTime) {

        // Update the viewport first, because all other objects are drawn relative to it.
        Viewport.update();

        // Update the ship
        ship.update(movePoint, elapsedTime);
    }

    /**
     * Initialize the ship, asteroids, etc. at the beginning of the level
     */
    public static void initLevel() {
        // Initialize the viewport
        Level level = getLevel(currentLevelNumber);

        Viewport.init(level.getLevelWidth(), level.getLevelHeight());

        // Initialize the ship
        ship.init();
    }

    /**
     * Find the specified level. I do it in a loop, because I don't want to have to guarantee that the levels are
     * stored in consecutive order.
     * @param number The level number we're interested in. Starts at 1.
     * @return A reference to the level, or the first one in the list if it isn't found.
     */
    private static Level getLevel(int number) {
        for (Level l : levels) {
            if (l.getLevelNumber() == number) {
                return l;
            }
        }
        return levels.get(0);
    }


    public static Level getCurrentLevel() {
        return getLevel(currentLevelNumber);
    }

    /**
     * returns true if all ship parts have been selected, false otherwise
     */
    public static boolean hasAllParts() {
        return (ship.getBody() != null &&
            ship.getCannon() != null &&
            ship.getEngine() != null &&
            ship.getExtraPart() != null &&
            ship.getPowerCore() != null);
    }

    /**
     * Constructs a default ship
     */
    public static void setDefaultShip() {
        // Select parts:
        ship.setBody(mainBodies.get(1));
        ship.setExtraPart(extraPartTypes.get(1));
        ship.setEngine(engines.get(0));
        ship.setPowerCore(powerCoreTypes.get(1));
        ship.setCannon(cannons.get(0));

        // Initialize the level.
        initLevel();
    }

    /**
     * Gets asteroid data from the database
     */
    private static void getAsteroidData() {
        asteroidTypes = new ArrayList<>();
        asteroidTypes = database.getAsteroidTypeDAO().getAll();
    }

    /**
     * Gets background object data from the database
     */
    private static void getObjectData() {
        bgObjectTypes = new ArrayList<>();
        bgObjectTypes = database.getObjectDAO().getAll();
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public static AsteroidsGame getSINGLETON() {
        return SINGLETON;
    }

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        AsteroidsGame.database = database;
    }

    public static ArrayList<AsteroidType> getAsteroidTypes() {
        return asteroidTypes;
    }

    public static void setAsteroidTypes(ArrayList<AsteroidType> asteroidTypes) {
        AsteroidsGame.asteroidTypes = asteroidTypes;
    }

    public static ArrayList<String> getBgObjectTypes() {
        return bgObjectTypes;
    }

    public static void setBgObjectTypes(ArrayList<String> bgObjectTypes) {
        AsteroidsGame.bgObjectTypes = bgObjectTypes;
    }

    public static ArrayList<Level> getLevels() {
        return levels;
    }

    public static void setLevels(ArrayList<Level> levels) {
        AsteroidsGame.levels = levels;
    }

    public static ArrayList<CannonType> getCannons() {
        return cannons;
    }

    public static void setCannons(ArrayList<CannonType> cannons) {
        AsteroidsGame.cannons = cannons;
    }

    public static ArrayList<EngineType> getEngines() {
        return engines;
    }

    public static void setEngines(ArrayList<EngineType> engines) {
        AsteroidsGame.engines = engines;
    }

    public static ArrayList<ExtraPartType> getExtraPartTypes() {
        return extraPartTypes;
    }

    public static void setExtraPartTypes(ArrayList<ExtraPartType> extraPartTypes) {
        AsteroidsGame.extraPartTypes = extraPartTypes;
    }

    public static ArrayList<MainBodyType> getMainBodies() {
        return mainBodies;
    }

    public static void setMainBodies(ArrayList<MainBodyType> mainBodies) {
        AsteroidsGame.mainBodies = mainBodies;
    }

    public static ArrayList<PowerCoreType> getPowerCoreTypes() {
        return powerCoreTypes;
    }

    public static void setPowerCoreTypes(ArrayList<PowerCoreType> powerCoreTypes) {
        AsteroidsGame.powerCoreTypes = powerCoreTypes;
    }

    public static HashMap<Integer, Asteroid> getAsteroids() {
        return asteroids;
    }

    public static void setAsteroids(HashMap<Integer, Asteroid> asteroids) {
        AsteroidsGame.asteroids = asteroids;
    }

    public static MiniMap getMiniMap() {
        return miniMap;
    }

    public static void setMiniMap(MiniMap miniMap) {
        AsteroidsGame.miniMap = miniMap;
    }

    public static Ship getShip() {
        return ship;
    }

    public static void setShip(Ship ship) {
        AsteroidsGame.ship = ship;
    }

    public static HashMap<Integer, Projectile> getProjectiles() {
        return projectiles;
    }

    public static void setProjectiles(HashMap<Integer, Projectile> projectiles) {
        AsteroidsGame.projectiles = projectiles;
    }
}
