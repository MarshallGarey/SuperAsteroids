package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Rect;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * These objects have a position (x and y coordinates) in the universe/world.
 */
public class PositionedObject extends VisibleObject {

    /**
     * The x position of the object.
     */
    private int xPos;

    /**
     * The y position of the object.
     */
    private int yPos;

    /**
     * The bounding rectangle of the object - used for detecting collisions
     */
    private Rect boundingRectangle;

    public PositionedObject(int x, int y) {
        this.xPos = x;
        this.yPos = y;
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

    public Rect getBoundingRectangle() {
        return boundingRectangle;
    }

    public void setBoundingRectangle(Rect boundingRectangle) {
        this.boundingRectangle = boundingRectangle;
    }
}
