package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ImageObject;

/**
 * Created by Marshall Garey
 * The background for the world, or universe, or outer space.
 * Each object has a position in space.
 */
public class Background extends VisibleObject {

    /**
     * The background is an image that needs to be drawn.
     */
    private ImageObject backgroundImage;

    /**
     * Initialize data
     * @param backgroundImage   The path to the image used for the background
     * @param worldWidth        The width of said image
     * @param worldHeight       The height of said image
     */
    public Background(String backgroundImage, int worldWidth, int worldHeight) {
        this.backgroundImage = new ImageObject(backgroundImage, worldWidth, worldHeight);
    }

    /**
     * Draw the portion of the background
     */
    public void draw() {
        backgroundImage.drawPartial(Viewport.getView());
    }

    public void loadContent(ContentManager contentManager) {
        backgroundImage.setImageId(contentManager.loadImage(backgroundImage.getImageFile()));
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public ImageObject getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(ImageObject backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
