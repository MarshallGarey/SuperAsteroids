package edu.byu.cs.superasteroids.model_classes.visible_objects;

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

    public Projectile(int x, int y, int hp, int speed, float direction,
                      ProjectileType projectileType) {
        super(x, y, hp, speed, direction);
        this.projectileType = projectileType;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }
}
