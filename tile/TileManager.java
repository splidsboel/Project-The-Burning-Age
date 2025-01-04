package tile;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;

import java.awt.Graphics2D;

import tools.UtilityTool;

public class TileManager extends Tile {
    GamePanel gp;
    Player player;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        
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

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
    
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.playing.player.cameraX;
            int screenY = worldY - gp.playing.player.cameraY;
    
            // Only draw tiles visible on the screen
            if (worldX + gp.tileSize > gp.playing.player.cameraX && 
                worldX - gp.tileSize < gp.playing.player.cameraX + gp.screenWidth && 
                worldY + gp.tileSize > gp.playing.player.cameraY && 
                worldY - gp.tileSize < gp.playing.player.cameraY + gp.screenHeight) {
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
    
            worldCol++;
    
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    
 
    //HELPER METHODS
    public void setup(int index, String imageName) {
        UtilityTool tool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/images/tiles/" + imageName + ".png"));
            tile[index].image = tool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        setup(0, "Durotar_dirt_01");
        setup(1, "Durotar_dirt_01");
        setup(2, "Durotar_dirt_01");
        setup(3, "Durotar_dirt_01");
        setup(4, "Durotar_dirt_01");
        setup(5, "Durotar_dirt_01");
        setup(6, "Durotar_dirt_01");
        setup(7, "Durotar_dirt_01");
        setup(8, "Durotar_dirt_01");
        setup(9, "Durotar_dirt_01");
        
        setup(10, "Durotar_dirt_01");
        setup(11, "Durotar_dirt_02");
        setup(12, "Durotar_dirt_03");
        setup(13, "Durotar_dirt_04");
        setup(14, "Durotar_dirt_stone_01");
        setup(15, "Durotar_dirt_stone_02");
        setup(16, "Durotar_dirt_stone_03");
        setup(17, "Durotar_dirt_road_01");
        setup(18, "Durotar_dirt_road_02");
        setup(19, "Durotar_dirt_road_03");
        setup(20, "Durotar_dirt_road_04");
        setup(23, "Durotar_dirt_road_05");
        setup(24, "Durotar_dirt_road_corner_01");
        setup(25, "Durotar_dirt_road_corner_02");
        setup(26, "Durotar_dirt_road_corner_03");
        setup(27, "Durotar_dirt_road_corner_04");
        setup(28, "Durotar_dirt_road_widecorner_topleft");
        setup(29, "Durotar_dirt_road_widecorner_bottomleft");
        setup(30, "Durotar_dirt_road_widecorner_topright");
        setup(31, "Durotar_dirt_road_widecorner_bottomright");
    }

}
