package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey on 2/9/2016.
 * Octeroids split into 8 pieces when they are destroyed.
 */
public class Octeroid extends Asteroid {
    public Octeroid(int x, int y, int hp, int speed, float direction,
                    AsteroidType type, int numTimesSplit) {
        super(x, y, hp, speed, direction, type, numTimesSplit);
    }
}
