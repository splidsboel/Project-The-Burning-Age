package world.decoration;

import java.awt.image.BufferedImage;



public class Grass extends AbstractDecor {
    public static BufferedImage[] grassFrames;
    public Grass(double x, double y, BufferedImage[] frames) {
        super(x, y, frames, true);
        this.aniSpeed = 100;
        this.setAlpha(0.95f); // soft, slight transparency
    }
}

