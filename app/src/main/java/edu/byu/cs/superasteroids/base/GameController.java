package edu.byu.cs.superasteroids.base;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Viewport;

/**
 * Created by Marshall Garey
 * Controller for the game
 */
public class GameController implements IGameDelegate, IController {

    private IView view = null;
    private State_e state;

    private enum State_e {
        STATE_START_LEVEL,
        STATE_RUN,
        STATE_NEW_LEVEL,
        STATE_END
    }

    public GameController() {
        state = State_e.STATE_START_LEVEL;
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
     * Updates the game delegate. The game engine will call this function 60 times per second
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
        switch (state) {

            // Initialize and move on to the regular game engine
            case STATE_START_LEVEL:
                AsteroidsGame.initLevel();
                state = State_e.STATE_RUN;
                break;

            // Main loop of the game. Just update stuff.
            // TODO: The return value of update should determine whether to stay in this state, go to the next level,
            // make the ship invincible for a short period of time, or lose the game. I'll have to do something here
            // to handle when the ship gets hit by an asteroids - if it's dead, lose the game; otherwise, make it
            // invincible for a short period of time.
            case STATE_RUN:
                // Touch screen coordinates are stored in InputManager.movePoint
                // I need to convert screen coordinates to world coordinates because the positions of all objects are
                // stored as world coordinates
                AsteroidsGame.update(Viewport.screenToWorldCoordinates(InputManager.movePoint), elapsedTime);
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
        AsteroidsGame.loadContent(content);
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
        AsteroidsGame.unloadContent(content);
    }

    /**
     * Draws the game delegate. This function will be called 60 times per second.
     */
    @Override
    public void draw() {
        // Ask AsteroidsGame to draw - it will call draw on each object
        AsteroidsGame.draw();
    }
}
