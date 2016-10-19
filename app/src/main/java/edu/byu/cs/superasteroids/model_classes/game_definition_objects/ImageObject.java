package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * This class contains information common to all classes that have an image.
 */
public class ImageObject {

    /**
     * file path to the image
     */
    private String imageFile;

    /**
     * width of the image
     */
    private int imageWidth;

    /**
     * height of the image
     */
    private int imageHeight;

    /**
     * Every image that is loaded in Android has an ID
     * We use this ID when it is drawn
     */
    private int imageId;

    // default constructor
    public ImageObject() {}

    public ImageObject(String file, int width, int height) {
        this.imageFile = file;
        this.imageWidth = width;
        this.imageHeight = height;
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public void setImageId(int id) {
        imageId = id;
    }

    public int getImageId() {
        return imageId;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
}
