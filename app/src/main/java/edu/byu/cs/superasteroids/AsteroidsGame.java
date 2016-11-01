package edu.byu.cs.superasteroids;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.HashSet;

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
import edu.byu.cs.superasteroids.model_classes.visible_objects.GrowingAsteroid;
import edu.byu.cs.superasteroids.model_classes.visible_objects.MiniMap;
import edu.byu.cs.superasteroids.model_classes.visible_objects.MovingObject;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Octeroid;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Projectile;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Ship;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Viewport;

/**
 * Created by Marshall Garey
 * This is a container for all the information about game objects that we need in order to play
 * the game. It just contains lists of various game data and loads the model from the database.
 */
public class AsteroidsGame {

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

    private static ArrayList<Integer> asteroidTypeIndices;

    /**
     * lists of all asteroids in the level
     */
    private static HashSet<Asteroid> regularAsteroids;
    private static HashSet<GrowingAsteroid> growingAsteroids;
    private static HashSet<Octeroid> octeroidAsteroids;

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
     * the game's minimap
     */
    private static MiniMap miniMap;

    /**
     * the game's ship
     */
    private static Ship ship;

    /**
     * A list of active projectiles in the game.
     */
    private static HashSet<Projectile> projectiles;

    /**
     * the game's background - consists of an image
     */
    private static Background background;

    /**
     * Return value for the update function. This tells the game engine how to proceed.
     */
    public enum GAME_STATUS {
        GAME_OVER, WON_LEVEL, NORMAL
    }

    /**
     * Private constructor to prevent other classes from instantiating this one
     */
    private AsteroidsGame() {
    }

    public static void initAsteroidsGame() {
        initAsteroidsGame(gameContext);
    }

    /**
     * Import all game data
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
        Viewport.init(levelWidth, levelHeight);

        // Initialize the background
        background = new Background(BACKGROUND_IMAGE_FILE, levelWidth, levelHeight);

        // Initialize the list of background objects for the current level.
        initBgObjects();

        // Since I'm not getting the asteroid types in the same order as I read them in the JSON, I'm reordering them
        // like this. It's silly but works as a temporary fix. TODO: hopefully do this better
        asteroidTypeIndices = new ArrayList<>();
        asteroidTypeIndices.add(2);  // growing
        asteroidTypeIndices.add(1);  // octeroid
        asteroidTypeIndices.add(0);  // regular

        // Create an empty list of projectiles so they can be added in later when they're fired.
        projectiles = new HashSet<>();
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
     * Initialize the asteroids in the level.
     * Get the asteroids in the level by getting the level and looking at levelAsteroids. This tells me how many and
     * the type - well, it's the ID, but use the ID - 1 to index into the AsteroidTypes array to know which type.
     * I need to initialize them in random positions, making sure they don't spawn on the ship, and give them random
     * velocities. Make an init function in the Asteroid class.
     */
    private static void initAsteroids(Level level) {
        regularAsteroids = new HashSet<>();
        octeroidAsteroids = new HashSet<>();
        growingAsteroids = new HashSet<>();
        for (Level.LevelAsteroid levelAsteroid : level.getLevelAsteroids()) {
            int typeIndex = asteroidTypeIndices.get(levelAsteroid.getAsteroidID() - 1);
            // Add one asteroid for each
            for (int i = levelAsteroid.getAsteroidNumber(); i > 0; --i) {
                AsteroidType asteroidType = asteroidTypes.get(typeIndex);
                String typeString = asteroidType.getType();
                if (typeString.equals("regular")) {
                    regularAsteroids.add(new Asteroid(
                            asteroidType, level.getLevelWidth(), level.getLevelHeight()
                    ));
                }
                else if (typeString.equals("octeroid")) {
                    octeroidAsteroids.add(new Octeroid(
                            asteroidType, level.getLevelWidth(), level.getLevelHeight()
                    ));
                }
                else if (typeString.equals("growing")) {
                    growingAsteroids.add(new GrowingAsteroid(
                            asteroidType, level.getLevelWidth(), level.getLevelHeight()
                    ));
                }
                else {
                    // TODO: this is bad. Throw an error or something.
                }
            }
        }
    }

