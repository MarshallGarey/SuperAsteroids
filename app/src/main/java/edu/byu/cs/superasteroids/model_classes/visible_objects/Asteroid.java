package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey on 2/4/2016.
 * This class is a container for info about an asteroid and is a superclass for the various types
 * of asteroids.
 */
public class Asteroid extends MovingObject{

    /**
     * Asteroid type - can be regular, growing, or octeroid
     */
    private AsteroidType type;

    /**
     * The number of times this asteroid has broken. When an asteroid is hit, it breaks into
     * multiple pieces (the number depends on the type of asteroid). An asteroid only breaks into
     * pieces twice before it is completely destroyed.
     */
    private int numTimesSplit;

    public Asteroid(int x, int y, int hp, int speed, float direction,
                    AsteroidType type, int numTimesSplit) {
        super(x, y, hp, speed, direction);
        this.type = type;
        this.numTimesSplit = numTimesSplit;
    }

    public AsteroidType getType() {
        return type;
    }

    public void setType(AsteroidType type) {
        this.type = type;
    }
}
