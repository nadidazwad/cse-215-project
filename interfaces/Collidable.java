package interfaces;

/**
 * Interface for entities that can collide with other objects.
 */
public interface Collidable {
    boolean collidesWith(Collidable other);
    int[] getBounds();
}
