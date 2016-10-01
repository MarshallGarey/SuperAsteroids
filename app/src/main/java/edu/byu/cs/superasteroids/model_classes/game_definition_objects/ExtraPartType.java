package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Information about the extra part for the ship. There is nothing new about the extra part
 * that is not in its super class AttachableShipPart.
 */
public class ExtraPartType extends AttachableShipPart {

    public ExtraPartType(String file, int width, int height, String attachPoint) {
        super(file, width, height, attachPoint);
    }

    /**
     * id of the extra part
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
