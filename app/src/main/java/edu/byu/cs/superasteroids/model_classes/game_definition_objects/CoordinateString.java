package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Contains an x and y coordinate separated by a comma, i.e. "123,456" x = 123 and y = 456
 */
public class CoordinateString {

    /**
     * The string version of the position as listed in the JSON file.
     */
    private String position;

    /**
     * The x position of the object.
     */
    private int xPos;

    /**
     * The y position of the object.
     */
    private int yPos;

    public CoordinateString(String pos) {
        this.position = pos;
        setXY(pos);
    }

    public void setXY(String pos) {
        // set x position
        StringBuilder sb = new StringBuilder();
        int i = 0;
        char c;
        do {
            c = pos.charAt(i);
            i++;
            sb.append(c);
        } while (pos.charAt(i) != ',');
        xPos = Integer.parseInt(sb.toString());
        // set y position
        sb = new StringBuilder();
        i++; // get i past
        do {
            sb.append(pos.charAt(i));
            i++;
        } while (i < pos.length());
        yPos = Integer.parseInt(sb.toString());
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
