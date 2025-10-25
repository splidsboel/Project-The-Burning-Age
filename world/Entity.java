package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.Renderable;

public abstract class Entity implements Renderable {
    protected GamePanel gp;
    protected double x, y;
    public double worldX, worldY;
    protected BufferedImage[] frames;
    protected int frameIndex = 0;
    protected int aniTick = 0;
    protected int aniSpeed = 12;
    protected boolean animated = false;
    protected boolean visible = true;

    public Rectangle solidArea = new Rectangle();
    public int solidOffsetX, solidOffsetY;
    public boolean collisionUp, collisionDown, collisionLeft, collisionRight;
    public boolean collisionOn = false;

    public Entity(GamePanel gp, double x, double y, BufferedImage[] frames, boolean animated) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.frames = frames;
        this.animated = animated;
    }

    public void update() {
        if (!animated || frames == null || frames.length <= 1) return;
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            frameIndex = (frameIndex + 1) % frames.length;
        }
    }

    @Override
    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        if (!visible || frames == null || frames.length == 0) return;
        int screenX = (int) (x - cameraX);
        int screenY = (int) (y - cameraY);
        g2.drawImage(frames[frameIndex], screenX, screenY, null);
    }

    public void setSolidArea(int offsetX, int offsetY, int width, int height) {
        this.solidOffsetX = offsetX;
        this.solidOffsetY = offsetY;
        this.solidArea.setBounds((int)(x + offsetX), (int)(y + offsetY), width, height);
    }

    @Override
    public double getBottomY() {
        return y + solidArea.y + solidArea.height;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
