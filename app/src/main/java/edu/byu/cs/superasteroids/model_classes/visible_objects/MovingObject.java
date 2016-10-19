package edu.byu.cs.superasteroids.model_classes.visible_objects;

/**
 * Created by Marshall Garey
 * This class contains information and methods common to all moving objects. Moving objects are
 * things that move around the screen, such as projectiles, asteroids, and the ship.
 */
public class MovingObject extends PositionedObject {

    /**
     * The speed at which the object is moving.
     */
    private int speed;

    /**
     * The direction in which the object is moving.
     */
    private float direction;

    /**
     * The object's current hit points.
     */
    private int hp;

    public MovingObject(int x, int y, int hp, int speed, float direction) {
        super(x, y);
        this.hp = hp;
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Updates the positions (x and y coordinates) of the image
     * and then draws the image
     */
    public void update(int imageId) {
        // update x and y based on the direction and speed it's going and redraw the object
        updatePosition(speed, direction);
        draw(this.getxPos(), this.getyPos(), imageId);
    }

    /**
     * Detects if the object has collided with any other object.
     */
    public boolean detectCollision() {
        return false;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Getters and setters --------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
