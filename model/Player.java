package model;

import java.awt.Image;
import interfaces.Movable;

/**
 * Player class representing Pac-Man.
 */
public class Player extends GameEntity implements Movable {
    
    private char direction;
    private int velocityX;
    private int velocityY;
    private int lives;
    private int tileSize;
    private Image upImage, downImage, leftImage, rightImage;
    
    public Player(Image image, int x, int y, int width, int height, int tileSize) {
        super(image, x, y, width, height);
        this.direction = 'R';
        this.velocityX = 0;
        this.velocityY = 0;
        this.lives = 3;
        this.tileSize = tileSize;
    }
    
    public int getLives() { return lives; }
    @Override public char getDirection() { return direction; }
    @Override public int getVelocityX() { return velocityX; }
    @Override public int getVelocityY() { return velocityY; }
    
    public void setLives(int lives) { this.lives = lives; }
    public void setVelocityX(int velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }
    
    public void setDirectionImages(Image up, Image down, Image left, Image right) {
        this.upImage = up;
        this.downImage = down;
        this.leftImage = left;
        this.rightImage = right;
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
        updateImage();
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
    
    private void updateImage() {
        switch (direction) {
            case 'U': if (upImage != null) setImage(upImage); break;
            case 'D': if (downImage != null) setImage(downImage); break;
            case 'L': if (leftImage != null) setImage(leftImage); break;
            case 'R': if (rightImage != null) setImage(rightImage); break;
        }
    }
    
    @Override
    public void update() {
        updateImage();
    }
    
    @Override
    public void reset() {
        super.reset();
        this.velocityX = 0;
        this.velocityY = 0;
        this.direction = 'R';
    }
    
    public boolean loseLife() {
        lives--;
        return lives > 0;
    }
    
    public void undoMove() {
        setX(getX() - velocityX);
        setY(getY() - velocityY);
    }
}
