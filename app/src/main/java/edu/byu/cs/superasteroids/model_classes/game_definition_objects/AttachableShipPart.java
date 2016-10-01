package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Contains information common to all ship parts that attach to the main body. This
 * obviously excludes the main body itself.
 */
public class AttachableShipPart extends ShipPart {

    /**
     * the point on the main body to which the part attaches
     */
    private CoordinateString attachPoint;

    // Initializes data
    public AttachableShipPart(String file, int width, int height, String attachPoint) {
        super(file, width, height);
        this.attachPoint = new CoordinateString(attachPoint);
    }

    public CoordinateString getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(CoordinateString attachPoint) {
        this.attachPoint = attachPoint;
    }
}
