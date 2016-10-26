package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

/**
 * Created by Marshall Garey
 * This class contains information and methods common to all moving objects. Moving objects are
 * things that move around the screen, such as projectiles, asteroids, and the ship.
 */
public class MovingObject extends VisibleObject {

    private final int STARTING_HP = 5;

    /**
     * The speed at which the object is moving.
     */
    protected double speed;

    /**
     * The object's current hit points.
     */
    protected int hp;

    /**
     * Initialize the object at the position (x,y) with the specified velocity (speed and direction)
     * @param point     Starting position
     * @param speed     Starting speed
     * @param direction Starting angle in radians
     */
    public MovingObject(PointF point, double speed, float direction) {
        super();
        this.hp = STARTING_HP;
        this.speed = speed;
        this.direction = direction;
        this.worldPosition = point;
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
