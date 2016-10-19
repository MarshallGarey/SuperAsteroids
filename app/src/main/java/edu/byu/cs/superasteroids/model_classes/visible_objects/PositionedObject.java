package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Rect;

/**
 * Created by Marshall Garey
 * These objects have a position (x and y coordinates) in the universe/world.
 */
public class PositionedObject extends VisibleObject {

    /**
     * The x position of the object. This is its center
     */
    private int xPos;

    /**
     * The y position of the object.
     */
    private int yPos;

    private int width;
    private int height;

    /**
     * The bounding rectangle of the object - used for detecting collisions
     */
    private Rect hitBox = null;

    public PositionedObject(int x, int y) {
        super();
        this.xPos = x;
        this.yPos = y;
    }

    public void updatePosition(int speed, float direction) {
        // TODO: update x, y, and bounding rectangle
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

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    /**
     * Set the bounding rectangle, which is used when calculating collisions
     * Left, top, right, and bottom are the coordinates of the rectangle
     * x,y are the center of the image, so divide width and height by 2 and add to x,y to get left, top, right, bottom
     */
    public void setHitBox() {
        int left = xPos - width / 2;
        int right = xPos + width / 2;
        int top = yPos - height / 2;
        int bottom = yPos + height / 2;
        hitBox = new Rect(left, top, right, bottom);
    }
}
