package world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class WorldEntity {
    public double x, y; // world position
    protected BufferedImage[] frames;
    protected int frameIndex = 0;
    protected int aniTick = 0;
    protected int aniSpeed;

    public boolean animated = false;
    public boolean visible = true;

    public WorldEntity(double x, double y, BufferedImage[] frames, boolean animated) {
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

    public void draw(Graphics2D g2, double cameraX, double cameraY) {
        if (!visible || frames == null) return;

        int screenX = (int)(x - cameraX);
        int screenY = (int)(y - cameraY);
        g2.drawImage(frames[frameIndex], screenX, screenY, null);
    }
}

