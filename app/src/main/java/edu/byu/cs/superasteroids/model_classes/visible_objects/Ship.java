package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import java.util.HashSet;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;

/**
 * Created by Marshall Garey
 * The class has all the information about the ship that the player is flying.
 */
public class Ship extends MovingObject {

    /**
     * The ship's main body.
     */
    private MainBodyType body;

    /**
     * The ship's cannon.
     */
    private CannonType cannon;

    /**
     * The ship's engine.
     */
    private EngineType engine;

    /**
     * The ship's extra part.
     */
    private ExtraPartType extraPart;

    /**
     * The ship's power core.
     */
    private PowerCoreType powerCore;

    private int safeCount = 0;              // If 0, the ship will take damage from collisions with asteroids.
    private final int UPDATE_HZ = 60;       // Update happens 60 times per second
    private final int SAFE_MODE_TIME = 5;   // The ship is safe for 5 seconds
    private final int SAFE_MODE_COUNT = UPDATE_HZ * SAFE_MODE_TIME;

    private final int MAX_SHIP_HP = 5;
    public static final float SHIP_SCALE = (float) 0.2;

    /**
     * Typical constructor - initialize all the data
     *
     * @param x         x position
     * @param y         y position
     * @param hp        hit points
     * @param speed     ship speed
     * @param direction ship direction
     * @param body      ship body part
     * @param cannon    ship right wing (has a cannon)
     * @param engine    ship engine
     * @param extraPart ship left wing
     * @param powerCore ship power core part
     */
    public Ship(int x, int y, int hp, int speed, float direction, MainBodyType body,
                CannonType cannon, EngineType engine, ExtraPartType extraPart,
                PowerCoreType powerCore) {
        super(new PointF(x, y), speed, direction);
        this.scale = SHIP_SCALE;
        this.hp = hp;
        this.body = body;
        this.cannon = cannon;
        this.engine = engine;
        this.extraPart = extraPart;
        this.powerCore = powerCore;
        this.safeCount = 0;
    }

    /**
     * Initialize the ship at the beginning of the game
     */
    public void init() {
        setHp(MAX_SHIP_HP);

        // Initialize position of the ship in the center of the viewport
        setWorldPosition(Viewport.getView().centerX(), Viewport.getView().centerY());

        // Initialize the positions and bounds (hit box) of all parts of the ship
        body.worldPosition = this.worldPosition;
        body.updateHitBox();
        extraPart.initPartOffsets(body.getExtraAttach(), body);
        cannon.initPartOffsets(body.getCannonAttach(), body);
        engine.initPartOffsets(body.getEngineAttach(), body);
        calculatePartPositions(0);
        extraPart.updateHitBox();
        cannon.updateHitBox();
        engine.updateHitBox();

        // Initialize velocity
        setSpeed(0.0);
        setDirection(0.0);
    }

    /**
     * TODO: apply the speed boost from the power core; also, speed should probably also depend on the distance from
     * the touch point (don't move if you're at or close to the touch point)
     *
     */
    public int update(PointF movePoint, double elapsedTime, boolean fireProjectile,
                      HashSet<Asteroid> regularAsteroids,
                      HashSet<GrowingAsteroid> growingAsteroids,
                      HashSet<Octeroid> octeroids) {

        // If the ship is dead, return a nonzero status so the game engine can go into the game over state.
        if (hp <= 0) {
            return -1;
        }

        // Update the ship angle and speed.
        if (movePoint != null) {
            direction = calculateAngleInRadians(movePoint);

            // The ship can rotate but not move forward if firing a missile.
            if (fireProjectile || (GraphicsUtils.distance(movePoint, worldPosition) < 100)) {
                speed = 0;
            }
            else {
                speed = engine.getBaseSpeed();
            }

//            speed = fireProjectile ? 0 : engine.getBaseSpeed(); //GraphicsUtils.distance(movePoint,worldPosition);
        } else {
            speed = 0;
        }

        // Figure out the positions of the attachments of the ship.
        calculatePartPositions(direction);

        // Move all parts of the ship. This also updates the hit box.
        body.update(speed, direction, elapsedTime);
        cannon.update(speed, direction, elapsedTime);
        extraPart.update(speed, direction, elapsedTime);
        engine.update(speed, direction, elapsedTime);

        // Update the safe mode counter.
        if (safeCount != 0) {
            safeCount--;
        } else {
            // Test to see if the ship collided with any asteroids by testing for collisions with each part.
            // We can test here because we know the ship is not in safe mode.
            // Take some damage and put the ship in safe mode if it collides with an asteroid.
            if (collisionWithAsteroid(regularAsteroids, growingAsteroids, octeroids)) {
                safeCount = SAFE_MODE_COUNT;
                MovingObject.playImpactSound();
                this.hp--;
            }
        }

        // Make the ship's position the same as the body position
        worldPosition = body.worldPosition;
        return 0;
    }

