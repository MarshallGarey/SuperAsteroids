package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marshall Garey on 1/30/2016.
 * Helper class for the database. Opens the database by creating all the database tables.
 * Required by Android.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_FILE_NAME = "asteroids.sqlite";

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    /**
     * Creates all the database tables for the game.
     * @param db - a sqlite database for the game
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.AsteroidTypeContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.CannonContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.EngineContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.ExtraPartContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.LevelContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.LevelAsteroidContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.LevelObjectContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.MainBodyContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.ObjectContract.SQL_CREATE_TABLE);
        db.execSQL(Contract.PowerCoreContract.SQL_CREATE_TABLE);
    }

    /**
     * Since we don't upgrade the table, this just drops any existing database tables
     * and creates new ones. The oldversion and newversion parameters are unused.
     * @param db - a sqlite database for the game
     * @param oldversion - unused
     * @param newversion - unused
     */
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL(Contract.AsteroidTypeContract.SQL_DROP_TABLE);
        db.execSQL(Contract.CannonContract.SQL_DROP_TABLE);
        db.execSQL(Contract.EngineContract.SQL_DROP_TABLE);
        db.execSQL(Contract.ExtraPartContract.SQL_DROP_TABLE);
        db.execSQL(Contract.LevelContract.SQL_DROP_TABLE);
        db.execSQL(Contract.LevelAsteroidContract.SQL_DROP_TABLE);
        db.execSQL(Contract.LevelObjectContract.SQL_DROP_TABLE);
        db.execSQL(Contract.MainBodyContract.SQL_DROP_TABLE);
        db.execSQL(Contract.ObjectContract.SQL_DROP_TABLE);
        db.execSQL(Contract.PowerCoreContract.SQL_DROP_TABLE);
        onCreate(db);
    }

    /**
     * Just calls onUpgrade. The oldversion and newversion parameters are unused.
     * @param db - a sqlite database for the game
     * @param oldversion - unused
     * @param newversion - unused
     */
    public void onDowngrade(SQLiteDatabase db, int oldversion, int newversion) {
        onUpgrade(db, oldversion, newversion);
    }
}
