package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;

/**
 * Created by Marshall Garey
 * This class defines all methods that access the ExtraPartType database table.
 * These are the typical methods: add, remove, update, and query
 */
public class ExtraPartDAO {

    private final String SEL_ALL_PARTS = "select * from " + Contract.ExtraPartContract.TABLE_NAME;
    private final String[] EMPTY_STR_ARRAY = {};

    private SQLiteDatabase db;

    public ExtraPartDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets an ExtraPartType object from the database by its name.
     * @param name - the name of the extra part type
     * @return an ExtraPartType object
     */
    public ExtraPartType getByName(String name) {
        return null;
    }

    public ArrayList<ExtraPartType> getAll() {
        // query the database
        Cursor c = db.rawQuery(SEL_ALL_PARTS, EMPTY_STR_ARRAY);

        // Get the info from the cursor
        ArrayList<ExtraPartType> extraPartTypes = new ArrayList<>();
        String file, attach;
        int width, height;
        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            attach = c.getString(c.getColumnIndex(Contract.ExtraPartContract.COL_ATTACH));
            file = c.getString(c.getColumnIndex(Contract.ExtraPartContract.COL_FILE));
            height = c.getInt(c.getColumnIndex(Contract.ExtraPartContract.COL_HEIGHT));
            width = c.getInt(c.getColumnIndex(Contract.ExtraPartContract.COL_WIDTH));
            ExtraPartType obj = new ExtraPartType(file, width, height, attach);
            extraPartTypes.add(i, obj);
            i++;
        }
        c.close();
        return extraPartTypes;
    }

    /**
     * Adds an ExtraPartType object to the database.
     * @param extraPartType - an ExtraPartType object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(ExtraPartType extraPartType) {
        ContentValues values = new ContentValues();
        values.put(Contract.ExtraPartContract.COL_ATTACH, extraPartType.getAttachPoint().getPosition());
        values.put(Contract.ExtraPartContract.COL_FILE, extraPartType.getImageFile());
        values.put(Contract.ExtraPartContract.COL_WIDTH, extraPartType.getWidth());
        values.put(Contract.ExtraPartContract.COL_HEIGHT, extraPartType.getHeight());
        long id = db.insert(Contract.ExtraPartContract.TABLE_NAME, null, values);
        return id >= 0; // id is -1 if insert fails, zero or greater if insert succeeds
    }

    /**
     * Updates the database table entry with new information. It will search the database for the
     * name of the passed extra part type and update that entry.
     * @param extraPartType - an ExtraPartType object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(ExtraPartType extraPartType) {
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
     * Removes an entry for an ExtraPartType object in its table. Searches for the entry by the
     * name of the passed ExtraPartType object.
     * @param extraPartType - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(ExtraPartType extraPartType) {
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

