package world.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import world.Entity;

public abstract class Actor extends Entity {

    public boolean moving = false;
    public boolean up, right, left, down, left_up, left_down, right_up, right_down;
    public int defaultSpeed;
    public double speed;


    public Actor(GamePanel gp, double x, double y, BufferedImage[] frames, boolean animated) {
        super(gp, x, y, frames);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        super.draw(g2, cameraX, cameraY);
    }
}
