package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

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
     * Update the position of the missile. TODO: check for collisions.
     * Return a nonzero status when the missile leaves the level so it will be removed.
     * @param elapsedTime Time since the last update.
     * @return -1 if the missile has the left bounds of the level, 0 otherwise.
     */
    public int update(double elapsedTime) {
        super.update(speed, direction + DIRECTION_OFFSET, elapsedTime);
        // The missile has exited the level now.
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

    public void setProjectileType(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }
}
