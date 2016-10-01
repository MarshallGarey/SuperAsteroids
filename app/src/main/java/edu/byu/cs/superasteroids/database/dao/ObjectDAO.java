package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;

/**
 * Created by Marshall Garey
 * Description of a background object
 */
public class ObjectDAO {

    private SQLiteDatabase db;

    public ObjectDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets a list of all ObjectTypes from the database.
     * @return a list of all objectTypes
     */
    public ArrayList<String> getAll() {
        String[] projection = {
                Contract.ObjectContract.COL_FILE // only a single column in this table
        };
        String sortOrder = Contract.ObjectContract.COL_FILE + " DESC"; // descending order is fine

        // query the database
        Cursor c = db.query(
                Contract.ObjectContract.TABLE_NAME,
                projection,
                null, // no selection
                null, // no selection args
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder
        );

        // gather the info from the cursor
        ArrayList<String> result = new ArrayList<>();
        c.moveToPosition(-1); // start before the array
        int colIndex = c.getColumnIndex(Contract.ObjectContract.COL_FILE); // get index of column
        while (c.moveToNext()) {
            result.add(c.getString(colIndex)); // add next bg object file
        }
        c.close(); // close the cursor
        return result;
    }

    /**
     * Adds a bg object to the database.
     * @param bgObject - a bgObject which contains all necessary data for the table entry.
     * @return true if successful, false if not
     */
    public boolean add(String bgObject) {
        ContentValues values = new ContentValues();
        values.put(Contract.ObjectContract.COL_FILE, bgObject);
        long id = db.insert(Contract.ObjectContract.TABLE_NAME, null, values);

        if (id < 0) return false; // insert returns -1 when it fails
        return true;
    }
}
