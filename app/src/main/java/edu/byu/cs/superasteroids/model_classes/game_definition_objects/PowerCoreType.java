package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Information about the power core for the ship.
 */
public class PowerCoreType {

    /**
     * id
     */
    private int id;

    private String file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * boost stat for the cannon
     */
    private int cannonBoost;

    /**
     * boost stat for the engine
     */
    private int engineBoost;

    public PowerCoreType(String file, int cannonBoost, int engineBoost) {
        this.file = file;
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    public String getFile() { return file; }

    public void setFile(String file) {
        this.file = file;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }
}
