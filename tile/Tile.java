package tile;

import java.awt.image.BufferedImage;

public abstract class Tile {
    public BufferedImage image;
    public boolean collision;

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean hasCollision() {
        return collision;
    }
}
