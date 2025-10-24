package world.decoration;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import world.DecorAssetLoader;
import world.WorldEntity;

public class TreeWide extends WorldEntity {
    GamePanel gp;

    public static BufferedImage[] treeFrames;
    public TreeWide(double x, double y, GamePanel gp) {
        super(x, y, DecorAssetLoader.getAssetFrames(gp, 2, "images/world/decor/trees/tree01.png"), true);
        this.gp=gp;
        this.aniSpeed = 200;
        setSolidArea();
    }

    public void setSolidArea() {
        solidArea = new Rectangle((int)(x+(gp.tileSize*2)*0.41),(int)(y+(gp.tileSize*2)*0.82),(int)((gp.tileSize*2)*0.15),(int)((gp.tileSize*2)*0.10)); 
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

