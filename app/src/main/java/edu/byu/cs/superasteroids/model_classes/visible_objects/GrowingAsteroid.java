package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey on 2/9/2016.
 * Growing asteroids continuously get bigger until they reach the maximum size.
 */
public class GrowingAsteroid extends Asteroid {

    /**
     * The maximum size of this type of asteroid.
     * TODO: Set this to a reasonable number.
     */
    private final int MAX_SIZE = 0;

    public GrowingAsteroid(int x, int y, int hp, int speed, float direction, AsteroidType type,
                           int numTimesSplit) {
        super(x, y, hp, speed, direction, type, numTimesSplit);
    }

    /**
     * Makes the asteroid get bigger.
     */
    public void grow() {

    }
}
