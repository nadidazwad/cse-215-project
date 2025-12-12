package model;

import java.awt.Image;
import java.awt.Graphics;
import interfaces.Collidable;

/**
 * Abstract base class for all game entities.
 */
public abstract class GameEntity implements Collidable {
    
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    private int startX;
    private int startY;
    
    public GameEntity(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startX = x;
        this.startY = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImage() { return image; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setImage(Image image) { this.image = image; }
    
    public abstract void update();
    
    public void reset() {
        this.x = this.startX;
        this.y = this.startY;
    }
    
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }
    
    @Override
    public boolean collidesWith(Collidable other) {
        if (other instanceof GameEntity) {
            GameEntity o = (GameEntity) other;
            return this.x < o.x + o.width &&
                   this.x + this.width > o.x &&
                   this.y < o.y + o.height &&
                   this.y + this.height > o.y;
        }
        return false;
    }
    
    @Override
    public int[] getBounds() {
        return new int[]{x, y, width, height};
    }
}
