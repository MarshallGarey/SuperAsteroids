package edu.byu.cs.superasteroids.database;

import android.provider.BaseColumns;

/**
 * Created by Marshall Garey on 1/30/2016.
 * Defines the database.
 */
public final class Contract {

    // Empty constructor
    public Contract() {}

    /* Inner class that defines the table contents of the AsteroidType object */
    public static abstract class AsteroidTypeContract {
        public static final String TABLE_NAME = "AsteroidType";
        public static final String COL_NAME_ASTEROID_TYPE_IMAGE = "asteroidTypeImage";
        public static final String COL_NAME_WIDTH = "imageWidth";
        public static final String COL_NAME_HEIGHT = "imageHeight";
        public static final String COL_NAME_TYPE = "type";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"AsteroidType\" (\"asteroidTypeImage\" TEXT," +
                        "\"imageWidth\" INTEGER,\"imageHeight\" INTEGER,\"type\" TEXT);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class CannonContract {
        public static final String TABLE_NAME = "Cannon";
        public static final String COL_NAME_ATTACH = "attachPoint";
        public static final String COL_NAME_EMIT = "emitPoint";
        public static final String COL_NAME_FILE = "cannonImage";
        public static final String COL_NAME_WIDTH = "imageWidth";
        public static final String COL_NAME_HEIGHT = "imageHeight";
        public static final String COL_NAME_ATTACK_IMAGE = "attackImage";
        public static final String COL_NAME_ATTACK_WIDTH = "attackImageWidth";
        public static final String COL_NAME_ATTACK_HEIGHT = "attackImageHeight";
        public static final String COL_NAME_ATTACK_SOUND = "attackSound";
        public static final String COL_NAME_DAMAGE = "damage";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"Cannon\" (\"attachPoint\" TEXT,\"emitPoint\" " +
                        "TEXT,\"cannonImage\" TEXT,\"imageWidth\" INTEGER," +
                        "\"imageHeight\" INTEGER,\"attackImage\" TEXT," +
                        "\"attackImageWidth\" INTEGER,\"attackImageHeight\" INTEGER," +
                        "\"attackSound\" TEXT," + COL_NAME_DAMAGE + " INTEGER);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class EngineContract {
        public static final String TABLE_NAME = "Engine";
        public static final String COL_SPEED = "baseSpeed";
        public static final String COL_TURN = "baseTurnRate";
        public static final String COL_ATTACH = "attachPoint";
        public static final String COL_IMAGE = "engineImage";
        public static final String COL_WIDTH = "imageWidth";
        public static final String COL_HEIGHT = "imageHeight";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"Engine\" (\"baseSpeed\" INTEGER,\"baseTurnRate\" INTEGER," +
                "\"attachPoint\" TEXT,\"engineImage\" TEXT,\"imageWidth\" INTEGER," +
                "\"imageHeight\" INTEGER);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class ExtraPartContract {
        public static final String TABLE_NAME = "ExtraPart";
        public static String COL_ATTACH = "attachPoint";
        public static String COL_FILE = "extraPartImage";
        public static String COL_WIDTH = "imageWidth";
        public static String COL_HEIGHT = "imageHeight";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"ExtraPart\" (\"attachPoint\" TEXT,\"extraPartImage\" TEXT,\"imageWidth\" INTEGER,\"imageHeight\" INTEGER);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class LevelContract implements BaseColumns {
        public static final String TABLE_NAME = "Level";
        public static final String COL_NUMBER = "levelNumber";
        public static final String COL_TITLE = "title";
        public static final String COL_HINT = "hint";
        public static final String COL_WIDTH = "levelWidth";
        public static final String COL_HEIGHT = "levelHeight";
        public static final String COL_MUSIC = "music";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_NUMBER + " INTEGER," +
                        COL_TITLE + " TEXT," +
                        COL_HINT + " TEXT," +
                        COL_WIDTH + " INTEGER," +
                        COL_HEIGHT + " INTEGER," +
                        COL_MUSIC + " TEXT);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class LevelAsteroidContract implements BaseColumns {
        public static final String TABLE_NAME = "LevelAsteroid";
        public static final String COL_ID = "asteroidId";
        public static final String COL_ASTEROID_NUM = "asteroidNumber";
        public static final String COL_LEVEL_NUM = "levelNumber";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"LevelAsteroid\" (\"asteroidId\" INTEGER,\"asteroidNumber\" INTEGER, \"levelNumber\" INTEGER);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class LevelObjectContract implements BaseColumns {
        public static final String TABLE_NAME = "LevelObject";
        public static final String COL_ID = "objectId";
        public static final String COL_POSITION = "position";
        public static final String COL_SCALE = "scale";
        public static final String COL_LEVEL_NUM = "levelNumber";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"LevelObject\" (\"objectId\" INTEGER, \"position\" TEXT, \"scale\" FLOAT, \"levelNumber\" INTEGER);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class MainBodyContract {
        public static final String TABLE_NAME = "MainBody";
        public static final String COL_NAME_CANNON = "cannonAttach";
        public static final String COL_NAME_ENGINE = "engineAttach";
        public static final String COL_NAME_EXTRA = "extraAttach";
        public static final String COL_NAME_IMAGE = "image";
        public static final String COL_NAME_WIDTH = "imageWidth";
        public static final String COL_NAME_HEIGHT = "imageHeight";
        public static final String COL_NAME_ID = "id";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE \"MainBody\" (\"cannonAttach\" TEXT,\"engineAttach\" TEXT,\"extraAttach\" TEXT,\"image\" TEXT,\"imageWidth\" INTEGER,\"imageHeight\" INTEGER, \"id\" INTEGER);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class ObjectContract {
        public static final String TABLE_NAME = "Object";
        public static final String COL_FILE = "file";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (\"file\" TEXT)";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PowerCoreContract {
        public static final String TABLE_NAME = "PowerCore";
        public static final String COL_FILE = "powerCoreImage";
        public static final String COL_CANNON = "cannonBoost";
        public static final String COL_ENGINE = "engineBoost";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (\"cannonBoost\" INTEGER,\"engineBoost\" INTEGER,\"powerCoreImage\" TEXT);";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
