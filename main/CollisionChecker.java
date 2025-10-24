package main;

import entity.Entity;
import java.awt.Rectangle;
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

    public void checkDecor(Entity entity) {
        if (gp.decorM == null || gp.decorM.getDecorSolidAreaList().isEmpty()) return;

        // Predict next position
        double nextX = entity.worldX;
        double nextY = entity.worldY;

        if (entity.up) nextY -= entity.speed;
        if (entity.down) nextY += entity.speed;
        if (entity.left) nextX -= entity.speed;
        if (entity.right) nextX += entity.speed;

        // Build predicted collision box in world coordinates
        int sx = (int)(nextX + entity.solidArea.x);
        int sy = (int)(nextY + entity.solidArea.y);
        Rectangle nextArea = new Rectangle(sx, sy, entity.solidArea.width, entity.solidArea.height);

        // Check for intersection with any decor rectangle
        for (Rectangle decor : gp.decorM.getDecorSolidAreaList()) {
            if (nextArea.intersects(decor)) {
                entity.collisionOn = true;
                break;
            }
        }

        
    }

}
