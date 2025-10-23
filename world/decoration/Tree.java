package world.decoration;

import java.awt.image.BufferedImage;
import main.GamePanel;

public class Tree extends Decor {
    GamePanel gp;

    public static BufferedImage[] treeFrames;
    public Tree(double x, double y, GamePanel gp) {
        super(x, y, DecorAssetLoader.getTreeFrames(gp, 2), true);
        this.aniSpeed = 200;
    }
}

