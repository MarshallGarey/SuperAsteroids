package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * Visible objects are things that the user can see.
 */
public class VisibleObject {

    // Constants:
    private final int OPAQUE = 255;
    private final int TRANSPARENT = 0;
    protected final int IMAGE_NOT_LOADED = -1;

    /**
     * Every image that is loaded in Android has an ID
     * We use this ID when it is drawn
     */
    protected int imageId;

    /**
     * The angle (in radians) that the object points
     */
    protected float direction;

    protected float scale;
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
    public VisibleObject(int width, int height, float scale) {
        this.width = width;
        this.height = height;
        worldPosition = new PointF(0, 0);
        imageId = IMAGE_NOT_LOADED;
        this.scale = scale;
        updateHitBox();
    }

    /**
     * Constructor - initialize position
     *
     * @param worldPosition Initial position - (x,y) coordinates
     */
    public VisibleObject(PointF worldPosition, float scale) {
        this.worldPosition = worldPosition;
        this.hitBox = new RectF();
        this.imageId = IMAGE_NOT_LOADED;
        this.width = 0;
        this.height = 0;
        this.scale = scale;
        updateHitBox();
    }

    /**
     * Default Constructor - do nothing, let the caller initialize everything by hand
     */
    public VisibleObject() {
    }

    /**
     * Default draw method.
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
                    scale, scale, OPAQUE);

            // FOR DEBUGGING: DRAW THE HIT BOX
//            Rect rect = new Rect();
//            rect.left = (int)(pointf.x - scale * width / 2);
//            rect.right = (int)(pointf.x + scale * width / 2);
//            rect.top = (int)(pointf.y - scale * width / 2);
//            rect.bottom = (int)(pointf.y + scale * width / 2);
//            DrawingHelper.drawRectangle(rect, Color.RED, 255);
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
     * Draws an image without checking if it is in the viewport.
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
        DrawingHelper.drawImage(imageId, worldPosition.x, worldPosition.y, 0, scale, scale, OPAQUE);
    }

    /**
     * Updates the position and hit box of the object based on its speed and direction
     * @param speed         How fast the object is moving
     * @param direction     What direction the object is moving
     * @param elapsedTime   How much time elapsed since the last update
     */
    public void update(double speed, float direction, double elapsedTime) {
        // Compute the result
        GraphicsUtils.MoveObjectResult moveObjectResult = GraphicsUtils.moveObject(
                worldPosition,
                hitBox,
                speed,
                direction,
                elapsedTime);
        // Save the result
        hitBox = moveObjectResult.getNewObjBounds();
        worldPosition = moveObjectResult.getNewObjPosition();
    }

    public boolean outOfBounds(int worldWidth, int worldHeight) {
        return (hitBox.left < 0 || hitBox.right > worldWidth || hitBox.top < 0 || hitBox.bottom > worldHeight);
    }

    /**
     * Ricochets an object off the bounds of the world.
     * @param worldWidth    Width of the level.
     * @param worldHeight   Height of the level.
     */
    public void ricochet(int worldWidth, int worldHeight) {
        // Compute the result
        GraphicsUtils.RicochetObjectResult result =  GraphicsUtils.ricochetObject(
                worldPosition, hitBox, direction, worldWidth, worldHeight);
        // Save the result
        direction = (float)result.getNewAngleRadians();
        hitBox = result.getNewObjBounds();
        worldPosition = result.getNewObjPosition();
    }

    /**
     * Set the bounding rectangle, which is used when calculating collisions
     * Left, top, right, and bottom are the coordinates of the rectangle
     * x,y are the center of the image, so divide width and height by 2 and add to x,y to get left, top, right, bottom
     */
    public void updateHitBox() {
        float left = worldPosition.x - scale * (width / 2);
        float right = worldPosition.x + scale * (width / 2);
        float top = worldPosition.y - scale * (height / 2);
        float bottom = worldPosition.y + scale * (height / 2);
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
