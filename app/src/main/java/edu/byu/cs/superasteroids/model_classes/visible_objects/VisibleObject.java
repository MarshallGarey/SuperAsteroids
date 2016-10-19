package edu.byu.cs.superasteroids.model_classes.visible_objects;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * Visible objects are things that the user can see.
 */
public class VisibleObject {

    private float SCALE = (float) 0.2;
    private int OPAQUE = 255;
    private int TRANSPARENT = 0;

    public VisibleObject() {

    }

    /**
     * Draws the object at the object's x and y positions.
     * Does not draw the object if it is not in the viewport;
     * in other words, if the user cannot see it, there is no need to draw it.
     * TODO: get screen x and y positions by getting the viewport and world
     */
    public void draw(int objectX, int objectY, int imageId) {
        DrawingHelper.drawImage(imageId, objectX, objectY, 0, SCALE, SCALE, OPAQUE);
    }

    /**
     * Draws the object at the x and y position on the screen.
     * Different than draw because it ignores the viewport.
     */
    public void drawAbsolutePosition(int screenX, int screenY, int imageId) {
        DrawingHelper.drawImage(imageId, screenX, screenY, 0, SCALE, SCALE, OPAQUE);
    }
}
