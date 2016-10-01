package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Contains information about the type of a main body for the ship.
 */
public class MainBodyType extends ShipPart {

    /**
     * ID of the main body. We need this because can be multiple main bodies
     * the user can choose.
     */
    private int id;

    /**
     * Attach point of the cannon.
     */
    private CoordinateString cannonAttach;

    /**
     * Attach point of the engine.
     */
    private CoordinateString engineAttach;

    /**
     * Attach point of the extra part.
     */
    private CoordinateString extraAttach;

    public MainBodyType(String file, int width, int height, int id, String cannonAttachPoint,
                        String engineAttachPoint, String extraPartAttachPoint) {
        super(file, width, height);
        this.id = id;
        this.cannonAttach = new CoordinateString(cannonAttachPoint);
        this.engineAttach = new CoordinateString(engineAttachPoint);
        this.extraAttach = new CoordinateString(extraPartAttachPoint);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CoordinateString getCannonAttach() {
        return cannonAttach;
    }

    public void setCannonAttach(CoordinateString cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public CoordinateString getEngineAttach() {
        return engineAttach;
    }

    public void setEngineAttach(CoordinateString engineAttach) {
        this.engineAttach = engineAttach;
    }

    public CoordinateString getExtraAttach() {
        return extraAttach;
    }

    public void setExtraAttach(CoordinateString extraAttach) {
        this.extraAttach = extraAttach;
    }
}
