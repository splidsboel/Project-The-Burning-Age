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

    // Collision box (physical presence)
    protected final Rectangle solidArea = new Rectangle();
    protected int solidOffsetX, solidOffsetY;
    protected int solidBaseWidth, solidBaseHeight;

    // Attack / interaction box (offensive or range zone)
    protected final Rectangle hitBox = new Rectangle();
    protected int hitOffsetX, hitOffsetY;
    protected int hitBaseWidth, hitBaseHeight;
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
        // if (frames == null || frames.length <= 1) return;
        // aniTick++;
        // if (aniTick >= aniSpeed) {
        //     aniTick = 0;
        //     frameIndex = (frameIndex + 1) % frames.length;
        // }
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

    public void animation(int aniSpeed) {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            frameIndex++;
            if (frameIndex >= frames.length) {
                frameIndex = 1;
            }
        }
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

    public void setHitBox (double width, double height) {
        this.hitBaseWidth = (int)(pixels * width);
        this.hitBaseHeight = (int)(pixels * height);
        this.hitOffsetX = (pixels - hitBaseWidth) / 2;
        this.hitOffsetY = (pixels - hitBaseHeight) / 2;
        updateHitBox();
    }

    public void updateHitBox() {
        double scale = gp.tileSize / (double) gp.originalTileSize;
        hitBox.setBounds(
            (int)(x + hitOffsetX * scale),
            (int)(y + hitOffsetY * scale),
            (int)(hitBaseWidth * scale),
            (int)(hitBaseHeight * scale)
        );
    }
    
    @Override
    public void updatePosition() {

    }

    //GETTERS
    @Override
    public Rectangle getSolidArea() {
        return solidArea;
    }
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
