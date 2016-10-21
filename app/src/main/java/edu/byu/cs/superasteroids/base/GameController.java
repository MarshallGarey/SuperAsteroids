package edu.byu.cs.superasteroids.base;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * Created by Marshall Garey
 * Controller for the game
 */
public class GameController implements IGameDelegate, IController {

    private IView view = null;
    private State_e state;

    private enum State_e {
        STATE_START,
        STATE_RUN,
        STATE_NEW_LEVEL,
        STATE_END
    }

    public GameController() {
        state = State_e.STATE_START;
    }

    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return view;
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {
        this.view = view;
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

        /**
         * I use a state machine.
         */
        switch(state) {

            // Initialize and move on to the regular game engine
            case STATE_START:
                AsteroidsGame.getSINGLETON().initLevel();
                state = State_e.STATE_RUN;
                break;

            // Main loop of the game. Just update stuff.
            case STATE_RUN:
                AsteroidsGame.getSINGLETON().update();
                break;

            // New level, need to reload and do things
            case STATE_NEW_LEVEL:
                break;

            // Last level won, congratulate the player and quit
            case STATE_END:
                break;
        }
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
