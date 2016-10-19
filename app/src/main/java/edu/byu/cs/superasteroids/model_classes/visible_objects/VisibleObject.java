package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Point;
import android.graphics.Rect;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * Visible objects are things that the user can see.
 */
public class VisibleObject {

    // Constants:

    private final float SCALE = (float) 0.2;
    private final int OPAQUE = 255;
    private final int TRANSPARENT = 0;
    private final int IMAGE_NOT_LOADED = -1;

    /**
     * Every image that is loaded in Android has an ID
     * We use this ID when it is drawn
     */
    protected int imageId;

    protected int width;
    protected int height;
    protected Point worldPosition;
    protected Rect hitBox;

    /**
     * Constructor - initialize width and height
     * @param width     Width of the object
     * @param height    Height of the object
     */
    public VisibleObject(int width, int height) {
        this.width = width;
        this.height = height;
        worldPosition = new Point();
        hitBox = new Rect();
        imageId = IMAGE_NOT_LOADED;
    }

    /**
     * Constructor - initialize position
     * @param worldPosition Initial position - (x,y) coordinates
     */
    public VisibleObject(Point worldPosition) {
        this.worldPosition = new Point(worldPosition);
        this.hitBox = new Rect();
        this.imageId = IMAGE_NOT_LOADED;
        this.width = 0;
        this.height = 0;
    }

    /**
     * Default Constructor - do nothing, let the caller initialize everything by hand
     * TODO: get rid of this?
     */
    public VisibleObject() {}

    /**
     * Draws the object at the appropriate place in the viewport based on its world position.
     * Does not draw the object if it is not in the viewport;
     * in other words, if the user cannot see it, there is no need to draw it.
     * TODO: get screen x and y positions by getting the viewport and world
     */
    public void draw() {
        DrawingHelper.drawImage(imageId, worldPosition.x, worldPosition.y, 0, SCALE, SCALE, OPAQUE);
    }

    /**
     * Updates the position and hit box of the object based on its speed and direction
     * @param speed     How fast the object is moving
     * @param direction What direction the object is moving
     */
    public void updatePosition(int speed, float direction) {
        // TODO: Update position

        updateHitBox();
    }

    /**
     * Draws the object at its x and y position on the screen.
     * Different than draw because it assumes the object's x and y coordinates are relative to the screen, not the
     * viewport.
     */
    public void drawAbsolutePosition() {
        DrawingHelper.drawImage(imageId, worldPosition.x, worldPosition.y, 0, SCALE, SCALE, OPAQUE);
    }

    /**
     * Set the bounding rectangle, which is used when calculating collisions
     * Left, top, right, and bottom are the coordinates of the rectangle
     * x,y are the center of the image, so divide width and height by 2 and add to x,y to get left, top, right, bottom
     */
    public void updateHitBox() {
        int left = worldPosition.x - width / 2;
        int right = worldPosition.x + width / 2;
        int top = worldPosition.y - height / 2;
        int bottom = worldPosition.y + height / 2;
        hitBox = new Rect(left, top, right, bottom);
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(Point worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void setWorldPosition(int x, int y) {
        this.worldPosition.x = x;
        this.worldPosition.y = y;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rect hitBox) {
        this.hitBox = hitBox;
    }
}
