package world.decoration.rocks;

import main.GamePanel;
import world.SpriteLoader;

public class Stone extends Rock {
    public Stone(GamePanel gp, double x, double y) {
        super(gp, x, y, SpriteLoader.getAssetFrames(gp, 32, "images/world/decor/stone/stone01.png", 1));
        pixels = 32; // original pixel size of sprite 
        setSolidArea(
            (int)(pixels * 0.35),
            (int)(pixels * 0.60),
            (int)(pixels * 0.25),
            (int)(pixels * 0.04)
        );
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