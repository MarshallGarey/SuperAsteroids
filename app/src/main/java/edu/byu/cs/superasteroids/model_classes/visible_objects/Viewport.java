package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * The currently visible rectangular portion of the world
 */
public class Viewport {

    private static RectF view = null;
    private static PointF currentViewportPosition = null;

    private Viewport() {
    }

    /**
     * TODO: move the viewport to stay centered on the ship, as long as the entire viewport stays within the world
     */
    public static void update() {
        PointF shipPosition = AsteroidsGame.getShip().worldPosition;
        float screenWidth = DrawingHelper.getGameViewWidth();
        float screenHeight = DrawingHelper.getGameViewHeight();

        // Only move the viewport in the x direction if it will stay in bounds.
        if (isInBounds(shipPosition.x, screenWidth, AsteroidsGame.getCurrentLevel().getLevelWidth())) {
            currentViewportPosition.x = shipPosition.x;
        }

        // Same thing with the Y direction
        if (isInBounds(shipPosition.y, screenHeight, AsteroidsGame.getCurrentLevel().getLevelHeight())) {
            currentViewportPosition.y = shipPosition.y;
        }

        moveViewport();
    }

    private static boolean isInBounds(float position, float dimension, float maxValue) {
        return (((position - dimension / 2) > 0) &&
                position + dimension / 2 < maxValue);
    }

    /**
     * Initialize the viewport.
     * Set the rectangle to the size of the screen in the center of the world.
     * @param levelWidth    The width of the world
     * @param levelHeight   The height of the world
     */
    public static void init(int levelWidth, int levelHeight) {
        // Place the viewport in the center of the world
        float centerX = levelWidth / 2;
        float centerY = levelHeight / 2;
        currentViewportPosition  = new PointF(centerX, centerY);
        moveViewport();
    }

    private static void moveViewport() {
        float screenWidth = DrawingHelper.getGameViewWidth();
        float screenHeight = DrawingHelper.getGameViewHeight();

        // X coordinates start at the left and go right
        float left = currentViewportPosition.x - screenWidth / 2;
        float right = currentViewportPosition.x + screenWidth / 2;

        // Y coordinates start at the top and go down
        float top = currentViewportPosition.y - screenHeight / 2;
        float bottom = currentViewportPosition.y + screenHeight / 2;

        view = new RectF(left, top, right, bottom);
    }

    /**
     * object world position = object screen postion + viewport world position
     * @param screenCoordinates The screen coordinates to convert to world coordinates
     * @return The world coordinates of the passed point, or null if the passed point is null
     */
    public static PointF screenToWorldCoordinates(PointF screenCoordinates) {
        if (screenCoordinates == null) {
            return null;
        }
        return new PointF(screenCoordinates.x + view.left,      // X
                screenCoordinates.y + view.top);                // Y
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public static RectF getView() {
        return view;
    }

}
