package edu.byu.cs.superasteroids.model_classes.visible_objects;

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

    public GrowingAsteroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(type, levelWidth, levelHeight);
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
        width = (int)(width * scale);
        height = (int)(height * scale);
        updateHitBox();
    }
}
