package game.tiles;

import game.tiles.behaviors.Swimmable;
import javafx.scene.image.Image;

public class WaterTile extends Tile implements Swimmable {
    public WaterTile(Image image) {
        super(image);
    }

    @Override
    public double getSubmergeOffset(double tileSize) {
        return tileSize * 0.3;
    }

    public boolean isWater() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
