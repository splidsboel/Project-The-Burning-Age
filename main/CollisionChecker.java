package main;

import entity.Entity;
import tile.Tile;

public class CollisionChecker {
    GamePanel gp;

    public int entityLeftCol;
    public int entityRightCol;
    public int entityTopRow;
    public int entityButtomRow;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        double entityLeftWorldX = entity.worldX + entity.solidArea.x;
        double entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        double entityTopWorldY = entity.worldY + entity.solidArea.y;
        double entityButtomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        entityLeftCol = (int)(entityLeftWorldX / gp.tileSize);
        entityRightCol = (int)(entityRightWorldX / gp.tileSize);
        entityTopRow = (int)(entityTopWorldY / gp.tileSize);
        entityButtomRow = (int)(entityButtomWorldY / gp.tileSize);


    
        Tile tileLeft, tileRight;

        if (entity.up) {
            entityTopRow = (int)((entityTopWorldY - entity.speed) / gp.tileSize);
            tileLeft = gp.tileM.tileMap[entityLeftCol][entityTopRow];
            tileRight = gp.tileM.tileMap[entityRightCol][entityTopRow];
            if (tileLeft.collision == true || tileRight.collision == true) {
                entity.collisionOn = true;
            }
        }
        if (entity.down) {
            entityButtomRow = (int)((entityButtomWorldY + entity.speed) / gp.tileSize);
            tileLeft = gp.tileM.tileMap[entityLeftCol][entityButtomRow];
            tileRight = gp.tileM.tileMap[entityRightCol][entityButtomRow];
            if (tileLeft.collision == true || tileRight.collision == true) {
                entity.collisionOn = true;
            }
        }
        if (entity.left) {
            entityLeftCol = (int)((entityLeftWorldX - entity.speed) / gp.tileSize);
            tileLeft = gp.tileM.tileMap[entityLeftCol][entityTopRow];
            tileRight = gp.tileM.tileMap[entityLeftCol][entityButtomRow];
            if (tileLeft.collision == true || tileRight.collision == true) {
                entity.collisionOn = true;
            }  
        }
        if(entity.right) {
            entityRightCol = (int)((entityRightWorldX + entity.speed) / gp.tileSize);
            tileLeft = gp.tileM.tileMap[entityRightCol][entityTopRow];
            tileRight = gp.tileM.tileMap[entityRightCol][entityButtomRow];
            if (tileLeft.collision == true || tileRight.collision == true) {
                entity.collisionOn = true;
            }
        }      
    }
}
