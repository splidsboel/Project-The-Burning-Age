package world.decoration;

import main.GamePanel;
import world.DecorAssetLoader;
import world.WorldEntity;

public class Stone extends WorldEntity {
    GamePanel gp;
    
    public Stone(double x, double y, GamePanel gp) {
        super(gp, x, y, DecorAssetLoader.getAssetFrames(gp,1,"images/world/decor/stone/stone01.png"), false);
    }
}