    /**
     * Loads content of the asteroids game into memory (the activities content manager)
     *
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

        // Load the asteroid types
        for (AsteroidType asteroidType : asteroidTypes) {
            asteroidType.loadImage(contentManager);
        }

        MovingObject.loadImpactSound(contentManager);
    }

    /**
     * Unloads content of the asteroids game from memory (the activities content manager)
     *
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

        // Unload the asteroid types
        for (AsteroidType asteroidType : asteroidTypes) {
            asteroidType.unloadImage(contentManager);
        }

        MovingObject.unloadImpactSound(contentManager);
    }

    /**
     * Draws everything on the screen
     */
    public static void draw() {
        // Draw the background FIRST. TODO: scale the background.
        background.draw();

        // Draw the background images
        for (BgObject object : bgObjects) {
            object.draw();
        }

        // Draw the ship
        ship.draw();

        // Draw the asteroids:

        // Regular
        for (Asteroid asteroid : regularAsteroids) {
            asteroid.draw();
        }

        // Octeroid
        for (Octeroid octeroid : octeroidAsteroids) {
            octeroid.draw();
        }

        // Growing
        for (GrowingAsteroid growingAsteroid : growingAsteroids) {
            growingAsteroid.draw();
        }

        for (Projectile projectile : projectiles) {
            projectile.draw();
        }
    }

    /**
     * Updates all objects in the game.
     */
    public static GAME_STATUS update(PointF movePoint, double elapsedTime, boolean fireProjectile) {

        // Update the viewport first, because all other objects are drawn relative to it.
        Viewport.update();

        // Update the ship. Returns nonzero if the ship died, so we can report to the game engine a game over.
        if (ship.update(movePoint, elapsedTime, fireProjectile, regularAsteroids, growingAsteroids,
                octeroidAsteroids) != 0) {
            return GAME_STATUS.GAME_OVER;
        }

        // Fire projectile
        if (fireProjectile) {
            projectiles.add(ship.fire());
        }

        // Update projectiles, keeping track of the ones that need to be removed.
        ArrayList<Projectile> toBeRemoved = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            if (projectile.update(elapsedTime, regularAsteroids, growingAsteroids, octeroidAsteroids) != 0) {
                toBeRemoved.add(projectile);
            }
        }

        // Now we can remove projectiles - we can't do it at the same time we iterate over the list
        for (Projectile projectile : toBeRemoved) {
            projectiles.remove(projectile);
        }

        // Update the asteroids.
        // TODO: return GAME_STATUS.NEW_LEVEL when all asteroids are destroyed.
        updateAsteroids(elapsedTime);

