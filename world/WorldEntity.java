package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import tools.Renderable;

public class WorldEntity implements Renderable {
    public GamePanel gp;
    public double x, y; // world position
    public double scale;
    protected BufferedImage[] frames;
    protected int frameIndex = 0;
    protected int aniTick = 0;
    protected int aniSpeed;



    public Rectangle worldEntityArea;
    public Rectangle solidArea;
    public boolean animated = false;
    public boolean visible = true;

    public WorldEntity(GamePanel gp, double x, double y, BufferedImage[] frames, boolean animated) {
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
        if (!visible || frames == null) return;

        int screenX = (int)(x - cameraX);
        int screenY = (int)(y - cameraY);
        g2.drawImage(frames[frameIndex], screenX, screenY, null);
    
    }
    
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public double getBottomY() {
        if (solidArea != null) {
            return solidArea.y;
        } else if (frames != null && frames.length > 0 && frames[0] != null) {
            return y - gp.originalTileSize + frames[0].getHeight();
        } else {
            return y;
        }
    }


}

