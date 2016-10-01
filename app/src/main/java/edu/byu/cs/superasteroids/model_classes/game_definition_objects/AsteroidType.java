package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marshall Garey
 * Contains information about the type of the asteroid.
 */
public class AsteroidType extends ImageObject {

    /**
     * the asteroid type - regular, octeroid, or growing
     */
    private String type;

    public AsteroidType(JSONObject obj) throws JSONException {
        super.setImageFile(obj.getString("image"));
        super.setImageWidth(obj.getInt("imageWidth"));
        super.setImageHeight(obj.getInt("imageHeight"));
        type = obj.getString("type");

        // Log.i("JsonDomParserExample", title + ", " + artist);
    }

    public AsteroidType(String file, int width, int height, String type) {
        super(file, width, height);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
