package edu.byu.cs.superasteroids.model_classes.game_definition_objects;

import java.io.IOException;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model_classes.visible_objects.Ship;

/**
 * Created by Marshall Garey
 * Contains information about the type of projectile that corresponds to a specific type of cannon.
 */
public class ProjectileType extends ImageObject {

    private ContentManager contentManager;

    /**
     * The file path to the attack sound. Note that this is different from the file path
     * to the image for the projectile.
     */
    private String attackSoundFile;

    /**
     * This is the id of the attack sound file after it is loaded into memory. This id is accessed in order to play
     * the sound.
     */
    private int attackSoundFileID;

    /**
     * How much damage this projectile does
     */
    private int damage;

    // Initializes all info about the type of projectile.
    public ProjectileType(String image, int width, int height, String attackSoundFile, int damage) {
        super(image, width, height, Ship.SHIP_SCALE); // TODO: scale this correctly
        this.attackSoundFile = attackSoundFile;
        this.damage = damage;
    }

    public void loadContent(ContentManager contentManager) {
        this.contentManager = contentManager;
        loadImage(contentManager);
        try {
            attackSoundFileID = contentManager.loadSound(attackSoundFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadContent(ContentManager contentManager) {
        unloadImage(contentManager);
        contentManager.unloadSound(attackSoundFileID);
    }

    /**
     * Plays the sound associated with this projectile using
     * ContentManager.playSound(int id, double volume, double speed)
     * Volume is in the range [0.0,1.0].
     * Speed is in the range [0.5,2.0], where 1.0 is normal playback.
     */
    public void playSound() {
        contentManager.playSound(attackSoundFileID, 1, 1);
    }

    // ============================================================== //
    // ================== getters and setters ======================= //
    // ============================================================== //

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
