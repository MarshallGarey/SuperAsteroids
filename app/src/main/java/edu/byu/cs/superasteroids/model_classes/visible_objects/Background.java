package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.RectF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ImageObject;

/**
 * Created by Marshall Garey
 * The background for the world, or universe, or outer space.
 * Each object has a position in space.
 */
public class Background extends VisibleObject {

    private final double BGIMAGE_WIDTH = 2048.0;
    private final double BGIMAGE_HEIGHT = 2048.0;
    private double widthScale;
    private double heightScale;

    /**
     * The background is an image that needs to be drawn.
     */
    private ImageObject backgroundImage;

    /**
     * Initialize data.
     * The size of the background image needs to be scaled by the size of the world.
     *
     * @param backgroundImage The path to the image used for the background
     * @param worldWidth      The width of the world. Used for scaling.
     * @param worldHeight     The height of the world. Used for scaling.
     */
    public Background(String backgroundImage, int worldWidth, int worldHeight) {
        double ww = (double) worldWidth;
        double wh = (double) worldHeight;
        widthScale = BGIMAGE_WIDTH / ww;
        heightScale = BGIMAGE_HEIGHT / wh;
        this.backgroundImage = new ImageObject(backgroundImage, (int)BGIMAGE_WIDTH, (int)BGIMAGE_HEIGHT, 1);
    }

    /**
     * Draw the portion of the background that intersects with the position of the screen in the world.
     */
    public void draw() {
        RectF rect = new RectF(Viewport.getView());
        rect.left *= widthScale;
        rect.right *= widthScale;
        rect.top *= heightScale;
        rect.bottom *= heightScale;
        backgroundImage.drawPartial(rect);
    }

    public void loadContent(ContentManager contentManager) {
        backgroundImage.loadImage(contentManager);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public ImageObject getBackgroundImage() {
        return backgroundImage;
    }
}
