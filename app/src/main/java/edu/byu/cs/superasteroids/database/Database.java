package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.superasteroids.database.dao.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.database.dao.CannonDAO;
import edu.byu.cs.superasteroids.database.dao.EngineDAO;
import edu.byu.cs.superasteroids.database.dao.ExtraPartDAO;
import edu.byu.cs.superasteroids.database.dao.LevelDAO;
import edu.byu.cs.superasteroids.database.dao.MainBodyDAO;
import edu.byu.cs.superasteroids.database.dao.ObjectDAO;
import edu.byu.cs.superasteroids.database.dao.PowerCoreDAO;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.Level;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;

/**
 * Created by Marshall Garey on 2/9/2016.
 * The Database class uses the DBHelper (database helper) class to create the database tables.
 * The data importer will use the this class to populate the database. This class uses the DAO's to
 * populate the database.
 */
public class Database {

    /**
     * The database helper object.
     */
    private DBHelper dbHelper;

    /**
     * The actual database object.
     */
    private SQLiteDatabase database;

    /**
     * The context from which the database is made ("this" - which will be the data importer).
     * Required by Android.
     */
    private Context baseContext;

    /**
     * The actual AsteroidTypeDAO object.
     */
    private AsteroidTypeDAO asteroidTypeDAO;

    /**
     * The actual CannonDAO object.
     */
    private CannonDAO cannonDAO;

    /**
     * The actual EngineTypeDAO object.
     */
    private EngineDAO engineDAO;

    /**
     * The actual ExtraPartTypeDAO object.
     */
    private ExtraPartDAO extraPartDAO;

    /**
     * The actual LevelDAO object.
     */
    private LevelDAO levelDAO;

    /**
     * The actual MainBodyDAO object.
     */
    private MainBodyDAO mainBodyDAO;

    /**
     * The actual ObjectDAO object.
     */
    private ObjectDAO objectDAO;

    /**
     * The actual PowerCoreDAO object.
     */
    private PowerCoreDAO powerCoreDAO;

    // the constructor
    // initialize database open helper, open the database, create DAO's
    public Database(Context context) {
        this.baseContext = context; // context of the activity
        this.dbHelper = new DBHelper(baseContext); // create a database helper
        this.database = dbHelper.getWritableDatabase(); // open a read/write database
        // create DAOs
        asteroidTypeDAO = new AsteroidTypeDAO(this.database);
        cannonDAO = new CannonDAO(this.database);
        engineDAO = new EngineDAO(this.database);
        extraPartDAO = new ExtraPartDAO(this.database);
        levelDAO = new LevelDAO(this.database);
        mainBodyDAO = new MainBodyDAO(this.database);
        objectDAO = new ObjectDAO(this.database);
        powerCoreDAO = new PowerCoreDAO(this.database);
    }

    /**
     * Imports data from a JSON file name.
     * @param dataInputReader - input streamer that points to the beginning of the JSON file
     * @return true if successful, false if not
     */
    public boolean importData(InputStreamReader dataInputReader) {

        // clear out the old database
        dbHelper.onUpgrade(database, 0, 0);

        // make a JSON tree
        JSONObject rootObject;
        JSONObject root;
        try {
            root = new JSONObject(makeString(dataInputReader));
            rootObject = root.getJSONObject("asteroidsGame");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        database.beginTransaction();
        try {
            JSONObject elem;
            // Get background objects
            JSONArray objectsArray = rootObject.getJSONArray("objects");
            for (int i = 0; i < objectsArray.length(); i++) {
                String bgObject = objectsArray.getString(i);
                if (objectDAO.add(bgObject) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // Get asteroids
            AsteroidType asteroidType = null;
            objectsArray = rootObject.getJSONArray("asteroids");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                // create an asteroid type object
                asteroidType = new AsteroidType(elem);
                if (asteroidTypeDAO.add(asteroidType) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // Get levels
            Level level = null;
            objectsArray = rootObject.getJSONArray("levels");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                // This gets Level and its subclasses, levelObjects and levelAsteroids
                level = new Level(elem);
                if (levelDAO.add(level) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // main bodies
            MainBodyType mainBodyType = null;
            objectsArray = rootObject.getJSONArray("mainBodies");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                mainBodyType = new MainBodyType(
                        elem.getString("image"),
                        elem.getInt("imageWidth"),
                        elem.getInt("imageHeight"),
                        i,
                        elem.getString("cannonAttach"),
                        elem.getString("engineAttach"),
                        elem.getString("extraAttach")
                );
                if (mainBodyDAO.add(mainBodyType) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // cannons
            CannonType cannon = null;
            objectsArray = rootObject.getJSONArray("cannons");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                cannon = new CannonType(
                        elem.getString("image"),
                        elem.getInt("imageWidth"),
                        elem.getInt("imageHeight"),
                        elem.getString("attachPoint"),
                        i,
                        elem.getString("emitPoint"),
                        elem.getString("attackImage"),
                        elem.getInt("attackImageWidth"),
                        elem.getInt("attackImageHeight"),
                        elem.getString("attackSound"),
                        elem.getInt("damage")
                );
                if (cannonDAO.add(cannon) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // extra parts
            ExtraPartType extraPartType = null;
            objectsArray = rootObject.getJSONArray("extraParts");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                extraPartType = new ExtraPartType(
                        elem.getString("image"),
                        elem.getInt("imageWidth"),
                        elem.getInt("imageHeight"),
                        elem.getString("attachPoint")
                );
                if (extraPartDAO.add(extraPartType) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // engines
            EngineType engineType = null;
            objectsArray = rootObject.getJSONArray("engines");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                engineType = new EngineType(
                        elem.getString("image"),
                        elem.getInt("imageWidth"),
                        elem.getInt("imageHeight"),
                        elem.getString("attachPoint"),
                        i,
                        elem.getInt("baseSpeed"),
                        elem.getInt("baseTurnRate")
                );
                if (engineDAO.add(engineType) == false) {
                    database.endTransaction();
                    return false;
                }
            }

            // power cores
            PowerCoreType powerCoreType = null;
            objectsArray = rootObject.getJSONArray("powerCores");
            for (int i = 0; i < objectsArray.length(); i++) {
                elem = objectsArray.getJSONObject(i);
                powerCoreType = new PowerCoreType(
                        elem.getString("image"),
                        elem.getInt("cannonBoost"),
                        elem.getInt("engineBoost")
                );
                if (powerCoreDAO.add(powerCoreType) == false) {
                    database.endTransaction();
                    return false;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            database.endTransaction();
            return false;
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        return true;
    }

    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public Context getBaseContext() {
        return baseContext;
    }

    public AsteroidTypeDAO getAsteroidTypeDAO() {
        return asteroidTypeDAO;
    }

    public CannonDAO getCannonDAO() {
        return cannonDAO;
    }

    public EngineDAO getEngineDAO() {
        return engineDAO;
    }

    public ExtraPartDAO getExtraPartDAO() {
        return extraPartDAO;
    }

    public LevelDAO getLevelDAO() {
        return levelDAO;
    }

    public MainBodyDAO getMainBodyDAO() {
        return mainBodyDAO;
    }

    public ObjectDAO getObjectDAO() {
        return objectDAO;
    }

    public PowerCoreDAO getPowerCoreDAO() {
        return powerCoreDAO;
    }
}
