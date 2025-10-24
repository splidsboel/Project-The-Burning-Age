package world.decoration;

import java.awt.Graphics2D;
import main.GamePanel;
import world.DecorAssetLoader;
import world.WorldEntity;

public class TreeWide extends WorldEntity {
    GamePanel gp;

    
    public TreeWide(double x, double y, GamePanel gp, int aniSpeed) {
        super(gp, x, y, DecorAssetLoader.getAssetFrames(gp, 2, "images/world/decor/trees/tree01.png"), true);
        this.gp=gp;
        this.aniSpeed = aniSpeed;
        scale = 2;
        solidAreaX = (int)((gp.tileSize*scale)*0.37);
        solidAreaY = (int)((gp.tileSize*2)*0.88);
        solidAreaWidth = (int)((gp.tileSize*2)*0.25);
        solidAreaHeight = (int)((gp.tileSize*2)*0.05);

        setSolidArea(x, y,solidAreaX,solidAreaY,solidAreaWidth,solidAreaHeight);
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        super.draw(g2, cameraX, cameraY);

        // DEBUG: draw tree collision rectangle
        g2.setColor(java.awt.Color.GREEN);
        g2.drawRect(
        solidArea.x - (int) cameraX,
        solidArea.y - (int) cameraY,
        solidArea.width,
        solidArea.height
        );
    }
}

