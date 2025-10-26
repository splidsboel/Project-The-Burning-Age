package world.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import world.Entity;

public abstract class Actor extends Entity {

    //Movement
    public boolean moving = false;
    public boolean idle, up, right, left, down, left_up, left_down, right_up, right_down;
    public int defaultSpeed;
    public double speed;

    // Animation
    protected BufferedImage[][] animations;
    protected int aniTick;
    protected int aniIndex;


    public Actor(GamePanel gp, double x, double y, BufferedImage[] frames, boolean animated) {
        super(gp, x, y, frames);
    }

    @Override
    public void update() {
        super.update();
        updateSolidArea();
        updateHitBox();
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        super.draw(g2, cameraX, cameraY);
    }

    @Override
    public void animation(int aniSpeed) {
        if (moving) {
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= animations.length) {
                    aniIndex = 1;
                }
            } 
        } else {
            aniIndex = 0;
        }
    }
}
