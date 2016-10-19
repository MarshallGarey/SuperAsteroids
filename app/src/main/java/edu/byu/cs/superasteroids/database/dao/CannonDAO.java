package edu.byu.cs.superasteroids.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Contract;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;

/**
 * Created by Marshall Garey
 * This class defines all methods that access the CannonType database table.
 * These are the typical methods: add, remove, update, and query
 */
public class CannonDAO {

    private SQLiteDatabase db;

    public CannonDAO(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Gets a CannonType object from the database by its name.
     * @param name - the name of the cannon type
     * @return a CannonType object
     */
    public CannonType getByName(String name) {
        return null;
    }

    public ArrayList<CannonType> getAll() {
        // project all columns
        String[] projection = {
                Contract.CannonContract.COL_NAME_ATTACH,
                Contract.CannonContract.COL_NAME_ATTACK_HEIGHT,
                Contract.CannonContract.COL_NAME_ATTACK_IMAGE,
                Contract.CannonContract.COL_NAME_ATTACK_SOUND,
                Contract.CannonContract.COL_NAME_ATTACK_WIDTH,
                Contract.CannonContract.COL_NAME_DAMAGE,
                Contract.CannonContract.COL_NAME_EMIT,
                Contract.CannonContract.COL_NAME_FILE,
                Contract.CannonContract.COL_NAME_HEIGHT,
                Contract.CannonContract.COL_NAME_WIDTH,
        };
        // descending sort order is fine
        String sortOrder = Contract.CannonContract.COL_NAME_FILE + " DESC";

        // query the database
        Cursor c = db.query(
                Contract.CannonContract.TABLE_NAME,
                projection,
                null, // no selection
                null, // no selection args
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder
        );

        // Get the info from the cursor
        ArrayList<CannonType> cannonTypes = new ArrayList<>();
        String file, attach, emit, attackImage, attackSound;
        int width, height, attackWidth, attackHeight, damage;
        c.moveToPosition(-1);
        int i = 0;
        while (c.moveToNext()) {
            attach = c.getString(c.getColumnIndex(Contract.CannonContract.COL_NAME_ATTACH));
            attackHeight = c.getInt(c.getColumnIndex(Contract.CannonContract.COL_NAME_ATTACK_HEIGHT));
            attackImage = c.getString(c.getColumnIndex(Contract.CannonContract.COL_NAME_ATTACK_IMAGE));
            attackSound = c.getString(c.getColumnIndex(Contract.CannonContract.COL_NAME_ATTACK_SOUND));
            attackWidth = c.getInt(c.getColumnIndex(Contract.CannonContract.COL_NAME_ATTACK_WIDTH));
            damage = c.getInt(c.getColumnIndex(Contract.CannonContract.COL_NAME_DAMAGE));
            emit = c.getString(c.getColumnIndex(Contract.CannonContract.COL_NAME_EMIT));
            file = c.getString(c.getColumnIndex(Contract.CannonContract.COL_NAME_FILE));
            height = c.getInt(c.getColumnIndex(Contract.CannonContract.COL_NAME_HEIGHT));
            width = c.getInt(c.getColumnIndex(Contract.CannonContract.COL_NAME_WIDTH));
            CannonType obj = new CannonType(file, width, height, attach, i, emit, attackImage,
                attackWidth, attackHeight, attackSound, damage);
            cannonTypes.add(i, obj);
            i++;
        }
        c.close();
        return cannonTypes;
    }

    /**
     * Adds a CannonType object to the database.
     * @param cannonType - a CannonType object which contains all necessary data for the
     *                     table entry.
     * @return true if successful, false if not
     */
    public boolean add(CannonType cannonType) {
        ContentValues values = new ContentValues();
        values.put(Contract.CannonContract.COL_NAME_ATTACH, cannonType.getAttachPoint().getPosition());
        values.put(Contract.CannonContract.COL_NAME_EMIT, cannonType.getEmitPoint().getPosition());
        values.put(Contract.CannonContract.COL_NAME_FILE, cannonType.getImageFile());
        values.put(Contract.CannonContract.COL_NAME_WIDTH, cannonType.getWidth());
        values.put(Contract.CannonContract.COL_NAME_HEIGHT, cannonType.getHeight());
        values.put(Contract.CannonContract.COL_NAME_ATTACK_IMAGE, cannonType.getProjectileType().getImageFile());
        values.put(Contract.CannonContract.COL_NAME_ATTACK_WIDTH, cannonType.getProjectileType().getWidth());
        values.put(Contract.CannonContract.COL_NAME_ATTACK_HEIGHT, cannonType.getProjectileType().getHeight());
        values.put(Contract.CannonContract.COL_NAME_ATTACK_SOUND, cannonType.getProjectileType().getAttackSoundFile());
        values.put(Contract.CannonContract.COL_NAME_DAMAGE, cannonType.getProjectileType().getDamage());
        long id = db.insert(Contract.CannonContract.TABLE_NAME, null, values);
        return id >= 0; // id is -1 if insert fails, zero or greater if insert succeeds
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
     * name of the passed cannon type and update that entry.
     * @param cannonType - a CannonType object
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean update(CannonType cannonType) {
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
     * Removes an entry for a CannonType object in its table. Searches for the entry by the
     * name of the passed CannonType object.
     * @param cannonType - the object to remove from the table.
     * @return true if successful, false if not. For example, this would return false if the
     * database table entry was not found.
     */
    public boolean remove(CannonType cannonType) {
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

