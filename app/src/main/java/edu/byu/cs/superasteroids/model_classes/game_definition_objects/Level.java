package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model_classes.visible_objects.VisibleObject;

/**
 * Created by Marshall Garey
 * All the information about a level is stored in this class.
 */
public class Level {

    public static final int STARTING_LEVEL = 1;

    /**
     * The level number.
     */
    private int levelNumber;

    /**
     * The level title.
     */
    private String title;

    /**
     * A useful hint for the level to help the player rock the game!
     */
    private String hint;

    /**
     * The width of the level.
     */
    private int levelWidth;

    /**
     * The height of the level.
     */
    private int levelHeight;

    /**
     * A file path to the background music for the level.
     */
    private String music;

    /**
     * A list of LevelObjects belonging to the level.
     */
    private ArrayList<LevelObject> levelObjects;

    /**
     * A list of LevelAsteroids belonging to the level. Destroy all asteroids to pass the level!
     */
    private ArrayList<LevelAsteroid> levelAsteroids;

    /**
     * The LevelObject class defines an object that is somewhere in the level.
     */
    public class LevelObject {
        /**
         * The ID of the object.
         */
        private int objectId;

        /**
         * The object's position in the level.
         */
        private CoordinateString position;

        /**
         * A scale factor for the object's size.
         */
        private float scale;

        public LevelObject(JSONObject levelObj) throws JSONException {
            objectId = levelObj.getInt("objectId");
            position = new CoordinateString(levelObj.getString("position"));
            scale = (float)levelObj.getDouble("scale");
        }

        public LevelObject(int id, String position, float scale) {
            this.objectId = id;
            this.position = new CoordinateString(position);
            this.scale = scale;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public CoordinateString getPosition() {
            return position;
        }

        public String getPosStr() {
            return position.getPosition();
        }

        public int getPosX() {
            return position.getxPos();
        }

        public int getPosY() {
            return position.getyPos();
        }

        public void setPosition(CoordinateString position) {
            this.position = position;
        }

        public float getScale() {
            return scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }
    }

    /**
     * The LevelAsteroid class defines an asteroid inside the level.
     */
    public class LevelAsteroid {
        /**
         * The asteroid's unique id.
         */
        private int asteroidID;

        /**
         * How many asteroids in the level.
         */
        private int asteroidNumber;

        public LevelAsteroid(JSONObject asteroidObj) throws JSONException {
            asteroidID = asteroidObj.getInt("asteroidId");
            asteroidNumber = asteroidObj.getInt("number");
        }

        public LevelAsteroid(int asteroidID, int number) {
            this.asteroidID = asteroidID;
            this.asteroidNumber = number;
        }

        public int getAsteroidID() {
            return asteroidID;
        }

        public void setAsteroidID(int asteroidID) {
            this.asteroidID = asteroidID;
        }

        public int getAsteroidNumber() {
            return asteroidNumber;
        }

        public void setAsteroidNumber(int asteroidNumber) {
            this.asteroidNumber = asteroidNumber;
        }
    }

    public Level(JSONObject elem) throws JSONException {
        // Initialize fields
        levelNumber = elem.getInt("number");
        title = elem.getString("title");
        hint = elem.getString("hint");
        levelWidth = elem.getInt("width");
        levelHeight = elem.getInt("height");
        music = elem.getString("music");
        // Get levelObjects
        levelObjects = new ArrayList<LevelObject>();
        JSONArray objects = elem.getJSONArray("levelObjects");
        for (int i = 0; i < objects.length(); i++) {
            JSONObject levelObj = objects.getJSONObject(i);
            levelObjects.add(i, new LevelObject(levelObj));
        }
        // Get levelAsteroids
        levelAsteroids = new ArrayList<LevelAsteroid>();
        objects = elem.getJSONArray("levelAsteroids");
        for (int i = 0; i < objects.length(); i++) {
            JSONObject asteroidObj = objects.getJSONObject(i);
            levelAsteroids.add(i, new LevelAsteroid(asteroidObj));
        }
    }

    public Level(int number, String title, String hint, int width, int height, String music) {
        this.levelNumber = number;
        this.title = title;
        this.hint = hint;
        this.levelWidth = width;
        this.levelHeight = height;
        this.music = music;
    }

    public void addAsteroid(int id, int number) {
        levelAsteroids.add(levelAsteroids.size(), new LevelAsteroid(id, number));
    }

    public void addObject(int id, String position, float scale) {
        levelObjects.add(levelObjects.size(), new LevelObject(id, position, scale));
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public ArrayList<LevelObject> getLevelObjects() {
        return levelObjects;
    }

    public ArrayList<LevelAsteroid> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelObjects(ArrayList<LevelObject> levelObjects) {
        this.levelObjects = levelObjects;
    }

    public void setLevelAsteroids(ArrayList<LevelAsteroid> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }
}
