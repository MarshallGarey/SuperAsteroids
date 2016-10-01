package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ImageObject;

/**
 * Created by Marshall Garey
 * Information about a background object in the game, such as nebulas, etc. These objects are
 * visible but do not move - they are decorations to make the game look cool.
 */
public class BgObject extends PositionedObject {

    /**
     * The file path to the object's image.
     */
    private String objectFile;

    public BgObject(int x, int y, String file) {
        super(x, y);
        this.objectFile = file;
    }
}
