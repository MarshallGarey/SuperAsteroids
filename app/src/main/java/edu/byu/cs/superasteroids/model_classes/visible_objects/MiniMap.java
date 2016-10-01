package edu.byu.cs.superasteroids.model_classes.visible_objects;

/**
 * Created by Marshall Garey on 2/9/2016.
 * The mini-map is always in the same place on the user's viewport. It has a red dot for every
 * asteroid and a green dot for the ship.
 */
public class MiniMap extends PositionedObject {

    /**
     * Draws a red dot in the mini-map for every asteroid and a green dot for the ship.
     */
    void drawDots() {
        return;
    }

    public MiniMap(int x, int y) {
        super(x, y);
    }
}
