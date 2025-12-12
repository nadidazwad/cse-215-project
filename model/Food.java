package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Food class representing pellets that Pac-Man eats.
 */
public class Food extends GameEntity {
    
    private int points;
    private boolean eaten;
    
    public Food(int x, int y, int width, int height) {
        super(null, x, y, width, height);
        this.points = 10;
        this.eaten = false;
    }
    
    public Food(int x, int y, int width, int height, int points) {
        super(null, x, y, width, height);
        this.points = points;
        this.eaten = false;
    }
    
    public int getPoints() { return points; }
    public boolean isEaten() { return eaten; }
    public void setEaten(boolean eaten) { this.eaten = eaten; }
    
    @Override
    public void draw(Graphics g) {
        if (!eaten) {
            g.setColor(Color.WHITE);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
    
    @Override
    public void update() {}
    
    @Override
    public void reset() {
        super.reset();
        this.eaten = false;
    }
    
    public int eat() {
        if (!eaten) {
            eaten = true;
            return points;
        }
        return 0;
    }
}
