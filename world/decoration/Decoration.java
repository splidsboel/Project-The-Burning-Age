package world.decoration;

import java.awt.Graphics2D;
import main.GamePanel;
import world.Entity;

public abstract class Decoration extends Entity {
    public Decoration(GamePanel gp, double x, double y) {
        super(gp, x, y, null);
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        super.draw(g2, cameraX, cameraY);

    }
}
