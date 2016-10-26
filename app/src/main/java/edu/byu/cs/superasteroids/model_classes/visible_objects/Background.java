package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ImageObject;

/**
 * Created by Marshall Garey
 * The background for the world, or universe, or outer space.
 * Each object has a position in space.
 */
public class Background extends VisibleObject {

    private final int BGIMAGE_WIDTH = 2048;
    private final int BGIMAGE_HEIGHT = 2048;
    private float widthScale;
    private float heightScale;

    /**
     * The background is an image that needs to be drawn.
     */
    private ImageObject backgroundImage;

    /**
     * Initialize data.
     * TODO:??? The size of the background image needs to be scaled by the size of the world:
     * scaledSize = worldSize / starFieldSize
     *
     * @param backgroundImage The path to the image used for the background
     * @param worldWidth      The width of the world
     * @param worldHeight     The height of the world
     */
    public Background(String backgroundImage, int worldWidth, int worldHeight) {
        widthScale = worldWidth / BGIMAGE_WIDTH;
        heightScale = worldHeight / BGIMAGE_HEIGHT;
        this.backgroundImage = new ImageObject(backgroundImage, BGIMAGE_WIDTH, BGIMAGE_HEIGHT, 1);
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
