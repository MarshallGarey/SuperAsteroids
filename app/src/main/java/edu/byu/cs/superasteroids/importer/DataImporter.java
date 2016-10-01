package edu.byu.cs.superasteroids.importer;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.byu.cs.superasteroids.database.Database;

/**
 * Created by Marshall Garey on 2/11/2016.
 * Imports the game data from a JSON file.
 */
public class DataImporter implements IGameDataImporter {

    private Context context;

    /**
     * The database for the program.
     */
    private Database db;

    public DataImporter(Context c) {
        this.context = c;
    }

    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        boolean result = false;

        // Instance the database
        db = new Database(context);

        // Let the database handle the specifics of importing the data.
        result = db.importData(dataInputReader);
        return result;
    }
}
