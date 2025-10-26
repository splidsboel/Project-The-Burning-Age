package world.decoration.rocks;

import java.awt.image.BufferedImage;
import main.GamePanel;
import world.decoration.Decoration;

public abstract class Rock extends Decoration {
    public Rock(GamePanel gp, double x, double y, BufferedImage[] frames) {
        super(gp, x, y);
        this.frames = frames;
    }

    @Override
    public void update() {
        super.update();
        // shared tree logic (e.g. wind sway)
    }
}
