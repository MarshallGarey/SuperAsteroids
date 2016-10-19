package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey
 * This class defines all methods that access the AsteroidType database table.
 * These are the typical methods: add, remove, update, and query
 */
public class AsteroidTypeDAO {

    private SQLiteDatabase db;

    public AsteroidTypeDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets an AsteroidType object from the database by its name.
     * @param name - the name of the engine type
     * @return an AsteroidType object
     */
    public AsteroidType getByName(String name) {
        return null;
    }

    /**
     * Gets all AsteroidType objects from the database
     */
    public ArrayList<AsteroidType> getAll() {
        // project all columns
        String[] projection = {
                Contract.AsteroidTypeContract.COL_NAME_ASTEROID_TYPE_IMAGE,
                Contract.AsteroidTypeContract.COL_NAME_HEIGHT,
                Contract.AsteroidTypeContract.COL_NAME_TYPE,
                Contract.AsteroidTypeContract.COL_NAME_WIDTH
        };
        // descending sort order is fine
        String sortOrder = Contract.AsteroidTypeContract.COL_NAME_ASTEROID_TYPE_IMAGE + " DESC";

        // query the database
        Cursor c = db.query(
                Contract.AsteroidTypeContract.TABLE_NAME,
                projection,
                null, // no selection
                null, // no selection args
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder
        );

        // Get the info from the cursor
        ArrayList<AsteroidType> asteroidTypes = new ArrayList<>();
        String file, type;
        int width, height;

        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            file = c.getString(c.getColumnIndex(Contract.AsteroidTypeContract.COL_NAME_ASTEROID_TYPE_IMAGE));
            width = c.getInt(c.getColumnIndex(Contract.AsteroidTypeContract.COL_NAME_WIDTH));
            height = c.getInt(c.getColumnIndex(Contract.AsteroidTypeContract.COL_NAME_HEIGHT));
            type = c.getString(c.getColumnIndex(Contract.AsteroidTypeContract.COL_NAME_TYPE));
            AsteroidType obj = new AsteroidType(file, width, height, type);
            asteroidTypes.add(i, obj);
            i++;
        }
        c.close();
        return asteroidTypes;
    }

    /**
     * Adds an AsteroidType object to the database.
     * @param asteroidType - an AsteroidType object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(AsteroidType asteroidType) {
        ContentValues values = new ContentValues();
        values.put(Contract.AsteroidTypeContract.COL_NAME_ASTEROID_TYPE_IMAGE, asteroidType.getImageFile());
        values.put(Contract.AsteroidTypeContract.COL_NAME_WIDTH, asteroidType.getWidth());
        values.put(Contract.AsteroidTypeContract.COL_NAME_HEIGHT, asteroidType.getHeight());
        values.put(Contract.AsteroidTypeContract.COL_NAME_TYPE, asteroidType.getType());
        long id = db.insert(Contract.AsteroidTypeContract.TABLE_NAME, null, values);
        return id >= 0; // id is -1 if insert fails, zero or greater if insert succeeds
    }

    /**
     * Updates the database table entry with new information. It will search the database for the
     * name of the passed asteroid type and update that entry.
     * @param asteroidType - an AsteroidType object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(AsteroidType asteroidType) {
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
     * Removes an entry for an AsteroidType object in its table. Searches for the entry by the
     * name of the passed AsteroidType object.
     * @param asteroidType - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(AsteroidType asteroidType) {
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
