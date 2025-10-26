package main;

import java.awt.Rectangle;
import java.util.List;
import tile.Tile;
import tools.Damageable;
import tools.Renderable;
import world.actor.Actor;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void check(Actor a) {
        // reset
        a.collisionUp = false;
        a.collisionDown = false;
        a.collisionLeft = false;
        a.collisionRight = false;

        checkTile(a);
        checkDecor(a);
        checkActor(a);
    
    }

    private void checkActor(Actor a) {
        List<Renderable> entities = gp.getEntityM().getHitBoxVisibleEntities(
            gp.getPlaying().getPlayer().getCameraX(), gp.getPlaying().getPlayer().getCameraY(), gp.screenWidth, gp.screenHeight
        );
        if (entities.isEmpty()) {
            return; 
        }

        Rectangle upBox = new Rectangle(
            (int)(a.getHitBox().x),
            (int)(a.getHitBox().y - a.speed),
            a.getHitBox().width,
            a.getHitBox().height
        );

        Rectangle downBox = new Rectangle(
            (int)(a.getHitBox().x),
            (int)(a.getHitBox().y + a.speed),
            a.getHitBox().width,
            a.getHitBox().height
        );

        Rectangle leftBox = new Rectangle(
            (int)(a.getHitBox().x - a.speed),
            (int)(a.getHitBox().y),
            a.getHitBox().width,
            a.getHitBox().height
        );

        Rectangle rightBox = new Rectangle(
            (int)(a.getHitBox().x + a.speed),
            (int)(a.getHitBox().y),
            a.getHitBox().width,
            a.getHitBox().height
        );

        for (Renderable e : entities) {
            if (e == a) continue; // 
            if (e instanceof Damageable d && upBox.intersects(e.getHitBox())) {
                d.damage(10);
            };  
            if (e instanceof Damageable d && downBox.intersects(e.getHitBox())) {
                d.damage(10);
            };  
            if ( e instanceof Damageable d &&leftBox.intersects(e.getHitBox())) {
                d.damage(10);
            };  
            if ( e instanceof Damageable d &&rightBox.intersects(e.getHitBox())) {
                d.damage(10);
            };  
        }
    }

    private void checkTile(Actor e) {
        
        // Current world bounds of the getHitBox()
        double leftx   = e.getSolidArea().x;
        double rightx  = e.getSolidArea().x + e.getSolidArea().width;
        double topy    = e.getSolidArea().y;
        double bottomy = e.getSolidArea().y + e.getSolidArea().height;


        // ---- UP probe ----
        {
            int nextTopRow = (int)((topy - e.speed) / gp.tileSize);
            int leftCol    = (int)(leftx / gp.tileSize);
            int rightCol   = (int)(rightx / gp.tileSize);

            Tile t1 = gp.getTileM().tileMap[leftCol][nextTopRow];
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

    private void checkDecor(Actor a) {
        List<Renderable> entities = gp.getEntityM().getSolidAreaVisibleEntities(
            gp.getPlaying().getPlayer().getCameraX(), gp.getPlaying().getPlayer().getCameraY(), gp.screenWidth, gp.screenHeight
        );
        if (entities.isEmpty()) return;

        Rectangle upBox = new Rectangle(
            (int)(a.getSolidArea().x),
            (int)(a.getSolidArea().y - a.speed),
            a.getSolidArea().width,
            a.getSolidArea().height
        );

        Rectangle downBox = new Rectangle(
            (int)(a.getSolidArea().x),
            (int)(a.getSolidArea().y + a.speed),
            a.getSolidArea().width,
            a.getSolidArea().height
        );

        Rectangle leftBox = new Rectangle(
            (int)(a.getSolidArea().x - a.speed),
            (int)(a.getSolidArea().y),
            a.getSolidArea().width,
            a.getSolidArea().height
        );

        Rectangle rightBox = new Rectangle(
            (int)(a.getSolidArea().x + a.speed),
            (int)(a.getSolidArea().y),
            a.getSolidArea().width,
            a.getSolidArea().height
        );

        for (Renderable e : entities) {
            if (upBox.intersects(e.getSolidArea()))    a.collisionUp = true;
            if (downBox.intersects(e.getSolidArea()))  a.collisionDown = true;
            if (leftBox.intersects(e.getSolidArea()))  a.collisionLeft = true;
            if (rightBox.intersects(e.getSolidArea())) a.collisionRight = true;
        }
    }
}
