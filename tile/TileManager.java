
package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tile.tiles.GrassTile;
import tile.tiles.SandTile;
import tile.tiles.WaterTile;
import tools.UtilityTool;
import world.actor.Player;

public class TileManager {
    GamePanel gp;
    public Tile[][] tileMap;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tileMap = new Tile[gp.maxWorldCol][gp.maxWorldRow];
        registerTilesets();
    }

    public void registerTilesets() {
    
        BufferedImage grassSheet = UtilityTool.importImg("images/world/ground/tiled/grass.png");
        BufferedImage waterSheet = UtilityTool.importImg("images/world/ground/tiled/water.png");
        BufferedImage sandSheet = UtilityTool.importImg("images/world/ground/tiled/sand.png");
        
        Tileset grassSet = new Tileset(GrassTile.class, grassSheet, gp.originalTileSize);
        Tileset waterSet = new Tileset(WaterTile.class, waterSheet, gp.originalTileSize);
        Tileset sandSet = new Tileset(SandTile.class, sandSheet, gp.originalTileSize);

        TilesetFactory.registerTileset(1, waterSet);
        TilesetFactory.registerTileset(37, grassSet);
        TilesetFactory.registerTileset(53, sandSet);
        
    }

    public void setTile(int col, int row, Tile tile) {
        tileMap[col][row] = tile;
    }

    public void draw(Graphics2D g2) {
        Player player = gp.getPlaying().getPlayer();
        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                Tile tile = tileMap[col][row];
                if (tile == null) continue;

                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                double screenX = worldX - player.getCameraX();
                double screenY = worldY - player.getCameraY();

                if (worldX + gp.tileSize > player.getCameraX() &&
                    worldX - gp.tileSize < player.getCameraX() + gp.screenWidth &&
                    worldY + gp.tileSize > player.getCameraY() &&
                    worldY - gp.tileSize < player.getCameraY() + gp.screenHeight) {

                    g2.drawImage(tile.getImage(), (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
                    
                    //DEBUG directly on tiles
                //     g2.setColor(Color.BLACK);
                //     g2.drawString("Row:"+String.valueOf(row), (int)screenX+ gp.originalTileSize/2, (int)screenY);
                //     g2.drawString("Col:"+String.valueOf(col), (int)screenX+ gp.originalTileSize/2, (int)screenY-10);
                }
            }
        }
    }
}




       