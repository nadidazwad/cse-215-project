package model;

import java.awt.Image;

/**
 * Wall class representing maze walls.
 */
public class Wall extends GameEntity {
    
    public Wall(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }
    
    @Override
    public void update() {}
    
    @Override
    public void reset() {}
}
