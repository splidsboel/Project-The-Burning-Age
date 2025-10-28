package game.tiles;

import game.tiles.behaviors.Swimable;
import javafx.scene.image.Image;

public class WaterTile extends Tile implements Swimable {
    public WaterTile(Image image) {
        super(image);
    }
}