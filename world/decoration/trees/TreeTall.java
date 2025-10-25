package world.decoration.trees;

import java.awt.Graphics2D;
import main.GamePanel;
import world.DecorAssetLoader;
import world.Entity;

public class TreeTall extends Entity {
    GamePanel gp;
    protected int scale;


    public TreeTall(double x, double y, GamePanel gp, int aniSpeed) {
        super(gp, x, y, DecorAssetLoader.getAssetFrames(gp, 2, "images/world/decor/trees/tree02.png"), true);
        this.gp = gp;
        this.aniSpeed = aniSpeed;
        scale = 2;
        solidArea.x = (int)((gp.tileSize*scale)*0.45);
        solidArea.y = (int)((gp.tileSize*2)*0.85);
        solidArea.width = (int)((gp.tileSize*2)*0.07);
        solidArea.height = (int)((gp.tileSize*2)*0.04);
        
        setSolidArea(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
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