        return GAME_STATUS.NORMAL;
    }

    /**
     * Updates the asteroids, including collision detecting, splitting, and removing properly.
     * @param elapsedTime How much time since the last update.
     */
    private static void updateAsteroids(double elapsedTime) {
        // Update the regular asteroids. update returns nonzero if it has been destroyed.
        ArrayList<Asteroid> asteroidsToBeRemoved = new ArrayList<>();
        ArrayList<Asteroid> asteroidsToAdd = new ArrayList<>();
        for (Asteroid asteroid : regularAsteroids) {
            if (asteroid != null) {
                if (asteroid.update(elapsedTime) != 0) {
                    if (asteroid.getNumTimesSplit() <= Asteroid.MAX_TIMES_SPLIT) {
                        // Split the asteroid.
                        for (int i = 0; i < Asteroid.NUM_SPLIT_ASTEROIDS; i++) {
                            asteroidsToAdd.add(new Asteroid(
                                    asteroid.getType(), asteroid.getNumTimesSplit(), asteroid.getWorldPosition(),
                                    asteroid.getScale() / Asteroid.NUM_SPLIT_ASTEROIDS
                            ));
                        }
                    }
                    // Add the asteroid to the list to be removed.
                    asteroidsToBeRemoved.add(asteroid);
                }
            }
        }

        // Remove any asteroids that have been destroyed. This erases it.
        for (Asteroid asteroid : asteroidsToBeRemoved) {
            regularAsteroids.remove(asteroid);
        }

        // Add any new asteroids.
        for (Asteroid asteroid : asteroidsToAdd) {
            regularAsteroids.add(asteroid);
        }

        // Update the growing asteroids. update returns nonzero if it has been destroyed.
        ArrayList<GrowingAsteroid> growingAsteroidsToBeRemoved = new ArrayList<>();
        ArrayList<GrowingAsteroid> growingAsteroidsToAdd = new ArrayList<>();
        for (GrowingAsteroid asteroid : growingAsteroids) {
            if (asteroid != null) {
                if (asteroid.update(elapsedTime) != 0) {
                    if (asteroid.getNumTimesSplit() <= GrowingAsteroid.MAX_TIMES_SPLIT) {
                        // Split the asteroid.
                        for (int i = 0; i < GrowingAsteroid.NUM_SPLIT_ASTEROIDS; i++) {
                            growingAsteroidsToAdd.add(new GrowingAsteroid(
                                    asteroid.getType(), asteroid.getNumTimesSplit(), asteroid.getWorldPosition(),
                                    asteroid.getScale()
                            ));
                        }
                    }
                    // Add the asteroid to the list to be removed.
                    growingAsteroidsToBeRemoved.add(asteroid);
                }
            }
        }

        // Remove any asteroids that have been destroyed. This erases it.
        for (GrowingAsteroid asteroid : growingAsteroidsToBeRemoved) {
            growingAsteroids.remove(asteroid);
        }

        // Add any new asteroids.
        for (GrowingAsteroid asteroid : growingAsteroidsToAdd) {
            growingAsteroids.add(asteroid);
        }

        // Update the octeroid asteroids. update returns nonzero if it has been destroyed.
        ArrayList<Octeroid> octeroidsToBeRemoved = new ArrayList<>();
        ArrayList<Octeroid> octeroidsToAdd = new ArrayList<>();
        for (Octeroid asteroid : octeroidAsteroids) {
            if (asteroid != null) {
                if (asteroid.update(elapsedTime) != 0) {
                    if (asteroid.getNumTimesSplit() <= Octeroid.MAX_TIMES_SPLIT) {
                        // Split the asteroid.
                        for (int i = 0; i < Octeroid.NUM_SPLIT_ASTEROIDS; i++) {
                            octeroidsToAdd.add(new Octeroid(
                                    asteroid.getType(), asteroid.getNumTimesSplit(), asteroid.getWorldPosition(),
                                    asteroid.getScale()
                            ));
                        }
                    }
                    // Add the asteroid to the list to be removed.
                    octeroidsToBeRemoved.add(asteroid);
                }
            }
        }

        // Remove any asteroids that have been destroyed. This erases it.
        for (Octeroid asteroid : octeroidsToBeRemoved) {
            octeroidAsteroids.remove(asteroid);
        }

        // Add any new asteroids.
        for (Octeroid octeroid : octeroidsToAdd) {
            octeroidAsteroids.add(octeroid);
        }
    }

    /**
     * Initialize the ship, asteroids, etc. at the beginning of the level
     */
    public static void initLevel() {
        // Initialize the viewport for the current level.
        Level level = getLevel(currentLevelNumber);
        Viewport.init(level.getLevelWidth(), level.getLevelHeight());

        // Initialize the ship.
        ship.init();

        // Initialize the asteroids for the current level.
        initAsteroids(level);
    }

    /**
     * Find the specified level. I do it in a loop, because I don't want to have to guarantee that the levels are
     * stored in consecutive order.
     *
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

    /**
     * Gets the current level. Uses a more general method I already wrote.
     *
     * @return The current level
     */
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

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        AsteroidsGame.database = database;
    }

    public static ArrayList<CannonType> getCannons() {
        return cannons;
    }

    public static ArrayList<EngineType> getEngines() {
        return engines;
    }

    public static ArrayList<ExtraPartType> getExtraPartTypes() {
        return extraPartTypes;
    }

    public static ArrayList<MainBodyType> getMainBodies() {
        return mainBodies;
    }

    public static ArrayList<PowerCoreType> getPowerCoreTypes() {
        return powerCoreTypes;
    }

    public static Ship getShip() {
        return ship;
    }

    public static void setShip(Ship ship) {
        AsteroidsGame.ship = ship;
    }
}
