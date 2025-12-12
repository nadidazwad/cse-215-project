package model;

import java.awt.Image;
import interfaces.Movable;

/**
 * Ghost class representing enemy ghosts.
 */
public class Ghost extends GameEntity implements Movable {
    
    private char direction;
    private int velocityX;
    private int velocityY;
    private String color;
    private boolean isScared;
    private int tileSize;
    private Image normalImage;
    private Image scaredImage;
    
    public Ghost(Image image, int x, int y, int width, int height, String color, int tileSize) {
        super(image, x, y, width, height);
        this.color = color;
        this.direction = 'U';
        this.velocityX = 0;
        this.velocityY = 0;
        this.isScared = false;
        this.tileSize = tileSize;
        this.normalImage = image;
    }
    
    public String getColor() { return color; }
    public boolean isScared() { return isScared; }
    @Override public char getDirection() { return direction; }
    @Override public int getVelocityX() { return velocityX; }
    @Override public int getVelocityY() { return velocityY; }
    
    public void setScared(boolean scared) {
        this.isScared = scared;
        if (scared && scaredImage != null) {
            setImage(scaredImage);
        } else {
            setImage(normalImage);
        }
    }
    
    public void setScaredImage(Image scaredImage) {
        this.scaredImage = scaredImage;
    }
    
    @Override
    public void move() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
    }
    
    @Override
    public void updateDirection(char newDirection) {
        this.direction = newDirection;
        updateVelocity();
    }
    
    private void updateVelocity() {
        int speed = tileSize / 4;
        switch (direction) {
            case 'U': velocityX = 0; velocityY = -speed; break;
            case 'D': velocityX = 0; velocityY = speed; break;
            case 'L': velocityX = -speed; velocityY = 0; break;
            case 'R': velocityX = speed; velocityY = 0; break;
        }
    }
    
    @Override
    public void update() {}
    
    @Override
    public void reset() {
        super.reset();
        this.velocityX = 0;
        this.velocityY = 0;
        this.isScared = false;
        if (normalImage != null) {
            setImage(normalImage);
        }
    }
    
    public void undoMove() {
        setX(getX() - velocityX);
        setY(getY() - velocityY);
    }
    
    public boolean shouldEscapeSpawn(int targetY) {
        return getY() == targetY && direction != 'U' && direction != 'D';
    }
}
