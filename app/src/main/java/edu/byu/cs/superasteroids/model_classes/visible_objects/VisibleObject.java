package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * Visible objects are things that the user can see.
 */
public class VisibleObject {

    // Constants:

    protected final float SCALE = (float) 0.2;
    private final int OPAQUE = 255;
    private final int TRANSPARENT = 0;
    private final int IMAGE_NOT_LOADED = -1;

    /**
     * Every image that is loaded in Android has an ID
     * We use this ID when it is drawn
     */
    protected int imageId;

    /**
     * The angle (in radians) that the object points
     */
    protected float direction;

    protected int width;
    protected int height;
    protected PointF worldPosition;
    protected RectF hitBox;

    /**
     * Constructor - initialize width and height
     *
     * @param width  Width of the object
     * @param height Height of the object
     */
    public VisibleObject(int width, int height) {
        this.width = width;
        this.height = height;
        worldPosition = new PointF(0, 0);
        updateHitBox();
        imageId = IMAGE_NOT_LOADED;
    }

    /**
     * Constructor - initialize position
     *
     * @param worldPosition Initial position - (x,y) coordinates
     */
    public VisibleObject(Point worldPosition) {
        this.worldPosition = new PointF(worldPosition);
        this.hitBox = new RectF();
        this.imageId = IMAGE_NOT_LOADED;
        this.width = 0;
        this.height = 0;
        updateHitBox();
    }

    /**
     * Default Constructor - do nothing, let the caller initialize everything by hand
     * TODO: get rid of this?
     */
    public VisibleObject() {
    }

    /**
     * Draws the object at the appropriate place in the viewport based on its world position.
     * Does not draw the object if it is not in the viewport;
     * in other words, if the user cannot see it, there is no need to draw it.
     */
    public void draw() {
        // Test if the viewport intersects with the object, then translate the world coordinates to screen
        // coordinates, then draw.
        if (RectF.intersects(Viewport.getView(), this.getHitBox())) {
            PointF pointf = translateToScreenCoordinates();
            DrawingHelper.drawImage(
                    imageId,
                    pointf.x,
                    pointf.y,
                    (float)Math.toDegrees((double)direction),
                    SCALE, SCALE, OPAQUE);
        }
    }

    /**
     * Draws the portion of the image that intersects with the passed rectangle.
     *
     * @param imagePortion The rectangle (in image coordinates) that represents the portion of the image to be drawn.
     */
    public void drawPartial(RectF imagePortion) {
        DrawingHelper.drawImage(imageId,
                new Rect((int) imagePortion.left, (int) imagePortion.top,
                        (int) imagePortion.right, (int) imagePortion.bottom),
                null);
    }

    /**
     * Draws an image
     * @param scale How much to scale the image by.
     */
    public void draw(float scale) {
        PointF pointF = translateToScreenCoordinates();
        DrawingHelper.drawImage(imageId, pointF.x, pointF.y, 0, scale, scale, OPAQUE);
    }

    /**
     * Draws the object, assuming that its (x,y) world coordinates are the same as screen coordinates.
     */
    public void drawAbsolutePosition() {
        DrawingHelper.drawImage(imageId, worldPosition.x, worldPosition.y, 0, SCALE, SCALE, OPAQUE);
    }

    /**
     * Updates the position and hit box of the object based on its speed and direction
     * @param speed         How fast the object is moving
     * @param direction     What direction the object is moving
     * @param elapsedTime   How much time elapsed since the last update
     */
    public void update(double speed, float direction, double elapsedTime) {
        this.direction = direction;
        GraphicsUtils.MoveObjectResult moveObjectResult = GraphicsUtils.moveObject(
                worldPosition,
                hitBox,
                speed,
                direction - Math.PI / 2,    // Because the 0 or x axis is up, the direction is 90 degrees off what it
                                            // should be, so correct the offset when moving the ship
                elapsedTime);
        hitBox = moveObjectResult.getNewObjBounds();
        worldPosition = moveObjectResult.getNewObjPosition();
    }

    /**
     * Set the bounding rectangle, which is used when calculating collisions
     * Left, top, right, and bottom are the coordinates of the rectangle
     * x,y are the center of the image, so divide width and height by 2 and add to x,y to get left, top, right, bottom
     */
    public void updateHitBox() {
        float left = worldPosition.x - width / 2;
        float right = worldPosition.x + width / 2;
        float top = worldPosition.y - height / 2;
        float bottom = worldPosition.y + height / 2;
        hitBox = new RectF(left, top, right, bottom);
    }

    /**
     * Translate the objects world (x,y) coordinates to screen coordinates
     * @return The screen coordinates
     */
    private PointF translateToScreenCoordinates() {
        float screenX = worldPosition.x - Viewport.getView().left;
        float screenY = worldPosition.y - Viewport.getView().top;
        return new PointF(screenX, screenY);
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

    public PointF getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(PointF worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void setWorldPosition(float x, float y) {
        this.worldPosition.x = x;
        this.worldPosition.y = y;
    }

    public RectF getHitBox() {
        return hitBox;
    }

    public void setHitBox(RectF hitBox) {
        this.hitBox = hitBox;
    }
}
