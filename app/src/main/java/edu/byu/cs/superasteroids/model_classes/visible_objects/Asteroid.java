package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import java.sql.Time;
import java.util.Random;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.AsteroidType;

/**
 * Created by Marshall Garey
 * This class is a container for info about an asteroid and is a superclass for the various types
 * of asteroids.
 */
public class Asteroid extends MovingObject {

    private AsteroidType asteroidType;
    private final int MAX_TIMES_SPLIT = 2;
    private Random random;

    /**
     * The number of times this asteroid has broken. When an asteroid is hit, it breaks into
     * multiple pieces (the number depends on the type of asteroid). An asteroid only breaks into
     * pieces twice before it is completely destroyed.
     */
    private int numTimesSplit;

    /**
     * Constructor. Initialize the asteroid to a random position and velocity.
     *
     * @param type  Type of the asteroid
     */
    public Asteroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(
                null,               // position - initialize right after this
                0,                 // speed - TODO: make random
                0                   // direction - TODO: make random
        );
        numTimesSplit = 0;
        asteroidType = type;
        height = type.height;
        width = type.width;
        scale = 1;
        float maxSpeed = 150;
        float minSpeed = 50;
        speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
        direction = random.nextFloat() * (float)Math.PI * 2;    // 2PI is a full rotation
        random = new Random();
        worldPosition = initRandomPosition(levelWidth, levelHeight);
        updateHitBox();
    }

    /**
     * Return a starting point for an asteroid. The point cannot overlap with the ship, which starts in the center of
     * the level. This must also ensure that the entire asteroid is inside the level (no points right on the edge).
     *
     * @param levelWidth  Width of the level
     * @param levelHeight Height of the level
     * @return The starting position of the asteroid.
     */
    private PointF initRandomPosition(int levelWidth, int levelHeight) {
        float x;
        float y;
        PointF center;

        // Arbitrarily chosen
        int WIDTH_SAFEZONE = 500;
        int HEIGHT_SAFEXONE = 500;
        int minimumX = width / 2;
        int maximumX = levelWidth - width / 2;
        int minimumY = height / 2;
        int maximumY = levelHeight - height / 2;

        do {
            // Select a random point such that the entire asteroid is inside the level.
            x = random.nextInt(maximumX - minimumX + 1) + minimumX;
            y = random.nextInt(maximumY - minimumY + 1) + minimumY;
            center = new PointF(levelWidth / 2, levelHeight / 2);

            // Make sure the point is not within the "safezone" - close to the ship
        } while((x < center.x + WIDTH_SAFEZONE / 2) && (x > center.x - WIDTH_SAFEZONE / 2) &&
                (y < center.y + HEIGHT_SAFEXONE / 2) && (y > center.y - HEIGHT_SAFEXONE / 2));

        return new PointF(x, y);
    }

    @Override
    public void draw() {
        this.imageId = asteroidType.imageId;
        super.draw();
    }

    public void update(double elapsedTime) {
        super.update(speed, direction, elapsedTime);
        GraphicsUtils.ricochetObject(worldPosition, hitBox, direction, AsteroidsGame.getCurrentLevel().getLevelWidth(),
                AsteroidsGame.getCurrentLevel().getLevelHeight());
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Getters and setters --------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    public AsteroidType getType() {
        return asteroidType;
    }

    public void setType(AsteroidType type) {
        this.asteroidType = type;
    }
}
