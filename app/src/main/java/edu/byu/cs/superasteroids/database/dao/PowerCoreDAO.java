package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;

/**
 * Created by Marshall Garey on 1/30/2016.
 * This class defines all methods that access the PowerCore database table.
 * These are the typical methods: add, remove, update, and query
 */
public class PowerCoreDAO {

    private final String SEL_ALL = "select * from " + Contract.PowerCoreContract.TABLE_NAME;
    private final String[] EMPTY_STR_ARRAY = {};
    private SQLiteDatabase db;

    public PowerCoreDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets a PowerCoreType object from the database by its name.
     * @param name - the name of the main body type
     * @return a PowerCoreType object
     */
    public PowerCoreType getByName(String name) {
        return null;
    }

    /**
     * Gets all info from power core database
     * @return list of PowerCoreType
     */
    public ArrayList<PowerCoreType> getAll() {
        // query the database
        Cursor c = db.rawQuery(SEL_ALL, EMPTY_STR_ARRAY);

        // Get the info from the cursor
        ArrayList<PowerCoreType> powerCoreTypes = new ArrayList<>();
        String file;
        int cannonBoost, engineBoost;
        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            file = c.getString(c.getColumnIndex(Contract.PowerCoreContract.COL_FILE));
            cannonBoost = c.getInt(c.getColumnIndex(Contract.PowerCoreContract.COL_CANNON));
            engineBoost = c.getInt(c.getColumnIndex(Contract.PowerCoreContract.COL_ENGINE));
            PowerCoreType obj = new PowerCoreType(file, cannonBoost, engineBoost);
            powerCoreTypes.add(i, obj);
            i++;
        }
        c.close();
        return powerCoreTypes;
    }

    /**
     * Adds a PowerCoreType object to the database.
     * @param powerCoreType - a PowerCoreType object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(PowerCoreType powerCoreType) {
        ContentValues values = new ContentValues();
        values.put(Contract.PowerCoreContract.COL_CANNON, powerCoreType.getCannonBoost());
        values.put(Contract.PowerCoreContract.COL_ENGINE, powerCoreType.getEngineBoost());
        values.put(Contract.PowerCoreContract.COL_FILE, powerCoreType.getFile());
        long id = db.insert(Contract.PowerCoreContract.TABLE_NAME, null, values);
        return id >= 0; // id is -1 if insert fails, zero or greater if insert succeeds
    }

    /**
     * Updates the database table entry with new information. It will search the database for the
     * name of the passed power core type and update that entry.
     * @param powerCoreType - a PowerCoreType object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(PowerCoreType powerCoreType) {
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
     * Removes an entry for a PowerCoreType object in its table. Searches for the entry by the
     * name of the passed PowerCoreType object.
     * @param powerCoreType - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(PowerCoreType powerCoreType) {
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
