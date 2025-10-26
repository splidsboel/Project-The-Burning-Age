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
        double leftx   = e.solidArea.x;
        double rightx  = e.solidArea.x + e.solidArea.width;
        double topy    = e.solidArea.y;
        double bottomy = e.solidArea.y + e.solidArea.height;


        // ---- UP probe ----
        {
            int nextTopRow = (int)((topy - e.speed) / gp.tileSize);
            int leftCol    = (int)(leftx / gp.tileSize);
            int rightCol   = (int)(rightx / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[leftCol][nextTopRow];
            Tile t2 = gp.tileM.tileMap[rightCol][nextTopRow];

            if (t1.collision || t2.collision) {
                e.collisionUp = true;
            }
        }

        // ---- DOWN probe ----
        {
            int nextBottomRow = (int)((bottomy + e.speed) / gp.tileSize);
            int leftCol       = (int)(leftx / gp.tileSize);
            int rightCol      = (int)(rightx / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[leftCol][nextBottomRow];
            Tile t2 = gp.tileM.tileMap[rightCol][nextBottomRow];

            if (t1.collision || t2.collision) {
                e.collisionDown = true;
            }
        }

        // ---- LEFT probe ----
        {
            int nextLeftCol   = (int)((leftx - e.speed) / gp.tileSize);
            int topRow        = (int)(topy / gp.tileSize);
            int bottomRow     = (int)(bottomy / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[nextLeftCol][topRow];
            Tile t2 = gp.tileM.tileMap[nextLeftCol][bottomRow];

            if (t1.collision || t2.collision) {
                e.collisionLeft = true;
            }
        }

        // ---- RIGHT probe ----
        {
            int nextRightCol  = (int)((rightx + e.speed) / gp.tileSize);
            int topRow        = (int)(topy / gp.tileSize);
            int bottomRow     = (int)(bottomy / gp.tileSize);

            Tile t1 = gp.tileM.tileMap[nextRightCol][topRow];
            Tile t2 = gp.tileM.tileMap[nextRightCol][bottomRow];

            if (t1.collision || t2.collision) {
                e.collisionRight = true;
            }
        }
    }

    private void checkDecor(Actor e) {
        
        if (gp.getEntityM() == null || gp.getEntityM().getDecorSolidAreaList().isEmpty()) return;

        // Build 4 predicted rectangles. One per axis.
        Rectangle upBox = new Rectangle(
            (int)(e.solidArea.x),
            (int)(e.solidArea.y - e.speed),
            e.solidArea.width,
            e.solidArea.height
        );

        Rectangle downBox = new Rectangle(
            (int)(e.solidArea.x),
            (int)(e.solidArea.y + e.speed),
            e.solidArea.width,
            e.solidArea.height
        );

        Rectangle leftBox = new Rectangle(
            (int)(e.solidArea.x - e.speed),
            (int)(e.solidArea.y),
            e.solidArea.width,
            e.solidArea.height
        );

        Rectangle rightBox = new Rectangle(
            (int)(e.solidArea.x + e.speed),
            (int)(e.solidArea.y),
            e.solidArea.width,
            e.solidArea.height
        );

        for (Rectangle decor : gp.getEntityM().getDecorSolidAreaList()) {
            if (upBox.intersects(decor))    e.collisionUp = true; //System.out.println("Decor coliision!");
            if (downBox.intersects(decor))  e.collisionDown = true;
            if (leftBox.intersects(decor))  e.collisionLeft = true;
            if (rightBox.intersects(decor)) e.collisionRight = true;

        }
    }
}
