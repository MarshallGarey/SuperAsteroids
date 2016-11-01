package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey
 * Growing asteroids continuously get bigger until they reach the maximum size.
 */
public class GrowingAsteroid extends Asteroid {

    /**
     * The maximum size of this type of asteroid.
     * TODO: Change this to MAX_SCALE and set it to a reasonable number.
     */
    private final float MAX_SCALE = 2;
    private final int GROW_COUNT = 180; // (this/60) seconds between growing
    private int growCount = 0;

    public GrowingAsteroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(type, levelWidth, levelHeight);
        growCount = 0;
    }

    public GrowingAsteroid(AsteroidType type, int numTimesSplit, PointF parentPoint, float parentScale) {
        // Make scale 1 / n, where n is number of pieces split;
        super(type, numTimesSplit, parentPoint, parentScale / NUM_SPLIT_ASTEROIDS);
        growCount = 0;
    }

    /**
     * Same as the super class, but also grows the asteroid about every few seconds.
     *
     * @param elapsedTime Time since the last update.
     * @return See super class.
     */
    @Override
    public int update(double elapsedTime) {
        if (growCount++ >= GROW_COUNT) {
            growCount = 0;
            grow();
        }
        return super.update(elapsedTime);
    }

    /**
     * TODO: call this somewhere
     * Makes the asteroid get bigger by increasing the scale.
     */
    public void grow() {
        if (scale >= MAX_SCALE) {
            return;
        }
        scale += 0.1;
        width = (int) (width * scale);
        height = (int) (height * scale);
        updateHitBox();
    }
}
