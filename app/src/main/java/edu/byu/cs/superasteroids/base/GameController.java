package edu.byu.cs.superasteroids.base;

import android.widget.Toast;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.game.GameActivity;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Viewport;

/**
 * Created by Marshall Garey
 * Controller for the game
 */
public class GameController implements IGameDelegate, IController {

    private IView view = null;
    private State_e state;
    private GameActivity gameActivity = null;

    private enum State_e {
        STATE_START_LEVEL,
        STATE_RUN,
        STATE_NEW_LEVEL,
        STATE_END,
        STATE_GAME_OVER
    }

    public GameController(GameActivity gameActivity) {
        state = State_e.STATE_START_LEVEL;
        this.gameActivity = gameActivity;
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
         * A simple state machine.
         */
        switch (state) {

            // Initialize and move on to the regular game engine
            case STATE_START_LEVEL:
                AsteroidsGame.initLevel();
                state = State_e.STATE_RUN;
                break;

            // Main loop of the game. Just update stuff.
            case STATE_RUN:
                // Touch screen coordinates are stored in InputManager.movePoint
                // I need to convert screen coordinates to world coordinates because the positions of all objects are
                // stored as world coordinates.
                // The return value of update tells us whether to transition to a new level, end the game, or just
                // continue normal execution.
                AsteroidsGame.GAME_STATUS status = AsteroidsGame.update(
                        Viewport.screenToWorldCoordinates(InputManager.movePoint),
                        elapsedTime,
                        InputManager.firePressed);
                if (status == AsteroidsGame.GAME_STATUS.GAME_OVER) {
                    state = State_e.STATE_GAME_OVER;
                }
                break;

            // New level, need to reload and do things
            case STATE_NEW_LEVEL:
                break;

            // Last level won, congratulate the player and quit
            case STATE_END:
                break;

            // Lost the game, console the player and quit
            case STATE_GAME_OVER:
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
