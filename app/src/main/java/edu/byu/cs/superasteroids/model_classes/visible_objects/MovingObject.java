package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;
import android.graphics.RectF;

import java.io.IOException;

import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * Created by Marshall Garey
 * This class contains information and methods common to all moving objects. Moving objects are
 * things that move around the screen, such as projectiles, asteroids, and the ship.
 */
public class MovingObject extends VisibleObject {

    protected final int STARTING_HP = 10;

    /**
     * The speed at which the object is moving.
     */
    protected double speed;

    /**
     * The object's current hit points.
     */
    protected int hp;

    /**
     * The path to the impact sound
     */
    private static final String impactSound = "sounds/crash3.mp3";
    private static int impactSoundId = -1;
    private static ContentManager contentManager;

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
        this.scale = 1;
    }

    /**
     * Returns true if this object has collided with the other object.
     */
    public boolean collisionWith(RectF otherObject) {
        return (RectF.intersects(this.hitBox, otherObject));
    }

    /**
     * Static because it only needs to be called once and is called on the class, not an instance.
     */
    public static void loadImpactSound(ContentManager contentManager) {
        try {
            impactSoundId = contentManager.loadSound(impactSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MovingObject.contentManager = contentManager;
    }

    public static void unloadImpactSound(ContentManager contentManager) {
        contentManager.unloadSound(impactSoundId);
        MovingObject.contentManager = null;
        impactSoundId = -1;
    }

    /**
     * Plays the sound of an impact.
     */
    public static void playImpactSound() {
        contentManager.playSound(impactSoundId, 1, 1);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

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
