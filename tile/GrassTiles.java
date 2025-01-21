package tile;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class GrassTiles extends TileManager {
    BufferedImage img;
    BufferedImage[] tileImages;
    
    public GrassTiles(GamePanel gp) {
        super(gp);
        this.gp = gp;

        tile = new Tile[10 + 16];

        importTileImage("/res/aseprites/map/grassLayer.png");
        
    }


}
