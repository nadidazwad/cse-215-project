package interfaces;

/**
 * Interface for entities that can move.
 */
public interface Movable {
    void move();
    void updateDirection(char direction);
    char getDirection();
    int getVelocityX();
    int getVelocityY();
}
