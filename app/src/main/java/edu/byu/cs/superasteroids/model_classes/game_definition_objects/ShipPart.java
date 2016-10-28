package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Contains information common to all ship parts. For now, this isn't anything new, so this
 * class merely inherits information from ImageObject.
 */
public class ShipPart extends ImageObject {

    protected final float DIRECTION_OFFSET = (float)-Math.PI / 2;

    // Initialize fields common to ImageObject
    public ShipPart(String file, int width, int height, float scale) {
        super(file, width, height, scale);
    }

    @Override
    public void update(double speed, float direction, double elapsedTime) {
        this.direction = direction;
        // Because the 0 or x axis is up, the direction is 90 degrees off what it
        // should be, so correct the offset when moving the ship
        super.update(speed, direction + DIRECTION_OFFSET, elapsedTime);
    }
}
