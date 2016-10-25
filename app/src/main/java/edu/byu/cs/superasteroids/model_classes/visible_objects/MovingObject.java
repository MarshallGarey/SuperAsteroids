package edu.byu.cs.superasteroids.model_classes.visible_objects;

/**
 * Created by Marshall Garey
 * This class contains information and methods common to all moving objects. Moving objects are
 * things that move around the screen, such as projectiles, asteroids, and the ship.
 */
public class MovingObject extends VisibleObject {

    /**
     * The speed at which the object is moving.
     */
    protected double speed;

    /**
     * The object's current hit points.
     */
    protected int hp;

    public MovingObject(int x, int y, int hp, int speed, float direction) {
        super(x, y);
        this.hp = hp;
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * TODO: Detects if the object has collided with any other object.
     */
    public boolean detectCollision() {
        return false;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Getters and setters --------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = (float)direction;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
