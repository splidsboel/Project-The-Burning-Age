package main;

import java.awt.Rectangle;
import tile.Tile;
import world.actor.Actor;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void check(Actor e) {
        // reset
        e.collisionUp = false;
        e.collisionDown = false;
        e.collisionLeft = false;
        e.collisionRight = false;

        checkTile(e);
        checkDecor(e);
    }

    private void checkTile(Actor e) {
        // Current world bounds of the solidArea
        double leftWorldX   = e.worldX + e.solidArea.x;
        double rightWorldX  = e.worldX + e.solidArea.x + e.solidArea.width;
        double topWorldY    = e.worldY + e.solidArea.y;
        double bottomWorldY = e.worldY + e.solidArea.y + e.solidArea.height;

        // ---- UP probe ----
        {
            int nextTopRow = (int)((topWorldY - e.speed) / gp.tileSize);
            int leftCol    = (int)(leftWorldX / gp.tileSize);
            int rightCol   = (int)(rightWorldX / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[leftCol][nextTopRow];
            Tile t2 = gp.tileM.tileMap[rightCol][nextTopRow];

            if (t1.collision || t2.collision) {
                e.collisionUp = true;
            }
        }

        // ---- DOWN probe ----
        {
            int nextBottomRow = (int)((bottomWorldY + e.speed) / gp.tileSize);
            int leftCol       = (int)(leftWorldX / gp.tileSize);
            int rightCol      = (int)(rightWorldX / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[leftCol][nextBottomRow];
            Tile t2 = gp.tileM.tileMap[rightCol][nextBottomRow];

            if (t1.collision || t2.collision) {
                e.collisionDown = true;
            }
        }

        // ---- LEFT probe ----
        {
            int nextLeftCol   = (int)((leftWorldX - e.speed) / gp.tileSize);
            int topRow        = (int)(topWorldY / gp.tileSize);
            int bottomRow     = (int)(bottomWorldY / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[nextLeftCol][topRow];
            Tile t2 = gp.tileM.tileMap[nextLeftCol][bottomRow];

            if (t1.collision || t2.collision) {
                e.collisionLeft = true;
            }
        }

        // ---- RIGHT probe ----
        {
            int nextRightCol  = (int)((rightWorldX + e.speed) / gp.tileSize);
            int topRow        = (int)(topWorldY / gp.tileSize);
            int bottomRow     = (int)(bottomWorldY / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[nextRightCol][topRow];
            Tile t2 = gp.tileM.tileMap[nextRightCol][bottomRow];

            if (t1.collision || t2.collision) {
                e.collisionRight = true;
            }
        }
    }

    private void checkDecor(Actor e) {
        if (gp.decorM == null || gp.decorM.getDecorSolidAreaList().isEmpty()) return;

        // Build 4 predicted rectangles. One per axis.
        Rectangle upBox = new Rectangle(
            (int)(e.worldX + e.solidArea.x),
            (int)(e.worldY + e.solidArea.y - e.speed),
            e.solidArea.width,
            e.solidArea.height
        );

        Rectangle downBox = new Rectangle(
            (int)(e.worldX + e.solidArea.x),
            (int)(e.worldY + e.solidArea.y + e.speed),
            e.solidArea.width,
            e.solidArea.height
        );

        Rectangle leftBox = new Rectangle(
            (int)(e.worldX + e.solidArea.x - e.speed),
            (int)(e.worldY + e.solidArea.y),
            e.solidArea.width,
            e.solidArea.height
        );

        Rectangle rightBox = new Rectangle(
            (int)(e.worldX + e.solidArea.x + e.speed),
            (int)(e.worldY + e.solidArea.y),
            e.solidArea.width,
            e.solidArea.height
        );

        for (Rectangle decor : gp.decorM.getDecorSolidAreaList()) {
            if (upBox.intersects(decor))    e.collisionUp = true;
            if (downBox.intersects(decor))  e.collisionDown = true;
            if (leftBox.intersects(decor))  e.collisionLeft = true;
            if (rightBox.intersects(decor)) e.collisionRight = true;
        }
    }
}