    /**
     * Tests for collisions with asteroids.
     *
     * @param regularAsteroids List of regular asteroids.
     * @param octeroids        List of octeroids.
     * @param growingAsteroids List of growing asteroids.
     * @return True if the ship collided with an asteroid. False if not.
     */
    private boolean collisionWithAsteroid(HashSet<Asteroid> regularAsteroids,
                                          HashSet<GrowingAsteroid> growingAsteroids,
                                          HashSet<Octeroid> octeroids
                                          ) {
        for (Asteroid asteroid : regularAsteroids) {
            if (asteroid.collisionWith(body.getHitBox()) ||
                    asteroid.collisionWith(cannon.getHitBox()) ||
                    asteroid.collisionWith(extraPart.getHitBox()) ||
                    asteroid.collisionWith(engine.getHitBox())
                    ) {
                asteroid.touch(this);
                return true;

            }
        }

        for (Octeroid octeroid : octeroids) {
            if (octeroid.collisionWith(body.getHitBox()) ||
                    octeroid.collisionWith(cannon.getHitBox()) ||
                    octeroid.collisionWith(extraPart.getHitBox()) ||
                    octeroid.collisionWith(engine.getHitBox())
                    ) {
                octeroid.touch(this);
                return true;
            }
        }

        for (GrowingAsteroid growingAsteroid : growingAsteroids) {
            if (growingAsteroid.collisionWith(body.getHitBox()) ||
                    growingAsteroid.collisionWith(cannon.getHitBox()) ||
                    growingAsteroid.collisionWith(extraPart.getHitBox()) ||
                    growingAsteroid.collisionWith(engine.getHitBox())
                    ) {
                growingAsteroid.touch(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the angle the ship needs to be (in radians)
     */
    private float calculateAngleInRadians(PointF movePoint) {
        float diffY = movePoint.y - body.worldPosition.y;
        float diffX = movePoint.x - body.worldPosition.x;

        return (float) (Math.atan2(diffY, diffX) + (Math.PI / 2));
    }

    private void calculatePartPositions(float angle) {

        // Cannon (right wing) position:
        cannon.setWorldPosition(calculatePartPosition(
                cannon.getPartOffset(),
                angle
        ));

        // Extra part (left wing) position:
        extraPart.setWorldPosition(calculatePartPosition(
                extraPart.getPartOffset(),
                angle
        ));

        // Engine position:
        engine.setWorldPosition(calculatePartPosition(
                engine.getPartOffset(),
                angle
        ));
    }

    public Projectile fire() {
        float x = cannon.getEmitPoint().getxPos() + cannon.getProjectileType().getWidth() / 2;
        float y = cannon.getEmitPoint().getyPos();
        PointF startingPoint = calculatePartPosition(new PointF(x, y), direction);
        return cannon.fire(startingPoint, engine.getBaseSpeed());
    }

    /**
     * Computes the attach point for a ship part
     *
     * @param angle - the angle to rotate the part by
     * @return point      - the location the part will be drawn
     */
    private PointF calculatePartPosition(PointF partOffset, float angle) {
        // Find the rotated offset
        PointF offset = GraphicsUtils.rotate(partOffset, angle);

        // Find the X coordinate
        float partLocationX = body.getWorldPosition().x + ((SHIP_SCALE) * offset.x);

        // Find the Y coordinate
        float partLocationY = body.getWorldPosition().y + ((SHIP_SCALE) * offset.y);

        return new PointF(partLocationX, partLocationY);
    }

    /**
     * Draws the ship
     */
    public void draw() {

        // Don't draw if the ship is dead.
        if (hp <= 0) {
            return;
        }

        // Call draw individually on each part of the ship. Make the ship more transparent if it is safe.
        if (safeCount != 0) {
            int alpha = OPAQUE / 2;
            body.draw(alpha);
            cannon.draw(alpha);
            engine.draw(alpha);
            extraPart.draw(alpha);
        }
        else {
            body.draw();
            cannon.draw();
            engine.draw();
            extraPart.draw();
        }
    }

    /**
     * Load the ship images into memory
     *
     * @param contentManager The content manager of the active activity
     */
    public void loadContent(ContentManager contentManager) {
        body.loadImage(contentManager);
        cannon.loadContent(contentManager);
        engine.loadImage(contentManager);
        extraPart.loadImage(contentManager);
    }

    public void unloadContent(ContentManager contentManager) {
        body.unloadImage(contentManager);
        cannon.unloadContent(contentManager);
        engine.unloadImage(contentManager);
        extraPart.unloadImage(contentManager);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public MainBodyType getBody() {
        return body;
    }

    public void setBody(MainBodyType body) {
        this.body = body;
    }

    public CannonType getCannon() {
        return cannon;
    }

    public void setCannon(CannonType cannon) {
        this.cannon = cannon;
    }

    public EngineType getEngine() {
        return engine;
    }

    public void setEngine(EngineType engine) {
        this.engine = engine;
    }

    public ExtraPartType getExtraPart() {
        return extraPart;
    }

    public void setExtraPart(ExtraPartType extraPart) {
        this.extraPart = extraPart;
    }

    public PowerCoreType getPowerCore() {
        return powerCore;
    }

    public void setPowerCore(PowerCoreType powerCore) {
        this.powerCore = powerCore;
    }
}
