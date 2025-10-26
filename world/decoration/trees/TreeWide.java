package world.decoration.trees;

import main.GamePanel;
import tools.UtilityTool;
import world.SpriteLoader;

public class TreeWide extends Tree {
    public TreeWide(GamePanel gp, double x, double y) {
        super(gp, x, y, SpriteLoader.getAssetFrames(gp, 64, "images/world/decor/trees/tree01.png", 4));
        pixels = 64; // original pixel size of sprite 
        setSolidArea(
            (int)(pixels * 0.37),
            (int)(pixels * 0.88),
            (int)(pixels * 0.25),
            (int)(pixels * 0.05)
        );
        aniSpeed = UtilityTool.randomNumberInterval(90,110);
    }

    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.y + solidArea.height; // bottom edge of hitbox
        } else if (frames != null && frames.length > 0 && frames[0] != null) {
            return y + frames[0].getHeight() * pixels; // bottom of sprite
        } else {
            return y;
        }
    }
}

