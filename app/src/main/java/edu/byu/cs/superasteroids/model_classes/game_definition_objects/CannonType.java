package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey on 1/18/2016.
 * Information about the type of cannon for the ship.
 */
public class CannonType extends AttachableShipPart {

    /**
     * the id of the cannon type - we can have more the one
     */
    private int id;

    /**
     * where the projectile is shot from
     */
    private CoordinateString emitPoint;

    /**
     * the type of projectile shot from this cannon
     */
    private ProjectileType projectileType;

    // Initialize data
    public CannonType(String file, int width, int height, String attachPoint, int id,
                      String emitPoint, String projectileImage, int projectileWidth,
                      int projectileHeight, String attackSound, int damage) {
        super(file, width, height, attachPoint);
        this.id = id;
        this.emitPoint = new CoordinateString(emitPoint);
        this.projectileType = new ProjectileType(projectileImage, projectileWidth,
                projectileHeight, attackSound, damage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CoordinateString getEmitPoint() {
        return emitPoint;
    }

    public void setEmitPoint(CoordinateString emitPoint) {
        this.emitPoint = emitPoint;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }
}
