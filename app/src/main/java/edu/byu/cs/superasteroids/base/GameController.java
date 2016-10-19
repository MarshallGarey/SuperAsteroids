package edu.byu.cs.superasteroids.base;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Marshall Garey
 * Controller for the game
 */
public class GameController implements IGameDelegate, IController {

    public GameController() {

    }

    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return null;
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {

    }

    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     *
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    @Override
    public void update(double elapsedTime) {
        // Ask AsteroidsGame to update - it will call update on each object
    }

    /**
     * Loads content such as image and sounds files and other data into the game. The GameActivty will
     * call this once right before the game engine enters the game loop. The ShipBuilding
     * activity calls this function in its onCreate() function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound
     *                files.
     */
    @Override
    public void loadContent(ContentManager content) {
        AsteroidsGame.getSINGLETON().loadContent(content);
    }

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     *
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    @Override
    public void unloadContent(ContentManager content) {
        AsteroidsGame.getSINGLETON().unloadContent(content);
    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    @Override
    public void draw() {
        // Draw the portion of space that we can see in our viewport
        // viewport.draw

        // Ask AsteroidsGame to draw - it will call draw on each object
        AsteroidsGame.getSINGLETON().draw();
    }
}
