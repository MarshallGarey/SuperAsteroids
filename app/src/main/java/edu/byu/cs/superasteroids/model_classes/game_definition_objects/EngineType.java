package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Information about the type of engine for the ship.
 */
public class EngineType extends AttachableShipPart {

    /**
     * The id for the engine type
     */
    private int id;

    /**
     * The base speed of the engine
     */
    private int baseSpeed;

    /**
     * The base turn rate of the engine
     */
    private int baseTurnRate;

    public EngineType(String file, int width, int height, String attachPoint,
                      int id, int baseSpeed, int baseTurnRate) {
        super(file, width, height, attachPoint);
        this.id = id;
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }
}
