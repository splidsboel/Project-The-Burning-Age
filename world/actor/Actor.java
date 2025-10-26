package world.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.Damageable;
import world.Entity;

public abstract class Actor extends Entity implements Damageable {
    //Stats
    protected String name;
    protected int health;
    protected boolean isAlive = true;

    //Combat
    protected boolean invulnerable; 
    protected int invulTime = 0;      // frames remaining
    protected int invulDuration = 60; // 1 second at 60 FPS


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


        if (invulnerable) {
            invulTime--;
        }
        if (invulTime <= 0) { 
            invulnerable = false;
        }
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

    @Override
    public void damage(int dmg) {
        if (invulnerable) {
        } else {
            setHealth(health - dmg);
            invulnerable = true;
            invulTime = invulDuration;
            if (health <= 0) {
                kill();
            }
        }
    }

    @Override
    public void kill() {
        setAlive(false);
    }

    //GETTERS
    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return isAlive;
    }


    //SETTERS
    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }


    
}
