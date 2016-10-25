package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CoordinateString;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;

/**
 * Created by Marshall Garey
 * The class has all the information about the ship that the player is flying.
 */
public class Ship extends MovingObject {

    private final int MAX_HP = 5;

    public int getMAX_HP() {
        return MAX_HP;
    }

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
        super(x, y, hp, speed, direction);
        this.body = body;
        this.cannon = cannon;
        this.engine = engine;
        this.extraPart = extraPart;
        this.powerCore = powerCore;
    }

    /**
     * Initialize the ship at the beginning of the game
     */
    public void init() {
        setHp(MAX_HP);

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

    /*
     * TODO: use GraphicsUtils.MoveObject
     * TODO: change speed so that object will move
     */
    public void update(PointF movePoint, double elapsedTime) {

        // Update the angle that we're pointing
        if (movePoint != null) {
            direction = calculateAngleInRadians(movePoint);
            speed = GraphicsUtils.distance(movePoint, worldPosition);
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

        // Make the ship's position the same as the body position
        worldPosition = body.worldPosition;
    }

    /*
     * Returns the angle the ship needs to be (in radians)
     */
    private float calculateAngleInRadians(PointF movePoint) {
//        float diffY = worldPosition.y - movePoint.y; // because Y increases from top to bottom
        float diffY = movePoint.y - body.worldPosition.y; // because Y increases from top to bottom
        float diffX = movePoint.x - body.worldPosition.x; // because X increases from left to right
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

    /**
     * Computes the attach point for a ship part
     *
     * @param angle       - the angle to rotate the part by
     * @return point      - the location the part will be drawn
     */
    private PointF calculatePartPosition(PointF partOffset, float angle) {
        // Find the rotated offset
        PointF offset = GraphicsUtils.rotate(partOffset, angle);

        // Find the X coordinate
        float partLocationX = body.getWorldPosition().x + ((SCALE) * offset.x);

        // Find the Y coordinate
        float partLocationY = body.getWorldPosition().y + ((SCALE) * offset.y);

        return new PointF(partLocationX, partLocationY);
    }

    /**
     * Draws the ship
     */
    public void draw() {
        // Call draw individually on each part of the ship
        body.draw();
        cannon.draw();
        engine.draw();
        extraPart.draw();
    }

    /**
     * Load the ship images into memory
     *
     * @param contentManager The content manager of the active activity
     */
    public void loadContent(ContentManager contentManager) {
        body.loadImage(contentManager);
        cannon.loadImage(contentManager);
        engine.loadImage(contentManager);
        extraPart.loadImage(contentManager);
    }

    public void unloadContent(ContentManager contentManager) {
        body.unloadImage(contentManager);
        cannon.unloadImage(contentManager);
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
