package world.decoration;

import java.awt.image.BufferedImage;
import main.GamePanel;
import world.DecorAssetLoader;
import world.WorldEntity;

public class Grass extends WorldEntity {
    GamePanel gp;

    public static BufferedImage[] grassFrames;
    public Grass(double x, double y, GamePanel gp) {
        super(x, y, DecorAssetLoader.getAssetFrames(gp,1,"images/world/decor/grass/grass01.png"), true);
        this.aniSpeed = 70;
    }
}

