package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;

/**
 * Created by Marshall Garey
 * This class defines all methods that access the MainBody database table.
 * These are the typical methods: add, remove, update, and query
 */
public class MainBodyDAO {

    private final String SEL_ALL_BODIES = "select * from " + Contract.MainBodyContract.TABLE_NAME;
    private final String[] EMPTY_STR_ARRAY = {};
    private SQLiteDatabase db;

    public MainBodyDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets a MainBodyType object from the database by its name.
     * @param name - the name of the main body type
     * @return a MainBodyType object
     */
    public MainBodyType getByName(String name) {
        return null;
    }

    public ArrayList<MainBodyType> getAll() {
        // query the database
        Cursor c = db.rawQuery(SEL_ALL_BODIES, EMPTY_STR_ARRAY);

        // Get the info from the cursor
        ArrayList<MainBodyType> mainBodyTypes = new ArrayList<>();
        String file, cannonAttach, engineAttach, extraAttach;
        int width, height;
        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            extraAttach = c.getString(c.getColumnIndex(Contract.MainBodyContract.COL_NAME_EXTRA));
            engineAttach = c.getString(c.getColumnIndex(Contract.MainBodyContract.COL_NAME_ENGINE));
            cannonAttach = c.getString(c.getColumnIndex(Contract.MainBodyContract.COL_NAME_CANNON));
            file = c.getString(c.getColumnIndex(Contract.MainBodyContract.COL_NAME_IMAGE));
            height = c.getInt(c.getColumnIndex(Contract.MainBodyContract.COL_NAME_HEIGHT));
            width = c.getInt(c.getColumnIndex(Contract.MainBodyContract.COL_NAME_WIDTH));
            MainBodyType obj = new MainBodyType(file, width, height, i, cannonAttach, engineAttach, extraAttach);
            mainBodyTypes.add(i, obj);
            i++;
        }
        c.close();
        return mainBodyTypes;
    }

    /**
     * Adds a MainBodyType object to the database.
     * @param mainBodyType - a MainBodyType object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(MainBodyType mainBodyType) {
        ContentValues values = new ContentValues();
        values.put(Contract.MainBodyContract.COL_NAME_CANNON, mainBodyType.getCannonAttach().getPosition());
        values.put(Contract.MainBodyContract.COL_NAME_ENGINE, mainBodyType.getEngineAttach().getPosition());
        values.put(Contract.MainBodyContract.COL_NAME_EXTRA, mainBodyType.getExtraAttach().getPosition());
        values.put(Contract.MainBodyContract.COL_NAME_HEIGHT, mainBodyType.getImageHeight());
        values.put(Contract.MainBodyContract.COL_NAME_ID, mainBodyType.getId());
        values.put(Contract.MainBodyContract.COL_NAME_IMAGE, mainBodyType.getImageFile());
        values.put(Contract.MainBodyContract.COL_NAME_WIDTH, mainBodyType.getImageWidth());
        long id = db.insert(Contract.MainBodyContract.TABLE_NAME, null, values);
        return id >= 0; // id is -1 if insert fails, zero or greater if insert succeeds
    }

    /**
     * Updates the database table entry with new information. It will search the database for the
     * name of the passed main body type and update that entry.
     * @param mainBodyType - a MainBodyType object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(MainBodyType mainBodyType) {
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
     * Removes an entry for a MainBodyType object in its table. Searches for the entry by the
     * name of the passed MainBodyType object.
     * @param mainBodyType - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(MainBodyType mainBodyType) {
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
