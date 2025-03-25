package tile;

import entity.Player;
import main.GamePanel;
import tools.UtilityTool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class TileManager extends Tile {
    GamePanel gp;
    Player player;
    UtilityTool uTool;
    BufferedImage img;
    BufferedImage[] tileImages;

    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        uTool = new UtilityTool(gp);
        tile = new Tile[24];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        
        importTileImage("/res/images/world/ground/desert.png");
        loadSubImages(24,0);
        loadTileImages();
        
    }

    public void importTileImage(String filepath) {
        img = UtilityTool.importImg(filepath);
    }

    public void loadSubImages(int tilesLength, int rows) {
        tileImages = new BufferedImage[tilesLength];

        for (int i = 0; i < tileImages.length; i++) {
            tileImages[i] = img.getSubimage(i*32, rows*32, 32, 32);
        }
    }

    public void loadTileImages() {
        for (int i = 0; i < tileImages.length; i++) {
            tile[i] = new Tile();
            tile[i].image = UtilityTool.scaleImage(tileImages[i], gp.tileSize, gp.tileSize);
        }      
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
    
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            double screenX = worldX - gp.playing.player.cameraX;
            double screenY = worldY - gp.playing.player.cameraY;
    
            // Only draw tiles visible on the screen
            if (worldX + gp.tileSize > gp.playing.player.cameraX && 
                worldX - gp.tileSize < gp.playing.player.cameraX + gp.screenWidth && 
                worldY + gp.tileSize > gp.playing.player.cameraY && 
                worldY - gp.tileSize < gp.playing.player.cameraY + gp.screenHeight) {
    
                g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, null);
                

                //DEBUG directly on tiles
                // g2.setColor(Color.WHITE);
                // g2.drawString(String.valueOf(tileNum), (int)screenX, (int)screenY);
            }
    
            worldCol++;
    
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    
}
