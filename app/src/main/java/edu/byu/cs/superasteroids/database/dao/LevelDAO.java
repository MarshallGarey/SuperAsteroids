package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.Level;

/**
 * Created by Marshall Garey on 1/30/2016.
 * This class defines all methods that access the Level, LevelAsteroid, and LevelObject database
 * tables. The reason for defining methods for all three tables in this same class is that the
 * latter two objects are part of the Level model class. This does limit the flexibility of
 * updating, deleting, adding, and querying the LevelAsteroid and LevelObject tables, but all
 * necessary operations can still be performed.
 * These are the typical methods: add, remove, update, and query
 */
public class LevelDAO {

    private final String SELECT_ALL_INFO = "select * from " + Contract.LevelContract.TABLE_NAME;
    private final String SEL_ALL_ASTEROIDS = "select * from " + Contract.LevelAsteroidContract.TABLE_NAME;
    private final String SEL_ALL_OBJECTS = "select * from " + Contract.LevelObjectContract.TABLE_NAME;
    private final String[] EMPTY_STR_ARRAY = {};

    private SQLiteDatabase db;

    public LevelDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets a Level object from the database by its id. This also gets information about all of
     * the LevelObjects and LevelAsteroids belonging to the level from their respective database
     * tables.
     * @param id - the id of the level (also just the level number)
     * @return a Level object
     */
    public Level getLevelByID(int id) {
        return null;
    }

    public ArrayList<Level> getAll() {
        // project all columns of the Level table
        Cursor c = db.rawQuery(SELECT_ALL_INFO, EMPTY_STR_ARRAY);

        // Get the info from the cursor
        ArrayList<Level> levels = new ArrayList<>();
        String music, hint, title;
        int width, height, number;
        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            music = c.getString(c.getColumnIndex(Contract.LevelContract.COL_MUSIC));
            height = c.getInt(c.getColumnIndex(Contract.LevelContract.COL_HEIGHT));
            hint = c.getString(c.getColumnIndex(Contract.LevelContract.COL_HINT));
            number = c.getInt(c.getColumnIndex(Contract.LevelContract.COL_NUMBER));
            title = c.getString(c.getColumnIndex(Contract.LevelContract.COL_TITLE));
            width = c.getInt(c.getColumnIndex(Contract.LevelContract.COL_WIDTH));

            Level obj = new Level(number, title, hint, width, height, music);
            obj.setLevelAsteroids(new ArrayList<Level.LevelAsteroid>());
            obj.setLevelObjects(new ArrayList<Level.LevelObject>());
            levels.add(i, obj);
            i++;
        }
        c.close();

        // Now get LevelAsteroidS
        // query the database
        c = db.rawQuery(SEL_ALL_ASTEROIDS, EMPTY_STR_ARRAY);

        // Get the info from the cursor
        int asteroidNum, asteroidId, levelNum;
        c.moveToPosition(-1);
        i = 0;
        while (c.moveToNext()) {
            asteroidNum = c.getInt(c.getColumnIndex(Contract.LevelAsteroidContract.COL_ASTEROID_NUM));
            asteroidId = c.getInt(c.getColumnIndex(Contract.LevelAsteroidContract.COL_ID));
            levelNum = c.getInt(c.getColumnIndex(Contract.LevelAsteroidContract.COL_LEVEL_NUM));
            // add to correct level
            for (int j = 0; j < levels.size(); j++)
                if (levels.get(j).getLevelNumber() == levelNum) {
                    levels.get(j).addAsteroid(asteroidId, asteroidNum);
                    break;
                }
            i++;
        }
        c.close();

        // Get LevelObjects from database
        // query the database
        c = db.rawQuery(SEL_ALL_OBJECTS, EMPTY_STR_ARRAY);

