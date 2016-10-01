package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

/**
 * Created by Marshall Garey
 * Contains information about the type of projectile that corresponds to a specific type of cannon.
 */
public class ProjectileType extends ImageObject {

    /**
     * The file path to the attack sound. Note that this is different from the file path
     * to the image for the projectile.
     */
    private String attackSoundFile;

    /**
     * How much damage this projectile does
     */
    private int damage;

    // Initializes all info about the type of projectile.
    public ProjectileType(String image, int width, int height, String attackSoundFile, int damage) {
        super(image, width, height);
        this.attackSoundFile = attackSoundFile;
        this.damage = damage;
    }

    public String getAttackSoundFile() {
        return attackSoundFile;
    }

    public void setAttackSoundFile(String attackSoundFile) {
        this.attackSoundFile = attackSoundFile;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
