package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey
 * Growing asteroids continuously get bigger until they reach the maximum size.
 */
public class GrowingAsteroid extends Asteroid {

    /**
     * The maximum size of this type of asteroid.
     * TODO: Set this to a reasonable number.
     */
    private final int MAX_SIZE = 0;

    public GrowingAsteroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(type, levelWidth, levelHeight);
    }

    /**
     * TODO: Makes the asteroid get bigger. While increasing the scale, the hit box also needs to grow
     */
    public void grow() {

    }
}
