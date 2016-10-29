package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import java.util.Random;

import edu.byu.cs.superasteroids.AsteroidsGame;
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
     * Constructor. Initialize the asteroid to a random position and velocity. Called the first time an asteroid is
     * created.
     *
     * @param type Type of the asteroid
     */
    public Asteroid(AsteroidType type, int levelWidth, int levelHeight) {
        super(null, 0, 0);
        random = new Random();
        numTimesSplit = 0;

        // Initialize and scale the height and width before position
        height = (int) (type.height * scale);
        width = (int) (type.width * scale);

        // Initialize to a random position
        worldPosition = initRandomPosition(levelWidth, levelHeight);

        // Call init last - it initializes remaining data.
        init(type);
    }

    /**
     * Constructor. Called when initializing an asteroid that is created from a split asteroid.
     *
     * @param type          Type
     * @param numTimesSplit How many times it has been split
     * @param parentPoint   Where the parent originitaed
     */
    public Asteroid(AsteroidType type, int numTimesSplit, PointF parentPoint, int parentHp) {
        super(null, 0, 0);
        this.numTimesSplit = numTimesSplit;
        this.hp = STARTING_HP / (2 * numTimesSplit);
        worldPosition = parentPoint;
        // TODO: make scale 1 / n, where n is number of pieces split;
        //      I should probably do this in the individual asteroid classes
        init(type);
    }

    /**
     * Initialize an asteroid object.
     * THE HEIGHT, WIDTH, AND POSITION MUST BE INITIALIZED BEFORE THIS METHOD IS CALLED OR A NULL POINTER EXCEPTION
     * WILL HAPPEN IN THE updateHitBox() METHOD.
     *
     * @param type - the type - octeroid, regular, growing
     */
    private void init(AsteroidType type) {
        asteroidType = type;
        this.imageId = asteroidType.imageId;

        // Initialize the hit box AFTER initializing height and width
        updateHitBox();

        // Initialize to random speed; the numbers were arbitrarily chosen, but they seem pretty good.
        float maxSpeed = 150;
        float minSpeed = 50;
        speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);

        // Initialize to random direction (angle in radians - 2PI is a full rotation)
        direction = random.nextFloat() * (float) Math.PI * 2;
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
        } while ((x < center.x + WIDTH_SAFEZONE / 2) && (x > center.x - WIDTH_SAFEZONE / 2) &&
                (y < center.y + HEIGHT_SAFEXONE / 2) && (y > center.y - HEIGHT_SAFEXONE / 2));

        return new PointF(x, y);
    }

    public int update(double elapsedTime) {

        // First, if the asteroid is dead (hp is below zero due to being hit by projectiles), just return right here
        // with a status saying that it is dead. It needs to be destroyed and split into multiple asteroids.
        if (hp <= 0) {
            numTimesSplit++;
            return -1;
        }

        // Otherwise, update the asteroid
        super.update(speed, direction, elapsedTime);

        // Ricochet if the asteroid is now out of bounds.
        int worldWidth = AsteroidsGame.getCurrentLevel().getLevelWidth();
        int worldHeight = AsteroidsGame.getCurrentLevel().getLevelHeight();
        if (outOfBounds(worldWidth, worldHeight)) {
            super.ricochet(worldWidth, worldHeight);
        }

        // Asteroid is still alive
        return 0;
    }

    public void touch(Projectile projectile) {
        int damage = projectile.getProjectileType().getDamage();
        this.hp -= damage;
    }

    public void touch(Ship ship) {
        this.hp -= 1;
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public AsteroidType getType() {
        return asteroidType;
    }

    public void setType(AsteroidType type) {
        this.asteroidType = type;
    }

}
