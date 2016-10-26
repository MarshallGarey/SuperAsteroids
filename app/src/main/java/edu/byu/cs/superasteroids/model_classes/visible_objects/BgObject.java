package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ImageObject;

/**
 * Created by Marshall Garey
 * Information about a background object in the game, such as nebulas, etc. These objects are
 * visible but do not move - they are decorations to make the game look cool.
 */
public class BgObject extends ImageObject {

    public BgObject(int x, int y, String file, float scale) {

        // Use the position constructor
        super(file, new PointF(x, y), scale);

        // Update the hitbox (or bounding box) immediately - this only ever needs to be done once, because the hit box
        // is used for detecting when the object is inside the viewport. Do this now so it doesn't need to be done
        // every iteration of the game engine inside update.
        // TODO: I don't think I even have a width or height yet.
        this.updateHitBox();
    }

    @Override
    public void draw() {
        super.draw(scale);
    }
}
