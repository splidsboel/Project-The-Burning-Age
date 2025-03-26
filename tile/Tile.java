package tile;

import java.awt.image.BufferedImage;

public abstract class Tile {
    protected BufferedImage image;
    protected boolean walkable;

    public Tile(BufferedImage image, boolean walkable) {
        this.image = image;
        this.walkable = walkable;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
