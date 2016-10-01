package edu.byu.cs.superasteroids.main_menu;

import android.content.Context;
import android.widget.Toast;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.base.IView;

/**
 * Created by Marshall Garey on 3/4/2016.
 * Controller for the MainMenuActivity
 */
public class MainMenuController implements IMainMenuController {

    private MainActivity mainActivity;

    public MainMenuController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * The MainActivity calls this function when the "quick play" button is pressed.
     */
    @Override
    public void onQuickPlayPressed() {
        AsteroidsGame.setDefaultShip();
        mainActivity.startGame();
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