        // Get the info from the cursor
        int objId, objLevelNum;
        String objPos;
        float objScale;
        c.moveToPosition(-1);
        i = 0;
        while (c.moveToNext()) {
            objId = c.getInt(c.getColumnIndex(Contract.LevelObjectContract.COL_ID));
            objPos = c.getString(c.getColumnIndex(Contract.LevelObjectContract.COL_POSITION));
            objLevelNum = c.getInt(c.getColumnIndex(Contract.LevelObjectContract.COL_LEVEL_NUM));
            objScale = (float)c.getDouble(c.getColumnIndex(Contract.LevelObjectContract.COL_SCALE));
            for (int j = 0; j < levels.size(); j++)
                if (levels.get(j).getLevelNumber() == objLevelNum) {
                    levels.get(j).addObject(objId, objPos, objScale);
                    break;
                }
            i++;
        }
        c.close();
        return levels;
    }

    /**
     * Adds a Level object to the database. Also adds all LevelAsteroids and LevelObjects belonging
     * to the level to their respective tables.
     * @param level - an Level object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(Level level) {
        ContentValues values = new ContentValues();
        // Add level to database
        values.put(Contract.LevelContract.COL_NUMBER, level.getLevelNumber());
        values.put(Contract.LevelContract.COL_TITLE, level.getTitle());
        values.put(Contract.LevelContract.COL_HINT, level.getHint());
        values.put(Contract.LevelContract.COL_WIDTH, level.getLevelWidth());
        values.put(Contract.LevelContract.COL_HEIGHT, level.getLevelHeight());
        values.put(Contract.LevelContract.COL_MUSIC, level.getMusic());
        long id = db.insert(Contract.LevelContract.TABLE_NAME, null, values);
        if (id < 0) return false; // id is -1 if insert fails, zero or greater if insert succeeds
        // Put all levelObjects into their database table
        for (int i = 0; i < level.getLevelObjects().size(); i++) {
            values = new ContentValues();
            Level.LevelObject levelObject = level.getLevelObjects().get(i);
            values.put(Contract.LevelObjectContract.COL_ID, levelObject.getObjectId());
            values.put(Contract.LevelObjectContract.COL_SCALE, levelObject.getScale());
            values.put(Contract.LevelObjectContract.COL_POSITION, levelObject.getPosStr());
            values.put(Contract.LevelObjectContract.COL_LEVEL_NUM, level.getLevelNumber());
            id = db.insert(Contract.LevelObjectContract.TABLE_NAME, null, values);
            if (id < 0) return false;
        }
        // Put all levelAsteroids into their database table
        for (int i = 0; i < level.getLevelAsteroids().size(); i++) {
            values = new ContentValues();
            Level.LevelAsteroid levelAsteroid = level.getLevelAsteroids().get(i);
            values.put(Contract.LevelAsteroidContract.COL_ASTEROID_NUM, levelAsteroid.getAsteroidNumber());
            values.put(Contract.LevelAsteroidContract.COL_LEVEL_NUM, level.getLevelNumber());
            values.put(Contract.LevelAsteroidContract.COL_ID, levelAsteroid.getAsteroidID());
            id = db.insert(Contract.LevelAsteroidContract.TABLE_NAME, null, values);
            if (id < 0) return false;
        }
        // No errors, success!
        return true;
    }
    /*
    public boolean addBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("genre", book.getGenre().toString());

        long id = db.insert("book", null, values);
        if (id >= 0) {
            book.setID(id);
            return true;
        }
        else {
            return false;
        }
    }
    */

    /**
     * Updates the database table entry with new information. It will search the database for the
     * id of the passed level and update that entry, as well as all the entries for LevelObjects
     * and LevelAsteroids that belong to that level.
     * @param level - a Level object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(Level level) {
        return false;
    }
    /*
    public boolean updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("genre", book.getGenre().toString());

        int rows = db.update("book", values, "id = " + book.getID(), null);
        return (rows == 1);
    }
    */

    /**
     * Removes an entry for a Level object in its table. Searches for the entry by the
     * id of the passed Level object.
     * @param level - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(Level level) {
        return false;
    }
    /*
    public boolean deleteBook(Book book) {
        int rows = db.delete("book", "id = " + book.getID(), null);
        if (rows == 1) {
            book.setID(-1);
            return true;
        }
        else {
            return false;
        }
    }
    */
}

