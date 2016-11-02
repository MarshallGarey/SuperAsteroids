package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.HashSet;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * The mini-map is always in the same place on the user's viewport. It has a red dot for every
 * asteroid and a green dot for the ship.
 */
public class MiniMap extends VisibleObject {

    public static final double MINIMAP_WIDTH = 150.0;
    public static final double MINIMAP_HEIGHT = 150.0;
    private double widthScale;
    private double heightScale;

    /**
     * Draws a red dot in the mini-map for every asteroid and a green dot for the ship.
     */
    public void draw(Ship ship, HashSet<Asteroid> regularAsteroids, HashSet<GrowingAsteroid> growingAsteroids,
                         HashSet<Octeroid> octeroids) {

        // Draw a green dot for the ship
        drawDot(ship.worldPosition, Color.GREEN);

        // Draw red dots for the asteroids:

        for (Asteroid asteroid : regularAsteroids) {
            drawDot(asteroid.worldPosition, Color.RED);
        }

        for (GrowingAsteroid asteroid : growingAsteroids) {
            drawDot(asteroid.worldPosition, Color.RED);
        }

        for (Octeroid asteroid : octeroids) {
            drawDot(asteroid.worldPosition, Color.RED);
        }
    }

    private void drawDot(PointF worldLocation, int color) {
        // Translate world location to minimap location.
        PointF mmLocation = new PointF();
        mmLocation.x = (float)(worldLocation.x * widthScale);
        mmLocation.y = (float)(worldLocation.y * heightScale);
        DrawingHelper.drawFilledCircle(mmLocation, 5, color, OPAQUE);
    }

    public MiniMap(int levelWidth, int levelHeight) {
        super((int)MINIMAP_WIDTH, (int)MINIMAP_HEIGHT, 1);
        double lw = (double)levelWidth;
        double lh = (double)levelHeight;
        this.widthScale = MINIMAP_WIDTH / lw;
        this.heightScale = MINIMAP_HEIGHT / lh;
    }
}
