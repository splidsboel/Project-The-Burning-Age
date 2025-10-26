package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.Renderable;

public abstract class Entity implements Renderable {
    protected GamePanel gp;
    public double x, y;
    protected double scale = 1.0;
    protected BufferedImage[] frames;
    protected int frameIndex;
    protected int aniTick;
    protected int aniSpeed;

    public Rectangle solidArea = new Rectangle();
    public int solidAreaX;
    public int solidAreaY;
    public int solidAreaWidth;
    public int solidAreaHeight;
    public int solidOffsetX, solidOffsetY;
    public boolean collisionUp, collisionDown, collisionLeft, collisionRight;
    public boolean collisionOn = false;

    public Entity(GamePanel gp, double x, double y, BufferedImage[] frames) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.frames = frames;
    }

    public void update() {
        if (frames == null || frames.length <= 1) return;
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        if (frames == null || frames.length == 0) return;
        int screenX = (int) (x - cameraX);
        int screenY = (int) (y - cameraY);
        g2.drawImage(frames[frameIndex], screenX, screenY, null);
    }

    public void setSolidArea(int offsetX, int offsetY, int width, int height) {
        this.solidOffsetX = offsetX;
        this.solidOffsetY = offsetY;
        this.solidArea.setBounds((int)(x + offsetX), (int)(y + offsetY), width, height);
    }


    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
