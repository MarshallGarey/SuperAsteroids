package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.model_classes.game_definition_objects.ProjectileType;

/**
 * Created by Marshall Garey
 * This class has information about the projectiles that the ship's cannon shoots.
 */
public class Projectile extends MovingObject{

    /**
     * The type of projectile.
     */
    private ProjectileType projectileType;

    public Projectile(float x, float y, double speed, float direction,
                      ProjectileType projectileType) {
        super(new PointF(x,y), speed, direction);
        this.hp = 1;
        this.projectileType = projectileType;
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
