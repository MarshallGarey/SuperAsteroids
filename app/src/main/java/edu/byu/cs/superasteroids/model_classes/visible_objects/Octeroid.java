package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey
 * Octeroids split into 8 pieces when they are destroyed.
 */
public class Octeroid extends Asteroid {
    public Octeroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(type, levelWidth, levelHeight);
    }
}
