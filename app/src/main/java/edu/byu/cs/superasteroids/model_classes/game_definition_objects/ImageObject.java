package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.visible_objects.VisibleObject;

/**
 * Created by Marshall Garey
 * This class contains information common to all classes that have an image.
 */
public class ImageObject extends VisibleObject {

    /**
     * file path to the image
     */
    private String imageFile;

    /**
     * width/height constructor
     * @param file      image
     * @param width     image width
     * @param height    image height
     */
    public ImageObject(String file, int width, int height, float scale) {
        super(width, height, scale);
        this.imageFile = file;
    }

    /**
     * position constructor
     * @param file  image
     * @param point position
     */
    public ImageObject(String file, PointF point, float scale) {
        super(point, scale);
        this.imageFile = file;
    }

    /**
     * Load the image (this object) into the ContentManager
     * @return the id of the image in the ContentManager upon success, or -1 upon failure to load the image
     */
    public int loadImage(ContentManager contentManager) {
        imageId = contentManager.loadImage(imageFile);
        return imageId;
    }

    /**
     * Unload the image (this object) from the ContentManager
     */
    public void unloadImage(ContentManager contentManager) {
        contentManager.unloadImage(imageId);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

}
