package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.Renderable;

public abstract class Entity implements Renderable {
    protected GamePanel gp;
    public double x, y;
    protected int pixels = 32;
    protected BufferedImage[] frames;
    protected int frameIndex;
    protected int aniTick;
    protected int aniSpeed;

    public Rectangle solidArea = new Rectangle();
    public int solidAreaX;
    public int solidAreaY;
    public int solidAreaWidth;
    public int solidAreaHeight;
    protected int solidBaseWidth, solidBaseHeight; // unscaled dimensions
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
        updateSolidArea();
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

        // Base pixel scale per tile (tileSize is already zoomed)
        double scale = gp.tileSize / (double) gp.originalTileSize;

        BufferedImage img = frames[frameIndex];

        // World → screen transform
        double drawX = (x - cameraX);
        double drawY = (y - cameraY);

        int drawW = (int) (img.getWidth() * scale);
        int drawH = (int) (img.getHeight() * scale);

        g2.drawImage(img, (int) drawX, (int) drawY, drawW, drawH, null);
    }

    public void setSolidArea(int offsetX, int offsetY, int width, int height) {
        this.solidOffsetX = offsetX;
        this.solidOffsetY = offsetY;
        this.solidBaseWidth = width;
        this.solidBaseHeight = height;
        updateSolidArea();
    }

    public void updateSolidArea() {
        double scale = gp.tileSize / (double) gp.originalTileSize;
        solidArea.setBounds(
            (int)(x + solidOffsetX * scale),
            (int)(y + solidOffsetY * scale),
            (int)(solidBaseWidth * scale),
            (int)(solidBaseHeight * scale)
        );
    }

    //GETTERS
    @Override
    public Rectangle getSolidArea() {
        return solidArea;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
