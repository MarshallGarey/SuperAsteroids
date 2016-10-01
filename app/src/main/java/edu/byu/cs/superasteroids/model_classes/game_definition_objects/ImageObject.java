package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey on 2/9/2016.
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

    // default constructor
    public ImageObject() {}

    public ImageObject(String file, int width, int height) {
        this.imageFile = file;
        this.imageWidth = width;
        this.imageHeight = height;
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
