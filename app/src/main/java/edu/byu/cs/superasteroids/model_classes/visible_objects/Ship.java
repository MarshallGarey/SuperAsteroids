package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.content.ContentManager;
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
     * Draws the ship
     */
    public void draw() {
        // TODO: call draw individually on each part of the ship
        body.draw();
        cannon.draw();
        engine.draw();
        extraPart.draw();
    }

    // TODO: consider making a single hit box for the ship rather that individual hit boxes for the parts
//    public void updateHitBox() {
//        setHeight(getShipHeight());
//        setWidth(getShipWidth());
//        setHitBox();
//    }

    /**
     * Calculate the width of the ship based on its parts
     */
//    public int getShipWidth() {
//        return extraPart.getImageWidth() + body.getImageWidth() + cannon.getImageWidth();
//    }

    /**
     * Calculate the height of the ship based on its parts
     */
//    public int getShipHeight() {
//        return extraPart.getImageHeight() + body.getImageHeight() + cannon.getImageHeight();
//    }

    /**
     * Load the ship images into memory
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
