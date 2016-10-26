package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.model_classes.visible_objects.Ship;

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

    protected float partOffsetX = 0;
    protected float partOffsetY = 0;
    protected PointF partOffset = null;

    // Initializes data
    public AttachableShipPart(String file, int width, int height, String attachPoint) {
        super(file, width, height, Ship.SHIP_SCALE);
        this.attachPoint = new CoordinateString(attachPoint);
        partOffset = new PointF(0,0);
    }

    public void initPartOffsets(CoordinateString shipAttach, MainBodyType body) {
        initPartOffsetX(shipAttach, body);
        initPartOffsetY(shipAttach, body);
        partOffset = new PointF(partOffsetX, partOffsetY);
    }

    private void initPartOffsetX(CoordinateString shipAttach, MainBodyType body) {
        partOffsetX = (shipAttach.getxPos() - body.getWidth() / 2) +
                (getCenterX() - attachPoint.getxPos());
    }

    private void initPartOffsetY(CoordinateString shipAttach, MainBodyType body) {
        partOffsetY = (shipAttach.getyPos() - body.getHeight() / 2) +
                (getCenterY() - attachPoint.getyPos());
    }

    public int getCenterX() {
        return getWidth() / 2;
    }

    public int getCenterY() {
        return getHeight() / 2;
    }

    public CoordinateString getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(CoordinateString attachPoint) {
        this.attachPoint = attachPoint;
    }

    public float getPartOffsetX() {
        return partOffsetX;
    }

    public void setPartOffsetX(float partOffsetX) {
        this.partOffsetX = partOffsetX;
    }

    public float getPartOffsetY() {
        return partOffsetY;
    }

    public void setPartOffsetY(float partOffsetY) {
        this.partOffsetY = partOffsetY;
    }

    public PointF getPartOffset() {
        return partOffset;
    }
}
