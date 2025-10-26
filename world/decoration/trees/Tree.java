package world.decoration.trees;

import java.awt.image.BufferedImage;
import main.GamePanel;
import world.decoration.Decoration;

public abstract class Tree extends Decoration {
    public Tree(GamePanel gp, double x, double y, BufferedImage[] frames) {
        super(gp, x, y);
        this.frames = frames;
    }

    @Override
    public void update() {
        super.update();
        // shared tree logic (e.g. wind sway)
    }
}
