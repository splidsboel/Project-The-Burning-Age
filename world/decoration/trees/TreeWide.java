package world.decoration.trees;

import main.GamePanel;
import tools.UtilityTool;
import world.SpriteLoader;

public class TreeWide extends Tree {
    public TreeWide(GamePanel gp, double x, double y) {
        super(gp, x, y, SpriteLoader.getAssetFrames(gp, 2, "images/world/decor/trees/tree01.png"));
        this.scale = 2;
        setSolidArea((int)((gp.tileSize*scale) * 0.37), (int)((gp.tileSize*scale) * 0.88), 
                     (int)((gp.tileSize*scale) * 0.25), (int)((gp.tileSize*scale) * 0.05));
        aniSpeed = UtilityTool.randomNumberInterval(90,110);
    }

    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.y + solidArea.height; // bottom edge of hitbox
        } else if (frames != null && frames.length > 0 && frames[0] != null) {
            return y + frames[0].getHeight() * scale; // bottom of sprite
        } else {
            return y;
        }
    }
}

