package world.decoration.trees;

import main.GamePanel;
import tools.UtilityTool;
import world.SpriteLoader;

public class TreeTall extends Tree {

    public TreeTall(GamePanel gp, double x, double y) {
        super(gp, x, y, SpriteLoader.getAssetFrames(gp, 64, "images/world/decor/trees/tree02.png", 4));
        pixels = 64; // original pixel size of sprite 
        setSolidArea(
            (int)(pixels * 0.45),
            (int)(pixels * 0.85),
            (int)(pixels * 0.07),
            (int)(pixels * 0.04)
        );
    }

    @Override
    public void update() {
        animation(UtilityTool.randomNumberInterval(90,100));
    }

    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.y + solidArea.height; // bottom edge of hitbox
        } else if (frames != null && frames.length > 0 && frames[0] != null) {
            return y + frames[0].getHeight(); // bottom of sprite
        } else {
            return y;
        }
    }
}
