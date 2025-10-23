package world.decoration;

import java.awt.image.BufferedImage;
import main.GamePanel;

public class Grass extends Decor {
    GamePanel gp;

    public static BufferedImage[] grassFrames;
    public Grass(double x, double y, GamePanel gp) {
        super(x, y, DecorAssetLoader.getGrassFrames(gp), true);
        this.aniSpeed = 70;
    }
}

