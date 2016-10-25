package edu.byu.cs.superasteroids.ship_builder;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.ETC1;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.crypto.spec.DHGenParameterSpec;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CannonType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.CoordinateString;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.EngineType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ExtraPartType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.MainBodyType;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.PowerCoreType;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Ship;

/**
 * Controller for the ship builder activity
 */
public class ShipBuildingController implements IShipBuildingController {

    private ShipBuildingActivity shipBuildingActivity;
    private ContentManager content;
    private IShipBuildingView.PartSelectionView partSelectionView;
    ArrayList<Integer> mainBodyIds;
    ArrayList<Integer> powerCoreIds;
    ArrayList<Integer> engineIds;
    ArrayList<Integer> extraPartIds;
    ArrayList<Integer> cannonIds;
    private final float SCALE = (float) 0.2;

    public ShipBuildingController(ShipBuildingActivity shipBuildingActivity) {
        this.shipBuildingActivity = shipBuildingActivity;
    }

    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view: Set the arrows for the view in
     * this function, display the part images, enable start game button if all ship parts
     *
     * @param partView - which body view
     */
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        partSelectionView = partView;
        switch (partView) {
            case MAIN_BODY: // main view
                shipBuildingActivity.setPartViewImageList(partSelectionView, mainBodyIds);
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, "Extra Part");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, "Cannon");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.UP, true, "Power Core");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, true, "Engine");
                break;
            case CANNON: // right view
                shipBuildingActivity.setPartViewImageList(partSelectionView, cannonIds);
                // only the left arrow is visible
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, "Main Body");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            case POWER_CORE: // top view
                shipBuildingActivity.setPartViewImageList(partSelectionView, powerCoreIds);
                // only the down arrow is visible
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, true, "Main Body");
                break;
            case ENGINE: // bottom view
                shipBuildingActivity.setPartViewImageList(partSelectionView, engineIds);
                // only the up arrow is visible
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.UP, true, "Main Body");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            case EXTRA_PART: // left view
                shipBuildingActivity.setPartViewImageList(partSelectionView, extraPartIds);
                // only the right arrow is visible
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, "Main Body");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, "");
                shipBuildingActivity.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            default:
                break;
        }
        setStartButton();
    }

    /**
     * Enable the start button when all ship parts have been selected
     */
    private void setStartButton() {
        if (AsteroidsGame.hasAllParts()) {
            shipBuildingActivity.setStartGameButton(true);
        } else {
            shipBuildingActivity.setStartGameButton(false);
        }
    }

    /**
     * Does nothing
     *
     * @param elapsedTime Time since the last update.
     */
    @Override
    public void update(double elapsedTime) {

    }

    /**
     * The ShipBuildingView calls this function as it is created. Load ship building content in this function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
    @Override
    public void loadContent(ContentManager content) {
        this.content = content;
        mainBodyIds = loadMainBodies();
        cannonIds = loadCannons();
        engineIds = loadEngines();
        extraPartIds = loadExtraParts();
        powerCoreIds = loadPowerCores();
    }

    /**
     * loads power cores into content
     */
    private ArrayList<Integer> loadPowerCores() {
        ArrayList<PowerCoreType> powerCores = AsteroidsGame.getPowerCoreTypes();
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < powerCores.size(); i++) {
            String file = powerCores.get(i).getFile();
            powerCores.get(i).setId(content.loadImage(file));
            ids.add(i, powerCores.get(i).getId());
        }
        return ids;
    }

    /**
     * loads engines into content
     */
    private ArrayList<Integer> loadEngines() {
        ArrayList<EngineType> engines = AsteroidsGame.getEngines();
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < engines.size(); i++) {
            String file = engines.get(i).getImageFile();
            ids.add(i, content.loadImage(file));
            engines.get(i).setImageId(ids.get(i));
        }
        return ids;
    }

    /**
     * loads main bodies into content
     */
    private ArrayList<Integer> loadMainBodies() {
        ArrayList<MainBodyType> mainBodies = AsteroidsGame.getMainBodies();
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < mainBodies.size(); i++) {
            String file = mainBodies.get(i).getImageFile();
            ids.add(i, content.loadImage(file));
            mainBodies.get(i).setImageId(ids.get(i));
        }
        return ids;
    }

    /**
     * loads cannons into content
     */
    private ArrayList<Integer> loadCannons() {
        ArrayList<CannonType> cannonTypes = AsteroidsGame.getCannons();
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < cannonTypes.size(); i++) {
            String file = cannonTypes.get(i).getImageFile();
            ids.add(i, content.loadImage(file));
            cannonTypes.get(i).setImageId(ids.get(i));
        }
        return ids;
    }

    /**
     * loads extra parts into content
     */
    private ArrayList<Integer> loadExtraParts() {
        ArrayList<ExtraPartType> extraParts = AsteroidsGame.getExtraPartTypes();
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < extraParts.size(); i++) {
            String file = extraParts.get(i).getImageFile();
            ids.add(i, content.loadImage(file));
            extraParts.get(i).setImageId(ids.get(i));
        }
        return ids;
    }

    /**
     * Unloads content out of memory
     *
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    @Override
    public void unloadContent(ContentManager content) {
        unloadMainBodies(content);
        unloadCannons(content);
        unloadEngines(content);
        unloadExtraParts(content);
        unloadPowerCores(content);
    }

    private void unloadMainBodies(ContentManager content) {
        for (int i = 0; i < mainBodyIds.size(); i++) {
            content.unloadImage(mainBodyIds.get(i));
        }
        mainBodyIds = null;
    }

    private void unloadCannons(ContentManager content) {
        for (int i = 0; i < cannonIds.size(); i++) {
            content.unloadImage(cannonIds.get(i));
        }
        cannonIds = null;
    }

    private void unloadEngines(ContentManager content) {
        for (int i = 0; i < engineIds.size(); i++) {
            content.unloadImage(engineIds.get(i));
        }
        engineIds = null;
    }

    private void unloadExtraParts(ContentManager content) {
        for (int i = 0; i < extraPartIds.size(); i++) {
            content.unloadImage(extraPartIds.get(i));
        }
        extraPartIds = null;
    }

    private void unloadPowerCores(ContentManager content) {
        for (int i = 0; i < powerCoreIds.size(); i++) {
            content.unloadImage(powerCoreIds.get(i));
        }
        powerCoreIds = null;
    }

    /**
     * Draws the content of the activity on the screen - draws the ship
     */
    @Override
    public void draw() {
        // If the main body doesn't exist (drawMainBody returns false), make sure not to try to draw the other parts
        if (!drawMainBody())
            return;
        drawExtraPart();
        drawCannon();
        drawEngine();
    }

    /**
     * @return false if the main body hasn't been selected yet, true if successfully drawn
     */
    private boolean drawMainBody() {
        Ship ship = AsteroidsGame.getShip();
        if (ship.getBody() == null)
            return false; // don't draw if a body hasn't been selected yet
        ship.setWorldPosition(new PointF(
                DrawingHelper.getGameViewWidth() / 2,
                DrawingHelper.getGameViewHeight() / 2
        ));

        int id = ship.getBody().getImageId();
        float rotation = 0;
        int alpha = 255;

        DrawingHelper.drawImage(id, AsteroidsGame.getShip().getWorldPosition().x,
                AsteroidsGame.getShip().getWorldPosition().y, rotation, SCALE, SCALE, alpha);
        return true;
    }

    private void drawCannon() {
        Ship ship = AsteroidsGame.getShip();
        if (ship.getCannon() == null)
            return; // don't draw if a cannon hasn't been selected yet
        CannonType cannon = ship.getCannon();
        int id = cannon.getImageId();
        PointF point = computeAttach(
                cannon.getAttachPoint(), // part attach point
                cannon.getWidth() / 2, // part center x point
                cannon.getHeight() / 2, // part center Y point
                ship.getBody().getCannonAttach(), // ship attach point
                ship // pointer to ship
        );
        float rotation = 0;
        int alpha = 255;
        DrawingHelper.drawImage(id, point.x, point.y, rotation, SCALE, SCALE, alpha);
    }

    private void drawEngine() {
        Ship ship = AsteroidsGame.getShip();
        if (ship.getEngine() == null)
            return; // don't draw if an engine hasn't been selected yet
        EngineType engine = ship.getEngine();
        int id = engine.getImageId();
        PointF point = computeAttach(
                engine.getAttachPoint(), // part attach point
                engine.getWidth() / 2, // part center x point
                engine.getHeight() / 2, // part center Y point
                ship.getBody().getEngineAttach(), // ship attach point
                ship // pointer to ship
        );
        float rotation = 0;
        int alpha = 255;
        DrawingHelper.drawImage(id, point.x, point.y, rotation, SCALE, SCALE, alpha);
    }

    private void drawExtraPart() {
        Ship ship = AsteroidsGame.getShip();
        if (ship.getExtraPart() == null)
            return; // don't draw if an extra part hasn't been selected yet
        ExtraPartType extraPart = ship.getExtraPart();
        int id = extraPart.getImageId();
        PointF point = computeAttach(
                extraPart.getAttachPoint(), // part attach point
                extraPart.getWidth() / 2, // part center x point
                extraPart.getHeight() / 2, // part center Y point
                ship.getBody().getExtraAttach(), // ship attach point
                ship // pointer to ship object
        );
        float rotation = 0;
        int alpha = 255;
        DrawingHelper.drawImage(id, point.x, point.y, rotation, SCALE, SCALE, alpha);
    }

    /**
     * Computes the attach point for a ship part
     *
     * @param partAttach  - part attach point
     * @param partCenterX - part center x point
     * @param partCenterY - part center Y point
     * @param shipAttach  - ship attach point
     * @param ship        - pointer to ship
     * @return point      - the location the part will be drawn
     */
    private PointF computeAttach(CoordinateString partAttach, int partCenterX, int partCenterY,
                                CoordinateString shipAttach, Ship ship) {
        // Find the X coordinate
        int partOffsetX = (shipAttach.getxPos() - ship.getBody().getWidth() / 2) +
                (partCenterX - partAttach.getxPos());
        float partLocationX = ship.getWorldPosition().x + ((SCALE) * (float) partOffsetX);

        // Find the Y coordinate
        int partOffsetY = (shipAttach.getyPos() - ship.getBody().getHeight() / 2) +
                (partCenterY - partAttach.getyPos());
        float partLocationY = ship.getWorldPosition().y + ((SCALE) * (float) partOffsetY);

        // Return the point
        return new PointF(partLocationX, partLocationY);
    }

    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     *
     * @param direction The direction of the swipe/fling.
     */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        // Views:
        // Main view: body
        // Top view: Power Core
        // Right view: Cannon
        // Left View: Extra Part
        // Bottom View: Engine
        switch (partSelectionView) {
            case MAIN_BODY: // main view
                switch (direction) {
                    case UP: // go to bottom view - engine
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.ENGINE,
                                IShipBuildingView.ViewDirection.DOWN);
                        break;
                    case RIGHT: // go to left view - extra part
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.EXTRA_PART,
                                IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT: // go to right view - cannon
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.CANNON,
                                IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    case DOWN: // go to top view - power core
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.POWER_CORE,
                                IShipBuildingView.ViewDirection.UP);
                        break;
                    default:
                        break;
                }
                break;
            case ENGINE: // bottom view
                switch (direction) {
                    case DOWN: // return to main body view
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.UP);
                        break;
                    case RIGHT:
                    case LEFT:
                    case UP:
                    default:
                        break;
                }
                break;
            case EXTRA_PART: // left view
                switch (direction) {
                    case LEFT: // return to main body view
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.RIGHT);
                        break;
                    case RIGHT:
                    case UP:
                    case DOWN:
                    default:
                        break;
                }
                break;
            case CANNON: // right view
                switch (direction) {
                    case RIGHT: // return to main body view
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                    case UP:
                    case DOWN:
                    default:
                        break;
                }
                break;
            case POWER_CORE: // top view
                switch (direction) {
                    case UP: // return to main body view
                        shipBuildingActivity.animateToView(
                                IShipBuildingView.PartSelectionView.MAIN_BODY,
                                IShipBuildingView.ViewDirection.DOWN);
                        break;
                    case RIGHT:
                    case LEFT:
                    case DOWN:
                    default:
                        break;
                }
                break;
        }
    }

    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     *
     * @param index The list index of the selected part.
     */
    @Override
    public void onPartSelected(int index) {
        // The player must select a main body before any of the other parts can be attached, so check for a main body
        // when they are selecting any other part
        if (partSelectionView != IShipBuildingView.PartSelectionView.MAIN_BODY) {
            if (AsteroidsGame.getShip().getBody() == null) {
                Context context = shipBuildingActivity.getApplicationContext();
                CharSequence text = "You must select a main body first!";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
                return;
            }
        }
        switch (partSelectionView) {
            case MAIN_BODY:
                AsteroidsGame.getShip().setBody(
                        AsteroidsGame.getMainBodies().get(index));
                break;
            case CANNON:
                AsteroidsGame.getShip().setCannon(
                        AsteroidsGame.getCannons().get(index));
                break;
            case ENGINE:
                AsteroidsGame.getShip().setEngine(
                        AsteroidsGame.getEngines().get(index));
                break;
            case EXTRA_PART:
                AsteroidsGame.getShip().setExtraPart(
                        AsteroidsGame.getExtraPartTypes().get(index));
                break;
            case POWER_CORE:
                AsteroidsGame.getShip().setPowerCore(
                        AsteroidsGame.getPowerCoreTypes().get(index));
                break;
            default:
                break;
        }
        // Enable the start game button if all parts have been selected
        setStartButton();
    }

    /**
     * The ShipBuildingView calls this function when the start game button is pressed.
     */
    @Override
    public void onStartGamePressed() {
        shipBuildingActivity.startGame();
    }

    /**
     * The ShipBuildingView calls this function when ship building has resumed. Does nothing
     */
    @Override
    public void onResume() {
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
