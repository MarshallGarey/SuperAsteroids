package edu.byu.cs.superasteroids.model_classes.visible_objects;

/**
 * Created by Marshall Garey on 2/9/2016.
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

    /**
     * Detects if the object has collided with any other object.
     */
    void detectCollision() {
       return;
    }
}
