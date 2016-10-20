package edu.byu.cs.superasteroids;

import android.content.Context;
import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.Level;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Asteroid;
import edu.byu.cs.superasteroids.model_classes.visible_objects.BgObject;
import edu.byu.cs.superasteroids.model_classes.visible_objects.MiniMap;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Projectile;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Ship;

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

    /**
     * a list of levels in our game
     */
    private static ArrayList<Level> levels;

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
     * a list of all background objects in the game
     */
    private static ArrayList<BgObject> bgObjects;

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
     * Private constructor to prevent other classes from instantiating this one
     */
    private AsteroidsGame() {}

    /**
     * Import all game data
     */
    public static void initAsteroidsGame(Context context) {
        // Open the database
        database = new Database(context);

        // get AsteroidTypes
        getAsteroidData();

        // get bgObjectTypes
        getObjectData();

        // get levels
        levels = new ArrayList<>();
        levels = database.getLevelDAO().getAll();

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
    }

    /**
     * Loads content of the asteroids game into memory (the activities content manager)
     * @param contentManager A reference to the content manager of the calling class / activity
     */
    public void loadContent(ContentManager contentManager) {
        // Load the ship parts:
        ship.loadContent(contentManager);

        // TODO: load other content
    }

    /**
     * Unloads content of the asteroids game from memory (the activities content manager)
     * @param contentManager A reference to the content manager of the calling class / activity
     */
    public void unloadContent(ContentManager contentManager) {
        // Unload the ship parts
        ship.unloadContent(contentManager);
    }

    /**
     * Draws everything on the screen
     */
    public void draw() {
        // Draw the ship
        ship.draw();

        // TODO: draw everything else - asteroids, minimap, projectiles, etc.
    }

    /**
     * Updates stuff
     */
    public void update() {
        ship.update();
    }

    /**
     * Initialize the ship, asteroids, etc. at the beginning of the level
     */
    public void init() {
        ship.init();
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

        ship.init();
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

    public static ArrayList<BgObject> getBgObjects() {
        return bgObjects;
    }

    public static void setBgObjects(ArrayList<BgObject> bgObjects) {
        AsteroidsGame.bgObjects = bgObjects;
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
