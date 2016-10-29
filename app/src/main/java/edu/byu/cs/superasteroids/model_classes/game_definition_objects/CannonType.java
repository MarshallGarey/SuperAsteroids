package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Projectile;

/**
 * Created by Marshall Garey
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

    public void loadContent(ContentManager contentManager) {
        loadImage(contentManager);
        projectileType.loadContent(contentManager);
    }

    public void unloadContent(ContentManager contentManager) {
        unloadImage(contentManager);
        projectileType.unloadContent(contentManager);
    }

    public Projectile fire(PointF position, double projectileSpeed) {
        return new Projectile(position, projectileSpeed, direction, projectileType);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

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
