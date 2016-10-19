package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Point;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ImageObject;

/**
 * Created by Marshall Garey
 * Information about a background object in the game, such as nebulas, etc. These objects are
 * visible but do not move - they are decorations to make the game look cool.
 */
public class BgObject extends VisibleObject {

    /**
     * The file path to the object's image.
     */
    private ImageObject imageObject;

    public BgObject(int x, int y, String file) {

        // Use the position constructor
        super(new Point(x, y));

        // Set the file
        imageObject = new ImageObject(file, 0, 0);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public ImageObject getImageObject() {
        return imageObject;
    }

    public void setImageObject(ImageObject imageObject) {
        this.imageObject = imageObject;
    }
}
