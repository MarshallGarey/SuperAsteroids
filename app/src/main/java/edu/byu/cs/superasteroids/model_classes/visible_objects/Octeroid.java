package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey
 * Octeroids split into 8 pieces when they are destroyed.
 */
public class Octeroid extends Asteroid {

    public static final int MAX_TIMES_SPLIT = 1;
    public static final int NUM_SPLIT_ASTEROIDS = 8;

    public Octeroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(type, levelWidth, levelHeight);
    }

    public Octeroid(AsteroidType type, int numTimesSplit, PointF parentPoint, float parentScale) {
        // Make scale 1 / n, where n is number of pieces split;
        super(type, numTimesSplit, parentPoint, parentScale / NUM_SPLIT_ASTEROIDS);
    }
}
