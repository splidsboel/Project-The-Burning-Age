package world.decoration.grass;

import main.GamePanel;
import world.DecorAssetLoader;
import world.Entity;

public class Grass extends Entity {
    GamePanel gp;
    
    public Grass(double x, double y, GamePanel gp, int aniSpeed) {
        super(gp, x, y, DecorAssetLoader.getAssetFrames(gp,1,"images/world/decor/grass/grass01.png"), true);
        this.aniSpeed = aniSpeed;
    }
}

