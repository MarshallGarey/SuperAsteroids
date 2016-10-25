package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * The currently visible rectangular portion of the world
 */
public class Viewport {

    private static RectF view = null;

    private Viewport() {
        // Initialize to avoid crashing
        init(0,0);
    }

    /**
     * TODO: move the viewport to stay centered on the ship, as long as the entire viewport stays within the world
     */
    public static void update() {
//        currentViewportPosition = new PointF(AsteroidsGame.getShip().worldPosition);
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

        float screenWidth = DrawingHelper.getGameViewWidth();
        float screenHeight = DrawingHelper.getGameViewHeight();

        // X coordinates start at the left and go right
        float left = centerX - screenWidth / 2;
        float right = centerX + screenWidth / 2;

        // Y coordinates start at the top and go down
        float top = centerY - screenHeight / 2;
        float bottom = centerY + screenHeight / 2;

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
