package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import java.util.HashSet;

import edu.byu.cs.superasteroids.AsteroidsGame;
import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ProjectileType;

/**
 * Created by Marshall Garey
 * This class has information about the projectiles that the ship's cannon shoots.
 */
public class Projectile extends MovingObject{

    private final float DIRECTION_OFFSET = (float)-Math.PI / 2;

    /**
     * The type of projectile.
     */
    private ProjectileType projectileType;

    public Projectile(PointF emitPoint, double speed, float direction,
                      ProjectileType projectileType) {
        super(emitPoint, speed, direction);
        this.hp = 1;
        this.projectileType = projectileType;
        this.imageId = projectileType.imageId;
        this.width = projectileType.width;
        this.height = projectileType.height;
        this.scale = projectileType.scale;
        projectileType.playSound();

        // initialize the hit box
        updateHitBox();
    }

    /**
     * Update the position of the missile and check for collision with an asteroid or exiting the level boundaries.
     * Return a nonzero status when the missile collides with an asteroid or leaves the level so it will be removed.
     * @param elapsedTime Time since the last update.
     * @return -1 if the missile should be removed, 0 otherwise.
     */
    public int update(double elapsedTime, HashSet<Asteroid> regularAsteroids,
                      HashSet<GrowingAsteroid> growingAsteroids,
                      HashSet<Octeroid> octeroids) {

        // Move the missile
        super.update(speed, direction + DIRECTION_OFFSET, elapsedTime);

        // Check if it has collided with an asteroid
        for (Asteroid asteroid : regularAsteroids) {
            if (this.collisionWith(asteroid.getHitBox())) {
                asteroid.touch(this);
                return -1;
            }
        }

        for (GrowingAsteroid growingAsteroid : growingAsteroids) {
            if (this.collisionWith(growingAsteroid.getHitBox())) {
                growingAsteroid.touch(this);
                return -1;
            }
        }

        for (Octeroid octeroid : octeroids) {
            if (this.collisionWith(octeroid.getHitBox())) {
                octeroid.touch(this);
                return -1;
            }
        }

        // Check if the missile has exited the level
        if (outOfBounds(AsteroidsGame.getCurrentLevel().getLevelWidth(),
                AsteroidsGame.getCurrentLevel().getLevelHeight())) {
            return -1;
        }
        return 0;
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

    public ProjectileType getProjectileType() {
        return projectileType;
    }
}
