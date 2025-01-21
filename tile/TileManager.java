package tile;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import entity.Player;
import main.GamePanel;
import tools.UtilityTool;

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
        tile = new Tile[10 + 15];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        importTileImage("/res/aseprites/map/baseLayer.png");
        loadSubImages(15,0);
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
            tile[10 + i] = new Tile();
            tile[10 + i].image = uTool.scaleImage(tileImages[i], gp.tileSize, gp.tileSize);
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
            }
    
            worldCol++;
    
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public void loadMap(String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
