package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

/**
 * Created by Marshall Garey
 * The mini-map is always in the same place on the user's viewport. It has a red dot for every
 * asteroid and a green dot for the ship.
 */
public class MiniMap extends VisibleObject {

    /**
     * Draws a red dot in the mini-map for every asteroid and a green dot for the ship.
     */
    void drawDots() {

    }

    public MiniMap(int x, int y) {
        super(new PointF(x, y), 1);
    }
}
