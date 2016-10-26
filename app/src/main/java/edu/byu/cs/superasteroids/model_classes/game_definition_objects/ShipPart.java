package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Contains information common to all ship parts. For now, this isn't anything new, so this
 * class merely inherits information from ImageObject.
 */
public class ShipPart extends ImageObject {

    // Initialize fields common to ImageObject
    public ShipPart(String file, int width, int height, float scale) {
        super(file, width, height, scale);
    }
}
