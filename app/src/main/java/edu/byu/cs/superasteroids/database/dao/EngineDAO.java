package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;

/**
 * Created by Marshall Garey
 * This class defines all methods that access the EngineType database table.
 * These are the typical methods: add, remove, update, and query
 */
public class EngineDAO {

    // sort order = // put column name that I want here
    //

    private SQLiteDatabase db;

    public EngineDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets an EngineType object from the database by its name.
     * @param name - the name of the engine type
     * @return an EngineType object
     */
    public EngineType getByName(String name) {
        return null;
    }

    /**
     * Returns a list of all engine types in the database
     * @return
     */
    public ArrayList<EngineType> getAll() {
        // project all columns
        String[] projection = {
                Contract.EngineContract.COL_ATTACH,
                Contract.EngineContract.COL_HEIGHT,
                Contract.EngineContract.COL_IMAGE,
                Contract.EngineContract.COL_SPEED,
                Contract.EngineContract.COL_TURN,
                Contract.EngineContract.COL_WIDTH
        };
        // descending sort order is fine
        String sortOrder = Contract.EngineContract.COL_IMAGE + " DESC";

        // query the database
        Cursor c = db.query(
                Contract.EngineContract.TABLE_NAME,
                projection,
                null, // no selection
                null, // no selection args
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder
        );

        // Get the info from the cursor
        ArrayList<EngineType> engineTypes = new ArrayList<>();
        String file, attach;
        int width, height, speed, turn;
        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            attach = c.getString(c.getColumnIndex(Contract.EngineContract.COL_ATTACH));
            height = c.getInt(c.getColumnIndex(Contract.EngineContract.COL_HEIGHT));
            file = c.getString(c.getColumnIndex(Contract.EngineContract.COL_IMAGE));
            speed = c.getInt(c.getColumnIndex(Contract.EngineContract.COL_SPEED));
            turn = c.getInt(c.getColumnIndex(Contract.EngineContract.COL_TURN));
            width = c.getInt(c.getColumnIndex(Contract.EngineContract.COL_WIDTH));
            EngineType obj = new EngineType(file, width, height, attach, i, speed, turn);
            engineTypes.add(i, obj);
            i++;
        }
        c.close();
        return engineTypes;
    }

    /**
     * Adds an EngineType object to the database.
     * @param engineType - an EngineType object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(EngineType engineType) {
        ContentValues values = new ContentValues();
        values.put(Contract.EngineContract.COL_ATTACH, engineType.getAttachPoint().getPosition());
        values.put(Contract.EngineContract.COL_HEIGHT, engineType.getImageHeight());
        values.put(Contract.EngineContract.COL_IMAGE, engineType.getImageFile());
        values.put(Contract.EngineContract.COL_SPEED, engineType.getBaseSpeed());
        values.put(Contract.EngineContract.COL_TURN, engineType.getBaseTurnRate());
        values.put(Contract.EngineContract.COL_WIDTH, engineType.getImageWidth());
        long id = db.insert(Contract.EngineContract.TABLE_NAME, null, values);
        return id >= 0; // id is -1 if insert fails, zero or greater if insert succeeds
    }

    /**
     * Updates the database table entry with new information. It will search the database for the
     * name of the passed engine type and update that entry.
     * @param engineType - an EngineType object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(EngineType engineType) {
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
     * Removes an entry for an EngineType object in its table. Searches for the entry by the
     * name of the passed EngineType object.
     * @param engineType - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(EngineType engineType) {
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

