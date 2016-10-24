package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Rect;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * The currently visible rectangular portion of the world
 */
public class Viewport {

    private static Rect view = null;

    private Viewport() {
        // Initialize to avoid crashing
        init(0,0);
    }

    /**
     * TODO: move the viewport to stay centered on the ship, as long as it is in the world
     */
    public static void update() {

    }

    /**
     * Initialize the viewport.
     * Set the rectangle to the size of the screen in the center of the world.
     * @param levelWidth    The width of the world
     * @param levelHeight   The height of the world
     */
    public static void init(int levelWidth, int levelHeight) {
        // Place the viewport in the center of the world
        int x = levelWidth / 2;
        int y = levelHeight / 2;

        int screenWidth = DrawingHelper.getGameViewWidth();
        int screenHeight = DrawingHelper.getGameViewHeight();

        // X coordinates start at the left and go right
        int left = x - screenWidth / 2;
        int right = x + screenWidth / 2;

        // Y coordinates start at the top and go down
        int top = y - screenHeight / 2;
        int bottom = y + screenHeight / 2;

        view = new Rect(left, top, right, bottom);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public static Rect getView() {
        return view;
    }

    public static void setView(Rect view) {
        Viewport.view = view;
    }
}
